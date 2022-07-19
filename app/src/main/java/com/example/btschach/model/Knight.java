package com.example.btschach.model;

import java.io.Serializable;

public class Knight  implements Chesspiece, Serializable {
    private String color;

    public Knight(String color) {
        this.color = color;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public String getColor() {
        return color;
    }
}
