package com.example.mybestvideo.item;

public class SpinnerItem {
    private String text;
    private int id;

    public SpinnerItem(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return text;
    }
}

