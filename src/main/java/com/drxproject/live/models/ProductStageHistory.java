package com.drxproject.live.models;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "product_stage_history")
public class ProductStageHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_stage_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @Column(name = "start_of_stage", nullable = false)
    private Timestamp startOfStage;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public ProductStageHistory() {
    }

    public ProductStageHistory(Product product, Stage stage, Timestamp startOfStage, User user) {
        this.product = product;
        this.stage = stage;
        this.startOfStage = startOfStage;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Timestamp getStartOfStage() {
        return startOfStage;
    }

    public void setStartOfStage(Timestamp startOfStage) {
        this.startOfStage = startOfStage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
