package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.*;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsIdsDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsIdsDtos;
import com.jpcchaves.softreaming.payload.dtos.movie.*;
import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;
import com.jpcchaves.softreaming.repositories.*;
import com.jpcchaves.softreaming.services.SecurityContextService;
import com.jpcchaves.softreaming.services.usecases.movie.*;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MovieServiceImpl implements MovieService {

    private static final int TWO = 2;

    private final MovieRepository repository;
    private final FilterMovieUseCase filterMovie;
    private final CreateMovieUseCase createMovie;
    private final GetAllMoviesUseCase getAllMovies;
    private final GetMovieByIdUseCase getMovieById;
    private final UpdateMovieUseCase updateMovie;
    private final DeleteMovieUseCase deleteMovie;
    private final AddMovieRatingUseCase addMovieRating;
    private final LineRatingRepository lineItemRepository;
    private final CategoryRepository categoryRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final SecurityContextService securityContextService;
    private final MapperUtils mapper;

    public MovieServiceImpl(MovieRepository repository,
                            FilterMovieUseCase filterMovie,
                            CreateMovieUseCase createMovie,
                            GetAllMoviesUseCase getAllMovies,
                            GetMovieByIdUseCase getMovieById,
                            UpdateMovieUseCase updateMovie,
                            DeleteMovieUseCase deleteMovie,
                            AddMovieRatingUseCase addMovieRating,
                            LineRatingRepository lineItemRepository,
                            CategoryRepository categoryRepository,
                            DirectorRepository directorRepository,
                            ActorRepository actorRepository,
                            SecurityContextService securityContextService,
                            MapperUtils mapper) {
        this.repository = repository;
        this.filterMovie = filterMovie;
        this.createMovie = createMovie;
        this.getAllMovies = getAllMovies;
        this.getMovieById = getMovieById;
        this.updateMovie = updateMovie;
        this.deleteMovie = deleteMovie;
        this.addMovieRating = addMovieRating;
        this.lineItemRepository = lineItemRepository;
        this.categoryRepository = categoryRepository;
        this.directorRepository = directorRepository;
        this.actorRepository = actorRepository;
        this.securityContextService = securityContextService;
        this.mapper = mapper;
    }

    @Override
    public ApiMessageResponseDto create(MovieRequestDto requestDto) {
        return createMovie.create(requestDto);
    }

    @Override
    public MovieResponsePaginatedDto<?> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return getAllMovies.getAll(pageable);
    }

    @Override
    public MovieResponseDto getById(Long id) {
        return getMovieById.getById(id);
    }

    @Override
    public MovieResponseDto update(MovieRequestDto requestDto,
                                   Long id) {
        return updateMovie.update(requestDto, id);
    }


    @Override
    public void delete(Long id) {
        deleteMovie.delete(id);
    }

    @Override
    public ApiMessageResponseDto addMovieRating(Long id,
                                                MovieRatingDto movieRatingDto) {
        return addMovieRating.addMovieRating(id, movieRatingDto);
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
    public MovieResponsePaginatedDto<?> filterBy(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC)
                                                 Pageable pageable,
                                                 String releaseDate,
                                                 String name) {
        return filterMovie.filterBy(pageable, releaseDate, name);
    }

    @Override
    public List<MovieByBestRatedDto> sortByBestRating() {
        List<Movie> movieList = repository.findTop10ByOrderByRatings_RatingDesc();

        return buildMovieListResponse(movieList);
    }

    @Override
    public MovieResponsePaginatedDto<?> filterByReleaseDateBetween(String startDate,
                                                                   String endDate,
                                                                   Pageable pageable) {
        Page<Movie> moviePage = repository.findByReleaseDateBetween(startDate, endDate, pageable);
        List<MovieByBestRatedDto> movieByBestRatedDto = buildMovieListResponse(moviePage.getContent());

        return buildMovieResponsePaginatedDto(movieByBestRatedDto, moviePage);
    }

    @Override
    public ApiMessageResponseDto addCategory(Long id,
                                             CategoryRequestDto categoryRequestDto) {
        Movie movie = getMovie(id);
        List<Category> categories = categoryRepository.findAllById(categoryRequestDto.getCategoryIds());

        movie.getCategories().addAll(categories);

        repository.save(movie);
        return new ApiMessageResponseDto("Categoria(s) adicionada(s) com sucesso");
    }

    @Override
    public ApiMessageResponseDto removeCategory(Long id,
                                                CategoryRequestDto categoryRequestDto) {
        Movie movie = getMovie(id);
        List<Category> categories = categoryRepository.findAllById(categoryRequestDto.getCategoryIds());

        categories.forEach(movie.getCategories()::remove);

        repository.save(movie);
        return new ApiMessageResponseDto("Categoria(s) removida(s) com sucesso");
    }

    @Override
    public ApiMessageResponseDto addDirector(Long id,
                                             DirectorsIdsDtos directorsIdsDtos) {
        Movie movie = getMovie(id);

        List<Director> directors = directorRepository.findAllById(directorsIdsDtos.getDirectorsIds());

        for (Director director : directors) {
            movie.getDirectors().add(director);
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Diretor(es) adicionado(s) com sucesso");
    }

    @Override
    public ApiMessageResponseDto removeDirector(Long id,
                                                DirectorsIdsDtos directorsIdsDtos) {
        Movie movie = getMovie(id);

        for (Long directorId : directorsIdsDtos.getDirectorsIds()) {
            movie.getDirectors().removeIf(director -> Objects.equals(director.getId(), directorId));
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Diretor(es) removido(s) com sucesso");
    }

    @Override
    public ApiMessageResponseDto addActor(Long id,
                                          ActorsIdsDto actorsIds) {
        Movie movie = getMovie(id);

        List<Actor> actors = actorRepository.findAllById(actorsIds.getActorsIds());

        for (Actor actor : actors) {
            movie.getActors().add(actor);
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Ator(es) adicionado(s) com sucesso");
    }

    @Override
    public ApiMessageResponseDto removeActor(Long id,
                                             ActorsIdsDto actorsIds) {
        Movie movie = getMovie(id);

        for (Long actorId : actorsIds.getActorsIds()) {
            movie.getActors().removeIf(actor -> Objects.equals(actor.getId(), actorId));
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Ator(es) removido(s) com sucesso");
    }

    @Override
    public MovieResponsePaginatedDto<?> filterByRatingGreaterThan(Pageable pageable,
                                                                  Double rating) {
        Page<Movie> movies = repository.findByRatings_RatingGreaterThanEqual(pageable, rating);
        List<MovieByBestRatedDto> movieByBestRatedDto = buildMovieListResponse(movies.getContent());

        return buildMovieResponsePaginatedDto(movieByBestRatedDto, movies);
    }

    @Override
    public MovieResponsePaginatedDto<?> findAllMoviesByCategory(Pageable pageable,
                                                                Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new BadRequestException("Categoria não encontrada com o ID informado: " + categoryId));

        Page<Movie> movies = repository.findAllByCategories(pageable, category);
        List<MovieByBestRatedDto> movieByBestRatedDto = buildMovieListResponse(movies.getContent());

        return buildMovieResponsePaginatedDto(movieByBestRatedDto, movies);
    }

    @Override
    public MovieResponsePaginatedDto<?> findAllMoviesByActor(Pageable pageable,
                                                             Long actorId) {
        Actor actor = actorRepository
                .findById(actorId)
                .orElseThrow(() -> new BadRequestException("Categoria não encontrada com o ID informado: " + actorId));

        Page<Movie> movies = repository.findAllByActors(pageable, actor);
        List<MovieByBestRatedDto> movieByBestRatedDto = buildMovieListResponse(movies.getContent());

        return buildMovieResponsePaginatedDto(movieByBestRatedDto, movies);
    }

    private List<MovieByBestRatedDto> buildMovieListResponse(List<Movie> movieList) {
        List<MovieByBestRatedDto> bestRatedDtos = new ArrayList<>();

        for (Movie movie : movieList) {
            bestRatedDtos.add(new MovieByBestRatedDto(mapper.parseObject(movie, MovieResponseMinDto.class),
                    movie.getRatings().getRating(),
                    movie.getRatings().getRatingsAmount())
            );
        }

        return bestRatedDtos;
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

        return movie;
    }

    private Movie getMovie(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um filme com o ID  informado"));
    }

    private <T> MovieResponsePaginatedDto<?> buildMovieResponsePaginatedDto(List<T> moviesResponse,
                                                                            Page<Movie> moviePage) {
        MovieResponsePaginatedDto<T> moviePaginated = new MovieResponsePaginatedDto<>();

        moviePaginated.setContent(moviesResponse);
        moviePaginated.setPageNo(moviePage.getNumber());
        moviePaginated.setPageSize(moviePage.getSize());
        moviePaginated.setTotalElements(moviePage.getTotalElements());
        moviePaginated.setTotalPages(moviePage.getTotalPages());
        moviePaginated.setLast(moviePage.isLast());

        return moviePaginated;
    }

}
