package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.entities.LineRating;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.entities.Rating;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.exceptions.SqlBadRequestException;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRatingDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRequestDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseMinDto;
import com.jpcchaves.softreaming.repositories.CategoryRepository;
import com.jpcchaves.softreaming.repositories.LineRatingRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.repositories.RatingRepository;
import com.jpcchaves.softreaming.services.MovieService;
import com.jpcchaves.softreaming.services.SecurityContextService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    private static final int ONE = 1;
    private static final int TWO = 2;

    private final MovieRepository repository;
    private final RatingRepository ratingRepository;
    private final LineRatingRepository lineItemRepository;
    private final CategoryRepository categoryRepository;
    private final SecurityContextService securityContextService;
    private final MapperUtils mapper;

    public MovieServiceImpl(MovieRepository repository,
                            RatingRepository ratingRepository,
                            LineRatingRepository lineItemRepository,
                            CategoryRepository categoryRepository,
                            SecurityContextService securityContextService,
                            MapperUtils mapper) {
        this.repository = repository;
        this.ratingRepository = ratingRepository;
        this.lineItemRepository = lineItemRepository;
        this.categoryRepository = categoryRepository;
        this.securityContextService = securityContextService;
        this.mapper = mapper;
    }

    @Override
    public MovieResponseDto create(MovieRequestDto requestDto) {

        if (repository.existsByName(requestDto.getName())) {
            throw new BadRequestException("Já existe um filme cadastrado com o nome informado: " + requestDto.getName());
        }

        for (Long id : requestDto.getCategoriesIds()) {
            if (!categoryRepository.existsById(id)) {
                throw new BadRequestException("A categoria com o id: " + id + " não existe");
            }
        }

        try {
            List<Category> categoriesList = categoryRepository
                    .findAllById(requestDto.getCategoriesIds());

            Set<Category> categorySet = new HashSet<>(categoriesList);

            Movie movie = mapper.parseObject(requestDto, Movie.class);
            movie.setCategories(categorySet);

            Movie savedMovie = repository.save(movie);

            Rating rating = ratingRepository.save(new Rating(0.0, 0, savedMovie));

            savedMovie.setRatings(rating);

            repository.save(savedMovie);

            MovieResponseDto movieResponseDto = mapper.parseObject(savedMovie, MovieResponseDto.class);
            return movieResponseDto;
        } catch (DataIntegrityViolationException ex) {
            throw new SqlBadRequestException(("Ocorreu um erro: " + ex.getRootCause().getMessage()));
        }
    }

    @Override
    public List<MovieResponseMinDto> getAll() {
        List<Movie> movies = repository.findAll();
        List<MovieResponseMinDto> movieDtos = mapper.parseListObjects(movies, MovieResponseMinDto.class);
        return movieDtos;
    }

    @Override
    public MovieResponseDto getById(Long id) {

        Movie movie = getMovie(id);

        MovieResponseDto movieDto = mapper.parseObject(movie, MovieResponseDto.class);

        return movieDto;
    }

    @Override
    public MovieResponseDto update(MovieRequestDto requestDto, Long id) {
        try {
            List<Category> categories = categoryRepository
                    .findAllById(requestDto.getCategoriesIds());

            Set<Category> newCategories = new HashSet<>(categories);

            Movie movie = getMovie(id);
            Movie updatedMovie = updateMovie(movie, requestDto);
            updatedMovie.setCategories(newCategories);

            Movie savedMovie = repository.save(updatedMovie);
            MovieResponseDto movieDto = mapper.parseObject(savedMovie, MovieResponseDto.class);

            return movieDto;
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
    public ApiMessageResponseDto updateMovieRating(Long id, MovieRatingDto movieRatingDto) {
        Movie movie = getMovie(id);
        Rating movieRating = movie.getRatings();
        Long userId = securityContextService.getCurrentLoggedUser().getId();

        LineRating newLineRating = new LineRating(movieRatingDto.getRating(), userId, movieRating);
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

    private Boolean hasMoreThanOneRating(Integer ratingsAmount) {
        return ratingsAmount > 0;
    }

    private Double formatRating(Double rating) {
        BigDecimal bd = new BigDecimal(rating).setScale(TWO, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    private Double calcRating(List<LineRating> lineRatingList) {
        Double avgRating = 0.0;

        for (LineRating lineRate : lineRatingList) {
            avgRating += lineRate.getRate();
        }

        return formatRating(avgRating / lineRatingList.size());
    }

    private Double calculateRating(Double previousRating, Double currentRating) {
        Double result = (previousRating + currentRating) / TWO;
        return formatRating(result);
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
        movie.setCategories(requestDto.getCategories());
        return movie;
    }

    private Movie getMovie(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um filme com o ID  informado"));
    }

}
