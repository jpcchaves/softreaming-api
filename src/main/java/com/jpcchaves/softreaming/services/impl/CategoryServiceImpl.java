package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.exceptions.SqlBadRequestException;
import com.jpcchaves.softreaming.payload.dtos.category.CategoryDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseMinDto;
import com.jpcchaves.softreaming.repositories.CategoryRepository;
import com.jpcchaves.softreaming.services.CategoryService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService<CategoryDto, CategoryDto> {

    private final CategoryRepository repository;
    private final MapperUtils mapper;

    public CategoryServiceImpl(CategoryRepository repository,
                               MapperUtils mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto create(CategoryDto requestDto) {
        try {
            Category category = mapper.parseObject(requestDto, Category.class);
            Category savedCategory = repository.save(category);
            CategoryDto categoryDto = mapper.parseObject(savedCategory, CategoryDto.class);
            return categoryDto;
        } catch (
                DataIntegrityViolationException ex) {
            throw new SqlBadRequestException(("Ocorreu um erro: " + ex.getRootCause().getMessage()));
        }
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = repository.findAll();
        List<CategoryDto> categoryDtos = mapper.parseListObjects(categories, CategoryDto.class);
        return categoryDtos;
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = getCategory(id).get();
        CategoryDto categoryDto = mapper.parseObject(category, CategoryDto.class);

        return categoryDto;
    }

    @Override
    public CategoryDto update(CategoryDto requestDto, Long id) {
        try {
            Category category = getCategory(id).get();
            Category updatedCategory = updateCategory(category, requestDto);

            Category savedCategory = repository.save(updatedCategory);
            CategoryDto categoryDto = mapper.parseObject(savedCategory, CategoryDto.class);

            return categoryDto;
        } catch (DataIntegrityViolationException ex) {
            throw new SqlBadRequestException(("Ocorreu um erro: " + ex.getRootCause().getMessage()));
        }
    }

    @Override
    public void delete(Long id) {
        getCategory(id);
        repository.deleteById(id);
    }

    @Override
    public Set<MovieResponseMinDto> getAllMoviesByCategoryId(Long categoryId) {
        var category = repository.findById(categoryId).orElseThrow(() -> new BadRequestException(""));
        return mapper.parseSetObjects(category.getMovies(), MovieResponseMinDto.class);
    }

    private Category updateCategory(Category category,
                                    CategoryDto categoryDto) {
        category.setId(category.getId());
        category.setCategory(categoryDto.getCategory());
        return category;
    }

    private Optional<Category> getCategory(Long id) {
        return Optional.ofNullable(repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um filme com o ID  informado")));
    }
}
