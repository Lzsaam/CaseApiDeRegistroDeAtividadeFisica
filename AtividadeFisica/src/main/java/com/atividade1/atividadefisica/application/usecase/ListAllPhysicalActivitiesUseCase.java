package com.atividade1.atividadefisica.application.usecase;

import com.atividade1.atividadefisica.domain.model.PhysicalActivity;
import com.atividade1.atividadefisica.domain.repository.PhysicalActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application use case that retrieves all registered physical activities.
 */
@Service
public class ListAllPhysicalActivitiesUseCase {

    private final PhysicalActivityRepository repository;

    public ListAllPhysicalActivitiesUseCase(PhysicalActivityRepository repository) {
        this.repository = repository;
    }

    public List<PhysicalActivity> execute() {
        return repository.findAll();
    }
}
