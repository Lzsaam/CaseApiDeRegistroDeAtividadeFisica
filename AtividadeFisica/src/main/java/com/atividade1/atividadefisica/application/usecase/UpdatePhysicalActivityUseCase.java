package com.atividade1.atividadefisica.application.usecase;

import com.atividade1.atividadefisica.application.dto.UpdatePhysicalActivityCommand;
import com.atividade1.atividadefisica.domain.exception.PhysicalActivityNotFoundException;
import com.atividade1.atividadefisica.domain.model.PhysicalActivity;
import com.atividade1.atividadefisica.domain.repository.PhysicalActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdatePhysicalActivityUseCase {

    private final PhysicalActivityRepository repository;

    public UpdatePhysicalActivityUseCase(PhysicalActivityRepository repository) {
        this.repository = repository;
    }

    public PhysicalActivity execute(Long id, UpdatePhysicalActivityCommand command) {
        PhysicalActivity current = repository.findById(id)
                .orElseThrow(() -> new PhysicalActivityNotFoundException(id));

        PhysicalActivity updated = current.withUpdatedDetails(
                command.getFunctional(),
                command.getDateTime(),
                command.getActivityCode(),
                command.getActivityDescription()
        );

        return repository.save(updated);
    }
}
