package com.example.btschach.model;

import java.io.Serializable;

public class Rook  implements Chesspiece, Serializable {
    private String color;

    public Rook(String color) {
        this.color = color;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public String getColor() {
        return color;
    }
}
