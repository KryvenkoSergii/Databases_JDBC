package com.softserve.entities;

public class CategoryEntity {

    private int category_id;
    private String name;
    private String last_update;

    public CategoryEntity(int category_id, String name, String last_update) {
        this.category_id = category_id;
        this.name = name;
        this.last_update = last_update;
    }

    public CategoryEntity() {
        this.category_id = -1;
        this.name = "default";
        this.last_update = "default";
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "CategoryEntity [category_id=" + category_id + ", name=" + name + ", last_update=" + last_update + "]";
    }

}
