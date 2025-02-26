package com.drxproject.live.security.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drxproject.live.models.Product;
import com.drxproject.live.models.ProductStageHistory;
import com.drxproject.live.models.Stage;
import com.drxproject.live.models.EStage;
import com.drxproject.live.models.User;
import com.drxproject.live.models.ERole;
import com.drxproject.live.repositories.ProductRepository;
import com.drxproject.live.repositories.ProductStageHistoryRepository;
import com.drxproject.live.repositories.UserRepository;
import com.drxproject.live.repositories.StageRepository;

@Service
public class ProductStageService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStageHistoryRepository productStageHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StageRepository stageRepository;

    public void moveToNextStage(Long productId, Long userId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (productOpt.isPresent() && userOpt.isPresent()) {
            Product product = productOpt.get();
            User user = userOpt.get();

            // Get the current stage from the product history (this is just an example;
            // you might need to adjust the query to fetch the latest history record)
            ProductStageHistory latestHistory = productStageHistoryRepository
                    .findTopByProductOrderByStartOfStageDesc(product)
                    .orElse(null);
            Stage currentStage = (latestHistory != null) ? latestHistory.getStage() : null;

            // Compute the next stage based on your business logic
            Stage nextStage = determineNextStage(currentStage);

            // Optionally: check if the user is allowed to move to that stage
            if (canMoveToNextStage(user, currentStage)) {
                ProductStageHistory stageHistory = new ProductStageHistory(product, nextStage,
                        new Timestamp(System.currentTimeMillis()), user);
                productStageHistoryRepository.save(stageHistory);
            } else {
                throw new RuntimeException("User does not have permission to move to the next stage.");
            }
        } else {
            throw new RuntimeException("Product or User not found.");
        }
    }

    public void overrideStage(Long productId, EStage newEStage, Long userId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (productOpt.isPresent() && userOpt.isPresent()) {
            Product product = productOpt.get();
            User user = userOpt.get();

            ProductStageHistory latestHistory = productStageHistoryRepository
                    .findTopByProductOrderByStartOfStageDesc(product)
                    .orElse(null);

            Stage currentStage = (latestHistory != null) ? latestHistory.getStage() : null;

            Stage newStage = stageRepository.findByName(newEStage)
                    .orElseThrow(() -> new RuntimeException("Stage not found: " + newEStage));

            if (currentStage != null) {
                if (currentStage.getName() == EStage.CANCEL) {
                    throw new RuntimeException("Product has been cancelled, it's stage cannot be modified.");
                }
                if (currentStage.getName() == EStage.STANDBY && newStage.getName() != EStage.PRODUCTION) {
                    throw new RuntimeException(
                            "Product has been put on standby, it's stage can only be modified to Production");
                }
            }

            if (!canManuallySetStage(user, newStage)) {
                throw new RuntimeException("User does not have permission to set stage to " + newEStage);
            }

            // Create and save a new history record for the stage change
            ProductStageHistory stageHistory = new ProductStageHistory(
                    product, newStage, new Timestamp(System.currentTimeMillis()), user);
            productStageHistoryRepository.save(stageHistory);
        } else {
            throw new RuntimeException("Product or User not found.");
        }
    }

    private boolean canManuallySetStage(User user, Stage newStage) {
        // You may want to customize this logic. For example:
        switch (newStage.getName()) {
            case CANCEL:
            case STANDBY:
                return user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN));
            case PRODUCTION:
                return user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_SELLER));
            default:
                // By default, allow the change if the user has any high privilege role.
                return user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN) ||
                        role.getName().equals(ERole.ROLE_PORTOFOLIO_MANAGER));
        }
    }

    private Stage determineNextStage(Stage currentStage) {
        EStage nextEStage;
        if (currentStage == null) {
            nextEStage = EStage.CONCEPT; // initial stage if none exists
        } else {
            switch (currentStage.getName()) {
                case CONCEPT:
                    nextEStage = EStage.FEASIBILITY;
                    break;
                case FEASIBILITY:
                    nextEStage = EStage.PROJECTION;
                    break;
                case PROJECTION:
                    nextEStage = EStage.PRODUCTION;
                    break;
                default:
                    throw new RuntimeException("No valid next stage found for: " + currentStage.getName());
            }
        }
        // Use the stageRepository to get the Stage entity corresponding to nextEStage
        return stageRepository.findByName(nextEStage)
                .orElseThrow(() -> new RuntimeException("Stage not found: " + nextEStage));
    }

    private boolean canMoveToNextStage(User user, Stage currentStage) {
        switch (currentStage.getName()) {
            case CONCEPT:
                return user.getRoles().stream().anyMatch(
                        role -> role.getName().equals(ERole.ROLE_DESIGNER) || role.getName().equals(ERole.ROLE_ADMIN));
            case FEASIBILITY:
                return user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_PORTOFOLIO_MANAGER)
                        || role.getName().equals(ERole.ROLE_ADMIN));
            case PROJECTION:
                return user.getRoles().stream().anyMatch(
                        role -> role.getName().equals(ERole.ROLE_DESIGNER) || role.getName().equals(ERole.ROLE_ADMIN));
            case PRODUCTION:
                return user.getRoles().stream().anyMatch(
                        role -> role.getName().equals(ERole.ROLE_SELLER) || role.getName().equals(ERole.ROLE_ADMIN));
            case RETREAT:
            case STANDBY:
            case CANCEL:
                return user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN));
            default:
                return false;
        }
    }
}