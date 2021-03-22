package com.example.tranferviewdata.model;

import java.io.Serializable;

public class DataSerial implements Serializable {
    private int id;
    private String Data;

    public DataSerial(int id, String data) {
        this.id = id;
        Data = data;
    }

    @Override
    public String toString() {
        return "DataSerial{" +
                "id=" + id +
                ", Data='" + Data + '\'' +
                '}';
    }
}
