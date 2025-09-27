package com.atividade1.atividadefisica.application.usecase;

import com.atividade1.atividadefisica.application.dto.RegisterPhysicalActivityCommand;
import com.atividade1.atividadefisica.domain.model.PhysicalActivity;
import com.atividade1.atividadefisica.domain.repository.PhysicalActivityRepository;
import org.springframework.stereotype.Service;

/**
 * Application service responsible for orchestrating the registration of new activities.
 */
@Service
public class RegisterPhysicalActivityUseCase {

    private final PhysicalActivityRepository repository;

    public RegisterPhysicalActivityUseCase(PhysicalActivityRepository repository) {
        this.repository = repository;
    }

    public PhysicalActivity execute(RegisterPhysicalActivityCommand command) {
        PhysicalActivity physicalActivity = PhysicalActivity.builder()
                .functional(command.getFunctional())
                .dateTime(command.getDateTime())
                .activityCode(command.getActivityCode())
                .activityDescription(command.getActivityDescription())
                .build();

        return repository.save(physicalActivity);
    }
}
