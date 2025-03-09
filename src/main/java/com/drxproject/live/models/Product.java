package com.drxproject.live.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(name = "description", nullable = false)
    private String description;

    @Min(value = 0, message = "Estimated height must be non-negative")
    @Column(name = "estimated_height", nullable = false)
    private double estimated_height;

    @Min(value = 0, message = "Estimated weight must be non-negative")
    @Column(name = "estimated_weight", nullable = false)
    private double estimated_weight;

    @Min(value = 0, message = "Estimated width must be non-negative")
    @Column(name = "estimated_width", nullable = false)
    private double estimated_width;

    @NotBlank
    @Size(max = 20)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bom_id", referencedColumnName = "bom_id", unique = true)
    private Bom bom;

    public Product() {
    }

    public Product(String description, double estimated_height, double estimated_weight, double estimated_width,
            String name, Bom bom) {
        this.description = description;
        this.estimated_height = estimated_height;
        this.estimated_weight = estimated_weight;
        this.estimated_width = estimated_width;
        this.name = name;
        this.bom = bom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getEstimated_height() {
        return estimated_height;
    }

    public void setEstimated_height(double estimated_height) {
        this.estimated_height = estimated_height;
    }

    public double getEstimated_weight() {
        return estimated_weight;
    }

    public void setEstimated_weight(double estimated_weight) {
        this.estimated_weight = estimated_weight;
    }

    public double getEstimated_width() {
        return estimated_width;
    }

    public void setEstimated_width(double estimated_width) {
        this.estimated_width = estimated_width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bom getBom() {
        return bom;
    }

    public void setBom(Bom bom) {
        this.bom = bom;
        if (bom != null) {
            bom.setProduct(this);
        }
    }
}
