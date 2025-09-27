package com.atividade1.atividadefisica.application.usecase;

import com.atividade1.atividadefisica.domain.exception.PhysicalActivityNotFoundException;
import com.atividade1.atividadefisica.domain.model.PhysicalActivity;
import com.atividade1.atividadefisica.domain.repository.PhysicalActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class FindPhysicalActivityByIdUseCase {

    private final PhysicalActivityRepository repository;

    public FindPhysicalActivityByIdUseCase(PhysicalActivityRepository repository) {
        this.repository = repository;
    }

    public PhysicalActivity execute(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PhysicalActivityNotFoundException(id));
    }
}
