package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.CategoryRequestDto;
import com.jpcchaves.softreaming.repositories.CategoryRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.AddMovieCategoryUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddMovieCategoryImpl implements AddMovieCategoryUseCase {

    private final MovieRepository repository;
    private final CategoryRepository categoryRepository;
    private final MovieUtils movieUtils;

    public AddMovieCategoryImpl(MovieRepository repository,
                                CategoryRepository categoryRepository,
                                MovieUtils movieUtils) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.movieUtils = movieUtils;
    }

    @Override
    public ApiMessageResponseDto addCategory(Long id,
                                             CategoryRequestDto categoryRequestDto) {
        Movie movie = movieUtils.getMovie(id);
        List<Category> categories = categoryRepository.findAllById(categoryRequestDto.getCategoryIds());

        movie.getCategories().addAll(categories);

        repository.save(movie);
        return new ApiMessageResponseDto("Categoria(s) adicionada(s) com sucesso");
    }
}
