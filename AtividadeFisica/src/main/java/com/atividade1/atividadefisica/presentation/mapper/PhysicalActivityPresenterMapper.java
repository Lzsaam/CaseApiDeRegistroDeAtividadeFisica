package com.atividade1.atividadefisica.presentation.mapper;

import com.atividade1.atividadefisica.application.dto.RegisterPhysicalActivityCommand;
import com.atividade1.atividadefisica.application.dto.UpdatePhysicalActivityCommand;
import com.atividade1.atividadefisica.domain.model.PhysicalActivity;
import com.atividade1.atividadefisica.presentation.dto.PhysicalActivityRequest;
import com.atividade1.atividadefisica.presentation.dto.PhysicalActivityResponse;

import java.util.List;

public final class PhysicalActivityPresenterMapper {

    private PhysicalActivityPresenterMapper() {
        // Utility class
    }

    public static RegisterPhysicalActivityCommand toCommand(PhysicalActivityRequest request) {
        return new RegisterPhysicalActivityCommand(
                request.getFuncional(),
                request.getDataHora(),
                request.getCodigoAtividade(),
                request.getDescricaoAtividade()
        );
    }

    public static PhysicalActivityResponse toResponse(PhysicalActivity physicalActivity) {
        return new PhysicalActivityResponse(
                physicalActivity.getId(),
                physicalActivity.getFunctional(),
                physicalActivity.getDateTime(),
                physicalActivity.getActivityCode(),
                physicalActivity.getActivityDescription()
        );
    }

    public static UpdatePhysicalActivityCommand toUpdateCommand(PhysicalActivityRequest request) {
        return new UpdatePhysicalActivityCommand(
                request.getFuncional(),
                request.getDataHora(),
                request.getCodigoAtividade(),
                request.getDescricaoAtividade()
        );
    }

    public static List<PhysicalActivityResponse> toResponseList(List<PhysicalActivity> activities) {
        return activities.stream()
                .map(PhysicalActivityPresenterMapper::toResponse)
                .toList();
    }
}
