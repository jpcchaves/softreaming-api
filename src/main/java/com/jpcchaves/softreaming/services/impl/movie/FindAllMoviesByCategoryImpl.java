package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieByBestRatedDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponsePaginatedDto;
import com.jpcchaves.softreaming.repositories.CategoryRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.FindAllMoviesByCategoryUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllMoviesByCategoryImpl implements FindAllMoviesByCategoryUseCase {

    private final MovieUtils movieUtils;
    private final MovieRepository repository;
    private final CategoryRepository categoryRepository;

    public FindAllMoviesByCategoryImpl(MovieUtils movieUtils,
                                       MovieRepository repository,
                                       CategoryRepository categoryRepository) {
        this.movieUtils = movieUtils;
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MovieResponsePaginatedDto<?> findAllMoviesByCategory(Pageable pageable,
                                                                Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new BadRequestException("Categoria não encontrada com o ID informado: " + categoryId));

        Page<Movie> movies = repository.findAllByCategories(pageable, category);
        List<MovieByBestRatedDto> movieByBestRatedDto = movieUtils.buildMovieListResponse(movies.getContent());

        return movieUtils.buildMovieResponsePaginatedDto(movieByBestRatedDto, movies);
    }
}
