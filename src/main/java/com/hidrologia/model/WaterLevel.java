package com.hidrologia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class WaterLevel {

    @NotBlank(message = "El ID de la estación no puede estar vacío")
    private String stationId;

    @NotBlank(message = "El nombre de la estación no puede estar vacío")
    private String stationName;

    @NotNull(message = "El nivel de agua no puede ser nulo")
    @Positive(message = "El nivel de agua debe ser positivo")
    private Double waterLevel;

    @Positive(message = "El caudal debe ser positivo")
    private Double flow;

    private WaterType type;
    private AlertStatus alertStatus;
    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdated;

    private Double temperature;
    private String observations;

    // Enums
    public enum WaterType {
        RIO("Río"),
        EMBALSE("Embalse"),
        LAGO("Lago"),
        ACUIFERO("Acuífero");

        private final String displayName;

        WaterType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum AlertStatus {
        NORMAL("Normal"),
        PRECAUCION("Precaución"),
        ALERTA("Alerta"),
        EMERGENCIA("Emergencia");

        private final String displayName;

        AlertStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructor por defecto
    public WaterLevel() {}

    // Constructor completo
    public WaterLevel(String stationId, String stationName, Double waterLevel, Double flow, 
                     WaterType type, AlertStatus alertStatus, String location, 
                     LocalDateTime lastUpdated, Double temperature, String observations) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.waterLevel = waterLevel;
        this.flow = flow;
        this.type = type;
        this.alertStatus = alertStatus;
        this.location = location;
        this.lastUpdated = lastUpdated;
        this.temperature = temperature;
        this.observations = observations;
    }

    // Getters y Setters
    public String getStationId() { return stationId; }
    public void setStationId(String stationId) { this.stationId = stationId; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public Double getWaterLevel() { return waterLevel; }
    public void setWaterLevel(Double waterLevel) { this.waterLevel = waterLevel; }

    public Double getFlow() { return flow; }
    public void setFlow(Double flow) { this.flow = flow; }

    public WaterType getType() { return type; }
    public void setType(WaterType type) { this.type = type; }

    public AlertStatus getAlertStatus() { return alertStatus; }
    public void setAlertStatus(AlertStatus alertStatus) { this.alertStatus = alertStatus; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
}