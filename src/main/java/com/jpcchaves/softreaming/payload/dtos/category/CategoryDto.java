package com.jpcchaves.softreaming.payload.dtos.category;


public class CategoryDto {
    private Long id;
    private String category;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
