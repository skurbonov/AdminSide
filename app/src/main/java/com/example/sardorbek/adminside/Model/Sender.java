package com.example.sardorbek.adminside.Model;

/**
 * Created by sardorbek on 4/25/18.
 */

public class Sender {
    public String to;
    public Notification notification;


    public Sender(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
