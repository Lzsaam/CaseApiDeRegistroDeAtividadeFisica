package com.atividade1.atividadefisica.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PhysicalActivityResponse {

    private Long id;

    @JsonProperty("funcional")
    private String functional;

    @JsonProperty("dataHora")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    @JsonProperty("codigoAtividade")
    private String activityCode;

    @JsonProperty("descricaoAtividade")
    private String activityDescription;

    public PhysicalActivityResponse(Long id, String functional, LocalDateTime dateTime, String activityCode, String activityDescription) {
        this.id = id;
        this.functional = functional;
        this.dateTime = dateTime;
        this.activityCode = activityCode;
        this.activityDescription = activityDescription;
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
}
