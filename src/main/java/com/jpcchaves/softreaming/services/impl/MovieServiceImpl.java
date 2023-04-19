package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieDto;
import com.jpcchaves.softreaming.repositories.CategoryRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.ICrudService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements ICrudService<MovieDto> {

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
    public MovieDto create(MovieDto requestDto) {
        Category category = categoryRepository
                .findById(requestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado uma categoria com o ID informado: " + requestDto.getCategoryId()));

        Movie movie = mapper.parseObject(requestDto, Movie.class);
        movie.getCategories().add(category);
        Movie savedMovie = repository.save(movie);

        MovieDto movieDto = mapper.parseObject(savedMovie, MovieDto.class);

        return movieDto;
    }

    @Override
    public List<MovieDto> getAll() {
        List<Movie> movies = repository.findAll();
        List<MovieDto> movieDtos = mapper.parseListObjects(movies, MovieDto.class);
        return movieDtos;
    }

    @Override
    public MovieDto getById(Long id) {

        Movie movie = getMovie(id).get();

        MovieDto movieDto = mapper.parseObject(movie, MovieDto.class);

        return movieDto;
    }

    @Override
    public MovieDto update(MovieDto requestDto, Long id) {
      Movie movie = getMovie(id).get();

      Movie updatedMovie = updateMovie(movie, requestDto);

      Movie savedMovie = repository.save(updatedMovie);
      MovieDto movieDto = mapper.parseObject(savedMovie, MovieDto.class);

      return movieDto;
    }

    private Movie updateMovie(Movie movie,
                              MovieDto requestDto) {
        movie.setId(movie.getId());
        movie.setCreatedAt(movie.getCreatedAt());
        movie.setName(requestDto.getName());
        movie.setDescription(requestDto.getDescription());
        movie.setDuration(requestDto.getDuration());
        movie.setMovieUrl(requestDto.getMovieUrl());
        movie.setPosterUrl(requestDto.getPosterUrl());
        return movie;
    }

    @Override
    public void delete(Long id) {
        getMovie(id);
        repository.deleteById(id);
    }

    private Optional<Movie> getMovie(Long id) {
        return Optional.ofNullable(repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um filme com o ID  informado")));
    }
}
