package com.rishipm.earneasyquizie.Activities.Model;

public class CategoryModel {
    private String catId, catName, catImage;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public CategoryModel() {    }


    public CategoryModel(String catId, String catName, String catImage) {
        this.catId = catId;
        this.catName = catName;
        this.catImage = catImage;
    }
}
