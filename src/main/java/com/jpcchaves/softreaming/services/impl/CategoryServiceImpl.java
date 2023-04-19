package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.payload.dtos.category.CategoryDto;
import com.jpcchaves.softreaming.repositories.CategoryRepository;
import com.jpcchaves.softreaming.services.ICrudService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICrudService<CategoryDto> {

    private final CategoryRepository repository;
    private final MapperUtils mapper;

    public CategoryServiceImpl(CategoryRepository repository,
                               MapperUtils mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto create(CategoryDto requestDto) {
        Category category = mapper.parseObject(requestDto, Category.class);
        Category savedCategory = repository.save(category);
        CategoryDto categoryDto = mapper.parseObject(savedCategory, CategoryDto.class);
        return categoryDto;
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
        Category category = getCategory(id).get();
        Category updatedCategory = updateCategory(category, requestDto);

        Category savedCategory = repository.save(updatedCategory);
        CategoryDto categoryDto = mapper.parseObject(savedCategory, CategoryDto.class);
        return categoryDto;
    }

    @Override
    public void delete(Long id) {
        getCategory(id);
        repository.deleteById(id);
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
