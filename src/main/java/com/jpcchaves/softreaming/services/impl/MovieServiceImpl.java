package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.*;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.exceptions.SqlBadRequestException;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorDto;
import com.jpcchaves.softreaming.payload.dtos.movie.*;
import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;
import com.jpcchaves.softreaming.repositories.*;
import com.jpcchaves.softreaming.services.MovieService;
import com.jpcchaves.softreaming.services.SecurityContextService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    private static final int ONE = 1;
    private static final int TWO = 2;

    private final MovieRepository repository;
    private final RatingRepository ratingRepository;
    private final LineRatingRepository lineItemRepository;
    private final CategoryRepository categoryRepository;
    private final DirectorRepository directorRepository;
    private final SecurityContextService securityContextService;
    private final MapperUtils mapper;

    public MovieServiceImpl(MovieRepository repository,
                            RatingRepository ratingRepository,
                            LineRatingRepository lineItemRepository,
                            CategoryRepository categoryRepository,
                            DirectorRepository directorRepository,
                            SecurityContextService securityContextService,
                            MapperUtils mapper) {
        this.repository = repository;
        this.ratingRepository = ratingRepository;
        this.lineItemRepository = lineItemRepository;
        this.categoryRepository = categoryRepository;
        this.directorRepository = directorRepository;
        this.securityContextService = securityContextService;
        this.mapper = mapper;
    }

    @Override
    public ApiMessageResponseDto create(MovieRequestDto requestDto) {

        if (repository.existsByName(requestDto.getName())) {
            throw new BadRequestException("Já existe um filme cadastrado com o nome informado: " + requestDto.getName());
        }

        List<DirectorDto> directorsToSave = new ArrayList<>();
        for (DirectorDto director : requestDto.getDirectors()) {
            Optional<Director> entity = directorRepository.findByFirstNameIgnoreCaseOrLastNameIgnoreCase(director.getFirstName(), director.getLastName());
            if (entity.isEmpty()) {
                directorRepository.save(mapper.parseObject(director, Director.class));
            } else {
                directorsToSave.add(director);
            }
        }

        try {

            List<Director> directors = new ArrayList<>();
            if (!directorsToSave.isEmpty()) {
                directors = mapper.parseListObjects(directorsToSave, Director.class);
            }
            List<Director> savedDirectors = directorRepository.saveAll(directors);

            Set<Category> categoriesSet = new HashSet<>(categoryRepository
                    .findAllById(requestDto.getCategoriesIds()));

            Movie movie = mapper.parseObject(requestDto,
                    Movie.class);
            movie.setCategories(categoriesSet);

            movie.setDirectors(new HashSet<>(savedDirectors));
            Movie savedMovie = repository.save(movie);

            Rating rating = ratingRepository.save(new Rating(0.0,
                    0,
                    new Date(),
                    savedMovie));
            savedMovie.setRatings(rating);

            repository.save(savedMovie);

            return new ApiMessageResponseDto("Filme criado com sucesso!");
        } catch (DataIntegrityViolationException ex) {
            throw new SqlBadRequestException(("Ocorreu um erro: " + ex.getRootCause().getMessage()));
        }
    }

    @Override
    public MovieResponsePaginatedDto getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Movie> moviesPage = repository.findAll(pageable);

        List<MovieResponseMinDto> movieResponseMinDtos = mapper.parseListObjects(moviesPage.getContent(),
                MovieResponseMinDto.class);

        return buildMovieResponsePaginatedDto(movieResponseMinDtos,
                moviesPage);
    }

    @Override
    public MovieResponseDto getById(Long id) {

        Movie movie = getMovie(id);

        return buildMovieResponseDto(movie);

    }

    @Override
    public MovieResponseDto update(MovieRequestDto requestDto,
                                   Long id) {
        try {
            List<Category> categories = categoryRepository
                    .findAllById(requestDto.getCategoriesIds());

            Set<Category> newCategories = new HashSet<>(categories);

            Movie movie = getMovie(id);
            Movie updatedMovie = updateMovie(movie,
                    requestDto);
            updatedMovie.setCategories(newCategories);

            Movie savedMovie = repository.save(updatedMovie);

            return mapper.parseObject(savedMovie,
                    MovieResponseDto.class);
        } catch (DataIntegrityViolationException ex) {
            throw new SqlBadRequestException(("Ocorreu um erro: " + ex.getRootCause().getMessage()));
        }
    }


    @Override
    public void delete(Long id) {
        getMovie(id);
        repository.deleteById(id);
    }

    @Override
    public ApiMessageResponseDto addMovieRating(Long id,
                                                MovieRatingDto movieRatingDto) {
        Long userId = securityContextService.getCurrentLoggedUser().getId();
        Movie movie = getMovie(id);
        Rating movieRating = movie.getRatings();

        if (lineItemRepository.existsByUserIdAndRating_Id(userId,
                movieRating.getId())) {
            throw new BadRequestException("Você já avaliou o filme: " + movie.getName());
        }

        LineRating newLineRating = new LineRating(movieRatingDto.getRating(),
                userId,
                movieRating);
        lineItemRepository.save(newLineRating);


        movieRating.setRatingsAmount(movieRating.getRatingsAmount() + ONE);

        List<LineRating> lineRatingList = lineItemRepository.findAllByRating_Id(movieRating.getId());

        if (hasMoreThanOneRating(movieRating.getRatingsAmount())) {
            Double avgRating = calcRating(lineRatingList);

            movieRating.setRating(avgRating);
            movieRating.setRatingsAmount(lineRatingList.size());
        } else {
            movieRating.setRating(movieRatingDto.getRating());
        }

        ratingRepository.save(movieRating);

        return new ApiMessageResponseDto("Filme avaliado com sucesso");
    }

    @Override
    public ApiMessageResponseDto updateRating(Long id,
                                              Long ratingId,
                                              MovieRatingDto movieRatingDto) {
        Long userId = securityContextService.getCurrentLoggedUser().getId();

        LineRating lineRating = lineItemRepository
                .findByUserIdAndId(userId,
                        ratingId)
                .orElseThrow(() -> new BadRequestException("Ocorreu um erro ao editar sua avaliação. Verifique os dados e tente novamente"));

        lineRating.setRate(movieRatingDto.getRating());

        lineItemRepository.save(lineRating);

        Movie movie = getMovie(id);
        Rating movieRating = movie.getRatings();

        if (movieRating.getLineRatings().isEmpty()) {
            throw new BadRequestException("Ainda não há avaliações para o filme");
        }

        if (hasMoreThanOneRating(movieRating.getRatingsAmount())) {
            Double avgRating = calcRating(movie.getRatings().getLineRatings());

            movieRating.setRating(avgRating);
            movieRating.setRatingsAmount(movie.getRatings().getLineRatings().size());
        } else {
            movieRating.setRating(movieRatingDto.getRating());
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Avaliação atualizada com sucesso!");
    }

    @Override
    public RatingDto getMovieRating(Long movieId) {
        Movie movie = getMovie(movieId);
        Rating movieRating = movie.getRatings();

        return mapper.parseObject(movieRating,
                RatingDto.class);
    }

    @Override
    public MovieResponsePaginatedDto filterBy(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC)
                                              Pageable pageable,
                                              String releaseDate,
                                              String name) {

        if (releaseDate == null && name == null) {
            throw new BadRequestException("Informe ao menos um filtro para conseguir realizar a busca");
        }

        if (releaseDate != null && name != null) {
            Page<Movie> moviePage = repository.findByNameContainingIgnoreCaseAndReleaseDate(pageable,
                    name,
                    releaseDate);
            return getMovieResponsePaginatedDto(moviePage);
        }

        Page<Movie> moviePage;
        if (releaseDate != null) {
            moviePage = repository.findByReleaseDate(pageable,
                    releaseDate);
        } else {
            moviePage = repository.findByNameContainingIgnoreCase(pageable,
                    name);
        }
        return getMovieResponsePaginatedDto(moviePage);
    }

    @Override
    public List<MovieByBestRatedDto> sortByBestRating() {
        List<Movie> movieList = repository.findTop10ByOrderByRatings_RatingDesc();
        List<MovieByBestRatedDto> bestRatedDtos = new ArrayList<>();

        for (Movie movie : movieList) {
            bestRatedDtos.add(new MovieByBestRatedDto(mapper.parseObject(movie, MovieResponseMinDto.class),
                    movie.getRatings().getRating(),
                    movie.getRatings().getRatingsAmount())
            );
        }

        return bestRatedDtos;
    }

    private MovieResponseDto buildMovieResponseDto(Movie movie) {
        MovieResponseDto movieResponseDto = new MovieResponseDto();

        for (Category category : movie.getCategories()) {
            movieResponseDto.getCategories().add(category.getCategory());
        }

        movieResponseDto.setId(movie.getId());
        movieResponseDto.setName(movie.getName());
        movieResponseDto.setShortDescription(movie.getShortDescription());
        movieResponseDto.setLongDescription(movie.getLongDescription());
        movieResponseDto.setDuration(movie.getDuration());
        movieResponseDto.setReleaseDate(movie.getReleaseDate());
        movieResponseDto.setMovieUrl(movie.getMovieUrl());
        movieResponseDto.setPosterUrl(movie.getPosterUrl());
        movieResponseDto.setCreatedAt(movie.getCreatedAt());
        movieResponseDto.setRatings(mapper.parseObject(movie.getRatings(),
                RatingDto.class));
        movieResponseDto.setDirectors(mapper.parseSetObjects(movie.getDirectors(), DirectorDto.class));

        return movieResponseDto;
    }

    private MovieResponsePaginatedDto getMovieResponsePaginatedDto(Page<Movie> moviePage) {
        MovieResponsePaginatedDto response = new MovieResponsePaginatedDto();
        response.setContent(mapper.parseListObjects(moviePage.getContent(),
                MovieResponseMinDto.class));
        response.setPageNo(moviePage.getNumber());
        response.setPageSize(moviePage.getSize());
        response.setTotalElements(moviePage.getTotalElements());
        response.setTotalPages(moviePage.getTotalPages());
        response.setLast(moviePage.isLast());

        return response;
    }

    private Boolean hasMoreThanOneRating(Integer ratingsAmount) {
        return ratingsAmount > 0;
    }

    private Double formatRating(Double rating) {
        BigDecimal bd = new BigDecimal(rating).setScale(TWO,
                RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    private Double calcRating(List<LineRating> lineRatingList) {
        Double avgRating = 0.0;

        for (LineRating lineRate : lineRatingList) {
            avgRating += lineRate.getRate();
        }

        return formatRating(avgRating / lineRatingList.size());
    }

    private Movie updateMovie(Movie movie,
                              MovieRequestDto requestDto) {
        movie.setId(movie.getId());
        movie.setCreatedAt(movie.getCreatedAt());
        movie.setName(requestDto.getName());
        movie.setShortDescription(requestDto.getShortDescription());
        movie.setLongDescription(requestDto.getLongDescription());
        movie.setDuration(requestDto.getDuration());
        movie.setMovieUrl(requestDto.getMovieUrl());
        movie.setPosterUrl(requestDto.getPosterUrl());
        movie.setCategories(mapper.parseSetObjects(requestDto.getCategories(),
                Category.class));
        return movie;
    }

    private Movie getMovie(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um filme com o ID  informado"));
    }

    private MovieResponsePaginatedDto buildMovieResponsePaginatedDto(List<MovieResponseMinDto> moviesResponse,
                                                                     Page<Movie> moviePage) {
        MovieResponsePaginatedDto moviePaginated = new MovieResponsePaginatedDto();

        moviePaginated.setContent(moviesResponse);
        moviePaginated.setPageNo(moviePage.getNumber());
        moviePaginated.setPageSize(moviePage.getSize());
        moviePaginated.setTotalElements(moviePage.getTotalElements());
        moviePaginated.setTotalPages(moviePage.getTotalPages());
        moviePaginated.setLast(moviePage.isLast());

        return moviePaginated;
    }

}
