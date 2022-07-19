package com.example.btschach.model;

import java.io.Serializable;

public class Pawn  implements Chesspiece, Serializable {
    private String color;

    public Pawn(String color) {
        this.color = color;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public String getColor() {
        return color;
    }
}
