package com.drxproject.live.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "materials")
public class Material {
    @Id
    @Column(name = "material_id", nullable = false)
    private String materialNumber;

    @Column(name = "material_description", nullable = false)
    private String materialDescription;

    @Column(name = "height", nullable = false)
    private double height;

    @Column(name = "width", nullable = false)
    private double width;

    @Column(name = "weight", nullable = false)
    private double weight;

    public Material() {
    }

    public Material(String materialNumber, String materialDescription, double height, double width, double weight) {
        this.materialNumber = materialNumber;
        this.materialDescription = materialDescription;
        this.height = height;
        this.width = width;
        this.weight = weight;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
