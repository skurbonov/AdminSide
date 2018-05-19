package com.example.sardorbek.adminside.Model;

/**
 * Created by sardorbek on 4/20/18.
 */

public class Book {
    private String title;
    private String author;
    private String iSBN;
    private String publishDate;
    private String pageCount;
    private String description;
    private String image;
    private String categoryId;

    public Book() {
    }

    public Book(String title, String author, String iSBN, String publishDate, String pageCount, String description, String image,String categoryId) {
        this.title = title;
        this.author = author;
        this.iSBN = iSBN;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
        this.description = description;
        this.image = image;
        this.categoryId=categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getiSBN() {
        return iSBN;
    }

    public void setiSBN(String iSBN) {
        this.iSBN = iSBN;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
