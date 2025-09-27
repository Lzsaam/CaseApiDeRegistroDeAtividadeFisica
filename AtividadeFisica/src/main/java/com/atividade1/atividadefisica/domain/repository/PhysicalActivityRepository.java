package com.atividade1.atividadefisica.domain.repository;

import com.atividade1.atividadefisica.domain.model.PhysicalActivity;

import java.util.List;
import java.util.Optional;

/**
 * Abstraction for persisting and retrieving {@link PhysicalActivity} aggregates.
 * Follows the Dependency Inversion Principle allowing infrastructure to be replaced without
 * impacting application or domain layers.
 */
public interface PhysicalActivityRepository {

    PhysicalActivity save(PhysicalActivity physicalActivity);

    List<PhysicalActivity> findAll();

    List<PhysicalActivity> findByFunctional(String functional);

    Optional<PhysicalActivity> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);
}
