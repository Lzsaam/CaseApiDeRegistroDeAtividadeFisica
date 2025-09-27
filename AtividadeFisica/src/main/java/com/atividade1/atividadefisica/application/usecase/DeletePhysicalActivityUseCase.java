package com.atividade1.atividadefisica.application.usecase;

import com.atividade1.atividadefisica.domain.exception.PhysicalActivityNotFoundException;
import com.atividade1.atividadefisica.domain.repository.PhysicalActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class DeletePhysicalActivityUseCase {

    private final PhysicalActivityRepository repository;

    public DeletePhysicalActivityUseCase(PhysicalActivityRepository repository) {
        this.repository = repository;
    }

    public void execute(Long id) {
        if (!repository.existsById(id)) {
            throw new PhysicalActivityNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
