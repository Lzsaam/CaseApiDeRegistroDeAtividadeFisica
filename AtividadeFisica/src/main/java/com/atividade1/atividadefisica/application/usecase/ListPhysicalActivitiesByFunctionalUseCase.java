package com.atividade1.atividadefisica.application.usecase;

import com.atividade1.atividadefisica.domain.model.PhysicalActivity;
import com.atividade1.atividadefisica.domain.repository.PhysicalActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application use case responsible for retrieving activities filtered by functional identifier.
 */
@Service
public class ListPhysicalActivitiesByFunctionalUseCase {

    private final PhysicalActivityRepository repository;

    public ListPhysicalActivitiesByFunctionalUseCase(PhysicalActivityRepository repository) {
        this.repository = repository;
    }

    public List<PhysicalActivity> execute(String functional) {
        return repository.findByFunctional(functional);
    }
}
