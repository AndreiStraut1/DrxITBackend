package com.drxproject.live.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drxproject.live.models.EStage;
import com.drxproject.live.models.Product;
import com.drxproject.live.models.ProductStageHistory;

@Repository
public interface ProductStageHistoryRepository extends JpaRepository<ProductStageHistory, Long> {
    Optional<ProductStageHistory> findTopByProductOrderByStartOfStageDesc(Product product);

    @Query("SELECT psh.product " +
            "FROM ProductStageHistory psh " +
            "WHERE psh.startOfStage = (" +
            "    SELECT MAX(psh2.startOfStage) " +
            "    FROM ProductStageHistory psh2 " +
            "    WHERE psh2.product = psh.product" +
            ") " +
            "AND psh.stage.name = :stage")

    List<Product> findProductsByCurrentStage(@Param("stage") EStage stage);

    List<ProductStageHistory> findByProduct(Product product);

    void deleteByProduct(Product product);

}