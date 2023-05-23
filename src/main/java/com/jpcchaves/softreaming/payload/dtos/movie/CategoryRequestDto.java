package com.jpcchaves.softreaming.payload.dtos.movie;

import java.util.List;

public class CategoryRequestDto {
    private List<Long> categoryIds;

    public CategoryRequestDto() {
    }

    public CategoryRequestDto(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
