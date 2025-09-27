package com.atividade1.atividadefisica.infrastructure.persistence.adapter;

import com.atividade1.atividadefisica.domain.model.PhysicalActivity;
import com.atividade1.atividadefisica.domain.repository.PhysicalActivityRepository;
import com.atividade1.atividadefisica.infrastructure.persistence.entity.PhysicalActivityEntity;
import com.atividade1.atividadefisica.infrastructure.persistence.mapper.PhysicalActivityMapper;
import com.atividade1.atividadefisica.infrastructure.persistence.repository.PhysicalActivityJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PhysicalActivityRepositoryAdapter implements PhysicalActivityRepository {

    private final PhysicalActivityJpaRepository jpaRepository;

    public PhysicalActivityRepositoryAdapter(PhysicalActivityJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PhysicalActivity save(PhysicalActivity physicalActivity) {
        PhysicalActivityEntity savedEntity = jpaRepository.save(PhysicalActivityMapper.toEntity(physicalActivity));
        return PhysicalActivityMapper.toDomain(savedEntity);
    }

    @Override
    public List<PhysicalActivity> findAll() {
        return jpaRepository.findAll().stream()
                .map(PhysicalActivityMapper::toDomain)
                .toList();
    }

    @Override
    public List<PhysicalActivity> findByFunctional(String functional) {
        return jpaRepository.findByFunctionalIgnoreCase(functional).stream()
                .map(PhysicalActivityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<PhysicalActivity> findById(Long id) {
        return jpaRepository.findById(id)
                .map(PhysicalActivityMapper::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
