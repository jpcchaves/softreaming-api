package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.SqlBadRequestException;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRequestDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;
import com.jpcchaves.softreaming.repositories.CategoryRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.UpdateMovieUseCase;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UpdateMovieImpl implements UpdateMovieUseCase {
    private final MapperUtils mapper;
    private final MovieUtils movieUtils;
    private final MovieRepository repository;
    private final CategoryRepository categoryRepository;

    public UpdateMovieImpl(MapperUtils mapper,
                           MovieUtils movieUtils,
                           MovieRepository repository,
                           CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.movieUtils = movieUtils;
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MovieResponseDto update(MovieRequestDto requestDto,
                                   Long id) {
        try {
            List<Category> categories = categoryRepository
                    .findAllById(requestDto.getCategoriesIds());

            Set<Category> newCategories = new HashSet<>(categories);

            Movie movie = movieUtils.getMovie(id);
            Movie updatedMovie = movieUtils.updateMovie(movie,
                    requestDto);
            updatedMovie.setCategories(newCategories);

            Movie savedMovie = repository.save(updatedMovie);

            return mapper.parseObject(savedMovie,
                    MovieResponseDto.class);
        } catch (DataIntegrityViolationException ex) {
            throw new SqlBadRequestException(("Ocorreu um erro: " + ex.getRootCause().getMessage()));
        }
    }
}
