package com.atividade1.atividadefisica.infrastructure.persistence.mapper;

import com.atividade1.atividadefisica.domain.model.PhysicalActivity;
import com.atividade1.atividadefisica.infrastructure.persistence.entity.PhysicalActivityEntity;

/**
 * Mapper responsible for translating between domain aggregates and persistence entities.
 */
public final class PhysicalActivityMapper {

    private PhysicalActivityMapper() {
        // Utility class
    }

    public static PhysicalActivityEntity toEntity(PhysicalActivity physicalActivity) {
        PhysicalActivityEntity entity = new PhysicalActivityEntity();
        entity.setId(physicalActivity.getId());
        entity.setFunctional(physicalActivity.getFunctional());
        entity.setDateTime(physicalActivity.getDateTime());
        entity.setActivityCode(physicalActivity.getActivityCode());
        entity.setActivityDescription(physicalActivity.getActivityDescription());
        return entity;
    }

    public static PhysicalActivity toDomain(PhysicalActivityEntity entity) {
        return PhysicalActivity.builder()
                .id(entity.getId())
                .functional(entity.getFunctional())
                .dateTime(entity.getDateTime())
                .activityCode(entity.getActivityCode())
                .activityDescription(entity.getActivityDescription())
                .build();
    }
}
