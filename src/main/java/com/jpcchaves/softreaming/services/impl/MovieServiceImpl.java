package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.exceptions.SqlBadRequestException;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRatingDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRequestDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;
import com.jpcchaves.softreaming.repositories.CategoryRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.MovieService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;
    private final CategoryRepository categoryRepository;
    private final MapperUtils mapper;

    public MovieServiceImpl(MovieRepository repository,
                            CategoryRepository categoryRepository,
                            MapperUtils mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public MovieResponseDto create(MovieRequestDto requestDto) {
        try {

            List<Category> categoriesList = categoryRepository
                    .findAllById(requestDto.getCategoriesIds());

            Set<Category> categorySet = new HashSet<>(categoriesList);

            Movie movie = mapper.parseObject(requestDto, Movie.class);
            movie.setCategories(categorySet);
            movie.setRating(0.0);
            movie.setRatingsAmount(0);

            Movie savedMovie = repository.save(movie);

            MovieRequestDto movieDto = mapper.parseObject(savedMovie, MovieRequestDto.class);

            MovieResponseDto movieResponseDto = mapper.parseObject(movieDto, MovieResponseDto.class);
            return movieResponseDto;
        } catch (DataIntegrityViolationException ex) {
            throw new SqlBadRequestException(("Ocorreu um erro: " + ex.getRootCause().getMessage()));
        }
    }

    @Override
    public List<MovieResponseDto> getAll() {
        List<Movie> movies = repository.findAll();
        List<MovieResponseDto> movieDtos = mapper.parseListObjects(movies, MovieResponseDto.class);
        return movieDtos;
    }

    @Override
    public MovieResponseDto getById(Long id) {

        Movie movie = getMovie(id).get();

        MovieResponseDto movieDto = mapper.parseObject(movie, MovieResponseDto.class);

        return movieDto;
    }

    @Override
    public MovieResponseDto update(MovieRequestDto requestDto, Long id) {
        try {
            List<Category> categories = categoryRepository
                    .findAllById(requestDto.getCategoriesIds());

            Set<Category> newCategories = new HashSet<>(categories);

            Movie movie = getMovie(id).get();
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
        Movie movie = getMovie(id).get();
        Integer ratingsAmount = movie.getRatingsAmount();

        if (ratingsAmount > 0) {
            Double rating = calculateRating(movie.getRating(), movieRatingDto.getRating());

            movie.setRating(rating);
        } else {
            movie.setRating(movieRatingDto.getRating());
        }

        movie.setRatingsAmount(movie.getRatingsAmount() + 1);

        repository.save(movie);

        return new ApiMessageResponseDto("Avaliado com sucesso");
    }

    private Double calculateRating(Double previousRating, Double currentRating) {
        return (previousRating + currentRating) / 2;
    }

    private Movie updateMovie(Movie movie,
                              MovieRequestDto requestDto) {
        movie.setId(movie.getId());
        movie.setCreatedAt(movie.getCreatedAt());
        movie.setName(requestDto.getName());
        movie.setDescription(requestDto.getDescription());
        movie.setDuration(requestDto.getDuration());
        movie.setMovieUrl(requestDto.getMovieUrl());
        movie.setPosterUrl(requestDto.getPosterUrl());
        movie.setCategories(requestDto.getCategories());
        return movie;
    }

    private Optional<Movie> getMovie(Long id) {
        return Optional.ofNullable(repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um filme com o ID  informado")));
    }

}
