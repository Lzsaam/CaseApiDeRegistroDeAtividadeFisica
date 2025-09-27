package com.atividade1.atividadefisica.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain aggregate representing a physical activity performed by an employee.
 * Uses the Builder pattern to enforce invariants and promote readability.
 */
public class PhysicalActivity {

    private final Long id;
    private final String functional;
    private final LocalDateTime dateTime;
    private final String activityCode;
    private final String activityDescription;

    private PhysicalActivity(Builder builder) {
        this.id = builder.id;
        this.functional = builder.functional;
        this.dateTime = builder.dateTime;
        this.activityCode = builder.activityCode;
        this.activityDescription = builder.activityDescription;
        validateState();
    }

    private void validateState() {
        Objects.requireNonNull(functional, "Functional must not be null");
        Objects.requireNonNull(dateTime, "Date/time must not be null");
        Objects.requireNonNull(activityCode, "Activity code must not be null");
        Objects.requireNonNull(activityDescription, "Activity description must not be null");
    }

    public Long getId() {
        return id;
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

    /**
     * Creates a copy of the domain object with a persisted identifier.
     */
    public PhysicalActivity withId(Long id) {
        return new Builder()
                .id(id)
                .functional(functional)
                .dateTime(dateTime)
                .activityCode(activityCode)
                .activityDescription(activityDescription)
                .build();
    }

    /**
     * Returns a copy of this aggregate with updated data while preserving the identifier.
     */
    public PhysicalActivity withUpdatedDetails(String functional, LocalDateTime dateTime,
                                               String activityCode, String activityDescription) {
        return new Builder()
                .id(id)
                .functional(functional)
                .dateTime(dateTime)
                .activityCode(activityCode)
                .activityDescription(activityDescription)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String functional;
        private LocalDateTime dateTime;
        private String activityCode;
        private String activityDescription;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder functional(String functional) {
            this.functional = functional;
            return this;
        }

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder activityCode(String activityCode) {
            this.activityCode = activityCode;
            return this;
        }

        public Builder activityDescription(String activityDescription) {
            this.activityDescription = activityDescription;
            return this;
        }

        public PhysicalActivity build() {
            return new PhysicalActivity(this);
        }
    }
}
