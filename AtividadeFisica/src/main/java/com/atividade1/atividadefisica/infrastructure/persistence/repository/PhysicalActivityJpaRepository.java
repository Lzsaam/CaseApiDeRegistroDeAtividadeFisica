package com.atividade1.atividadefisica.infrastructure.persistence.repository;

import com.atividade1.atividadefisica.infrastructure.persistence.entity.PhysicalActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhysicalActivityJpaRepository extends JpaRepository<PhysicalActivityEntity, Long> {

    List<PhysicalActivityEntity> findByFunctionalIgnoreCase(String functional);
}
