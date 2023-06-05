package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.CategoryRequestDto;
import com.jpcchaves.softreaming.repositories.CategoryRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.RemoveMovieCategoryUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemoveMovieCategoryImpl implements RemoveMovieCategoryUseCase {

    private final MovieUtils movieUtils;
    private final MovieRepository repository;
    private final CategoryRepository categoryRepository;

    public RemoveMovieCategoryImpl(MovieUtils movieUtils,
                                   MovieRepository repository,
                                   CategoryRepository categoryRepository) {
        this.movieUtils = movieUtils;
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ApiMessageResponseDto removeCategory(Long id,
                                                CategoryRequestDto categoryRequestDto) {
        Movie movie = movieUtils.getMovie(id);
        List<Category> categories = categoryRepository.findAllById(categoryRequestDto.getCategoryIds());

        categories.forEach(movie.getCategories()::remove);

        repository.save(movie);
        return new ApiMessageResponseDto("Categoria(s) removida(s) com sucesso");
    }
}
