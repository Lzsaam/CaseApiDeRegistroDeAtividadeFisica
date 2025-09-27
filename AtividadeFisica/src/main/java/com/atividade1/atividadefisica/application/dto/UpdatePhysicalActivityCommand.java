package com.atividade1.atividadefisica.application.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Command object representing updates to an existing physical activity.
 */
public final class UpdatePhysicalActivityCommand {

    private final String functional;
    private final LocalDateTime dateTime;
    private final String activityCode;
    private final String activityDescription;

    public UpdatePhysicalActivityCommand(String functional, LocalDateTime dateTime, String activityCode, String activityDescription) {
        this.functional = Objects.requireNonNull(functional, "functional must not be null");
        this.dateTime = Objects.requireNonNull(dateTime, "dateTime must not be null");
        this.activityCode = Objects.requireNonNull(activityCode, "activityCode must not be null");
        this.activityDescription = Objects.requireNonNull(activityDescription, "activityDescription must not be null");
    }

    public String getFunctional() {
        return functional;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public String getActivityDescription() {
        return activityDescription;
    }
}
