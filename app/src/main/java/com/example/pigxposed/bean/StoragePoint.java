package com.example.pigxposed.bean;

import java.io.Serializable;

public class StoragePoint implements Serializable {
    public float point_w;
    public float point_x;
    public float point_y;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("StoragePoint [point_x=");
        stringBuilder.append(this.point_x);
        stringBuilder.append(", point_y=");
        stringBuilder.append(this.point_y);
        stringBuilder.append(", point_w=");
        stringBuilder.append(this.point_w);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public StoragePoint(float f, float f2, float f3) {
        this.point_x = f;
        this.point_y = f2;
        this.point_w = f3;
    }

    public float getPoint_x() {
        return this.point_x;
    }

    public void setPoint_x(float f) {
        this.point_x = (float) ((int) f);
    }

    public float getPoint_y() {
        return this.point_y;
    }

    public void setPoint_y(float f) {
        this.point_y = (float) ((int) f);
    }

    public float getPoint_w() {
        return this.point_w;
    }

    public void setPoint_w(float f) {
        this.point_w = (float) ((int) f);
    }
}