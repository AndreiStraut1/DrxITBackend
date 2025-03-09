package com.drxproject.live.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "boms")
public class Bom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bom_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "bom", cascade = CascadeType.ALL)
    private List<BomMaterial> bomMaterials;

    @JsonIgnore
    @OneToOne(mappedBy = "bom", cascade = CascadeType.ALL)
    private Product product;

    public Bom() {
    }

    public Bom(String name, List<BomMaterial> bomMaterials, Product product) {
        this.name = name;
        this.bomMaterials = bomMaterials;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BomMaterial> getBomMaterials() {
        return bomMaterials;
    }

    public void setBomMaterials(List<BomMaterial> bomMaterials) {
        this.bomMaterials = bomMaterials;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
