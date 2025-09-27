package com.atividade1.atividadefisica.presentation.controller;

import com.atividade1.atividadefisica.application.usecase.DeletePhysicalActivityUseCase;
import com.atividade1.atividadefisica.application.usecase.FindPhysicalActivityByIdUseCase;
import com.atividade1.atividadefisica.application.usecase.ListAllPhysicalActivitiesUseCase;
import com.atividade1.atividadefisica.application.usecase.ListPhysicalActivitiesByFunctionalUseCase;
import com.atividade1.atividadefisica.application.usecase.RegisterPhysicalActivityUseCase;
import com.atividade1.atividadefisica.application.usecase.UpdatePhysicalActivityUseCase;
import com.atividade1.atividadefisica.presentation.dto.PhysicalActivityRequest;
import com.atividade1.atividadefisica.presentation.dto.PhysicalActivityResponse;
import com.atividade1.atividadefisica.presentation.mapper.PhysicalActivityPresenterMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/atividades")
@Validated
public class PhysicalActivityController {

    private final RegisterPhysicalActivityUseCase registerUseCase;
    private final ListAllPhysicalActivitiesUseCase listAllUseCase;
    private final FindPhysicalActivityByIdUseCase findByIdUseCase;
    private final ListPhysicalActivitiesByFunctionalUseCase listByFunctionalUseCase;
    private final UpdatePhysicalActivityUseCase updateUseCase;
    private final DeletePhysicalActivityUseCase deleteUseCase;

    public PhysicalActivityController(RegisterPhysicalActivityUseCase registerUseCase,
                                      ListAllPhysicalActivitiesUseCase listAllUseCase,
                                      FindPhysicalActivityByIdUseCase findByIdUseCase,
                                      ListPhysicalActivitiesByFunctionalUseCase listByFunctionalUseCase,
                                      UpdatePhysicalActivityUseCase updateUseCase,
                                      DeletePhysicalActivityUseCase deleteUseCase) {
        this.registerUseCase = registerUseCase;
        this.listAllUseCase = listAllUseCase;
        this.findByIdUseCase = findByIdUseCase;
        this.listByFunctionalUseCase = listByFunctionalUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<PhysicalActivityResponse> register(@Valid @RequestBody PhysicalActivityRequest request) {
        var command = PhysicalActivityPresenterMapper.toCommand(request);
        var physicalActivity = registerUseCase.execute(command);
        var response = PhysicalActivityPresenterMapper.toResponse(physicalActivity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PhysicalActivityResponse>> listAll() {
        var activities = listAllUseCase.execute();
        return ResponseEntity.ok(PhysicalActivityPresenterMapper.toResponseList(activities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicalActivityResponse> findById(@PathVariable Long id) {
        var activity = findByIdUseCase.execute(id);
        return ResponseEntity.ok(PhysicalActivityPresenterMapper.toResponse(activity));
    }

    @GetMapping("/funcional/{funcional}")
    public ResponseEntity<List<PhysicalActivityResponse>> listByFunctional(@PathVariable @NotBlank String funcional) {
        var activities = listByFunctionalUseCase.execute(funcional);
        return ResponseEntity.ok(PhysicalActivityPresenterMapper.toResponseList(activities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhysicalActivityResponse> update(@PathVariable Long id,
                                                           @Valid @RequestBody PhysicalActivityRequest request) {
        var command = PhysicalActivityPresenterMapper.toUpdateCommand(request);
        var updated = updateUseCase.execute(id, command);
        return ResponseEntity.ok(PhysicalActivityPresenterMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
