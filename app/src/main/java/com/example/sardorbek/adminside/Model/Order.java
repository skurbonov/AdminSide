package com.example.sardorbek.adminside.Model;

/**
 * Created by sardorbek on 4/20/18.
 */

public class Order {
    private String bookId;
    private String bookName;
    private String quantity;

    public Order() {}

    public Order(String bookId, String bookName, String quantity) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
