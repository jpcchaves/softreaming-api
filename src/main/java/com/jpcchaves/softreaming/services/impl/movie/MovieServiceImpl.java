package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsIdsDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsIdsDtos;
import com.jpcchaves.softreaming.payload.dtos.movie.*;
import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;
import com.jpcchaves.softreaming.services.usecases.movie.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final FilterMovieUseCase filterMovie;
    private final CreateMovieUseCase createMovie;
    private final GetAllMoviesUseCase getAllMovies;
    private final GetMovieByIdUseCase getMovieById;
    private final UpdateMovieUseCase updateMovie;
    private final DeleteMovieUseCase deleteMovie;
    private final AddMovieRatingUseCase addMovieRating;
    private final UpdateMovieRatingUseCase updateMovieRating;
    private final GetMovieRatingUseCase getMovieRating;
    private final SortByBestRatingUseCase sortByBestRating;
    private final FilterMovieByReleaseDateBetweenUseCase filterMovieByReleaseDateBetween;
    private final AddMovieCategoryUseCase addMovieCategory;
    private final RemoveMovieCategoryUseCase removeMovieCategory;
    private final AddMovieDirectorUseCase addMovieDirector;
    private final RemoveMovieDirectorUseCase removeMovieDirector;
    private final AddMovieActorUseCase addMovieActor;
    private final RemoveMovieActorUseCase removeMovieActor;
    private final FilterMovieByRatingGreaterThanUseCase filterMovieByRatingGreaterThan;
    private final FindAllMoviesByCategoryUseCase findAllMoviesByCategory;
    private final FindAllMoviesByActorUseCase findAllMoviesByActor;

    public MovieServiceImpl(FilterMovieUseCase filterMovie,
                            CreateMovieUseCase createMovie,
                            GetAllMoviesUseCase getAllMovies,
                            GetMovieByIdUseCase getMovieById,
                            UpdateMovieUseCase updateMovie,
                            DeleteMovieUseCase deleteMovie,
                            AddMovieRatingUseCase addMovieRating,
                            UpdateMovieRatingUseCase updateMovieRating,
                            GetMovieRatingUseCase getMovieRating,
                            SortByBestRatingUseCase sortByBestRating,
                            FilterMovieByReleaseDateBetweenUseCase filterMovieByReleaseDateBetween,
                            AddMovieCategoryUseCase addMovieCategory,
                            RemoveMovieCategoryUseCase removeMovieCategory,
                            AddMovieDirectorUseCase addMovieDirector,
                            RemoveMovieDirectorUseCase removeMovieDirector,
                            AddMovieActorUseCase addMovieActor,
                            RemoveMovieActorUseCase removeMovieActor,
                            FilterMovieByRatingGreaterThanUseCase filterMovieByRatingGreaterThan,
                            FindAllMoviesByCategoryUseCase findAllMoviesByCategory,
                            FindAllMoviesByActorUseCase findAllMoviesByActor) {
        this.filterMovie = filterMovie;
        this.createMovie = createMovie;
        this.getAllMovies = getAllMovies;
        this.getMovieById = getMovieById;
        this.updateMovie = updateMovie;
        this.deleteMovie = deleteMovie;
        this.addMovieRating = addMovieRating;
        this.updateMovieRating = updateMovieRating;
        this.getMovieRating = getMovieRating;
        this.sortByBestRating = sortByBestRating;
        this.filterMovieByReleaseDateBetween = filterMovieByReleaseDateBetween;
        this.addMovieCategory = addMovieCategory;
        this.removeMovieCategory = removeMovieCategory;
        this.addMovieDirector = addMovieDirector;
        this.removeMovieDirector = removeMovieDirector;
        this.addMovieActor = addMovieActor;
        this.removeMovieActor = removeMovieActor;
        this.filterMovieByRatingGreaterThan = filterMovieByRatingGreaterThan;
        this.findAllMoviesByCategory = findAllMoviesByCategory;
        this.findAllMoviesByActor = findAllMoviesByActor;
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
        return updateMovieRating.updateRating(id, ratingId, movieRatingDto);
    }

    @Override
    public RatingDto getMovieRating(Long movieId) {
        return getMovieRating.getMovieRating(movieId);
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
        return sortByBestRating.sortByBestRating();
    }

    @Override
    public MovieResponsePaginatedDto<?> filterByReleaseDateBetween(String startDate,
                                                                   String endDate,
                                                                   Pageable pageable) {
        return filterMovieByReleaseDateBetween.filterByReleaseDateBetween(startDate, endDate, pageable);
    }

    @Override
    public ApiMessageResponseDto addCategory(Long id,
                                             CategoryRequestDto categoryRequestDto) {
        return addMovieCategory.addCategory(id, categoryRequestDto);
    }

    @Override
    public ApiMessageResponseDto removeCategory(Long id,
                                                CategoryRequestDto categoryRequestDto) {

        return removeMovieCategory.removeCategory(id, categoryRequestDto);
    }

    @Override
    public ApiMessageResponseDto addDirector(Long id,
                                             DirectorsIdsDtos directorsIdsDtos) {
        return addMovieDirector.addDirector(id, directorsIdsDtos);
    }

    @Override
    public ApiMessageResponseDto removeDirector(Long id,
                                                DirectorsIdsDtos directorsIdsDtos) {
        return removeMovieDirector.removeDirector(id, directorsIdsDtos);
    }

    @Override
    public ApiMessageResponseDto addActor(Long id,
                                          ActorsIdsDto actorsIds) {
        return addMovieActor.addActor(id, actorsIds);
    }

    @Override
    public ApiMessageResponseDto removeActor(Long id,
                                             ActorsIdsDto actorsIds) {
        return removeMovieActor.removeActor(id, actorsIds);
    }

    @Override
    public MovieResponsePaginatedDto<?> filterByRatingGreaterThan(Pageable pageable,
                                                                  Double rating) {
        return filterMovieByRatingGreaterThan.filterByRatingGreaterThan(pageable, rating);
    }

    @Override
    public MovieResponsePaginatedDto<?> findAllMoviesByCategory(Pageable pageable,
                                                                Long categoryId) {
        return findAllMoviesByCategory.findAllMoviesByCategory(pageable, categoryId);
    }

    @Override
    public MovieResponsePaginatedDto<?> findAllMoviesByActor(Pageable pageable,
                                                             Long actorId) {
        return findAllMoviesByActor.findAllMoviesByActor(pageable, actorId);
    }
}
