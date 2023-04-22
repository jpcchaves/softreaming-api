package com.jpcchaves.softreaming.controllers;

import com.jpcchaves.softreaming.payload.dtos.category.CategoryDto;
import com.jpcchaves.softreaming.services.impl.CategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryServiceImpl service;

    public CategoryController(CategoryServiceImpl service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN)")
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(categoryDto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") Long id, @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(service.update(categoryDto, id));
    }

    @PreAuthorize("hasRole('ADMIN)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
