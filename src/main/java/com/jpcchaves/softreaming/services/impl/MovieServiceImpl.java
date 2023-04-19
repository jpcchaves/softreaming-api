package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieDto;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.ICrudService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;

import java.util.List;
import java.util.Optional;

public class MovieServiceImpl implements ICrudService<MovieDto> {

    private final MovieRepository repository;
    private final MapperUtils mapper;

    public MovieServiceImpl(MovieRepository repository,
                            MapperUtils mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MovieDto create(MovieDto requestDto) {
        Movie movie = mapper.parseObject(requestDto, Movie.class);
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

        Movie movie = movieExists(id).get();

        MovieDto movieDto = mapper.parseObject(movie, MovieDto.class);

        return movieDto;
    }

    @Override
    public MovieDto update(MovieDto requestDto, Long id) {
      Movie movie = movieExists(id).get();

      MovieDto movieDto = convertMovieToDto(movie);

      return movieDto;
    }

    @Override
    public void delete(Long id) {
       movieExists(id);
       repository.deleteById(id);
    }

    private MovieDto convertMovieToDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getName(),
                movie.getDescription(),
                movie.getDuration(),
                movie.getReleaseDate(),
                movie.getMovieUrl(),
                movie.getPosterUrl(),
                movie.getCreatedAt()
        );
    }

    private Optional<Movie> movieExists(Long id) {
        return Optional.ofNullable(repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um filme com o ID  informado")));
    }
}
