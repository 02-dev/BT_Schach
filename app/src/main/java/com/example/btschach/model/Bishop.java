package com.example.btschach.model;

import java.io.Serializable;

public class Bishop  implements Chesspiece, Serializable {
    private String color;

    public Bishop(String color) {
        this.color = color;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public String getColor() {
        return color;
    }
}
