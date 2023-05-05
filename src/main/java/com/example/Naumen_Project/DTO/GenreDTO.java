package com.example.Naumen_Project.DTO;

public class GenreDTO {
    private String name;
    private String slug;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "GenreDTO{" +
                "name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}
