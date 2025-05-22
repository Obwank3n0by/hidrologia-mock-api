package com.hidrologia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.smallrye.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Schema(description = "Datos de nivel de agua en una estación hidrológica")
public class WaterLevel {

    @Schema(description = "ID único de la estación hidrológica", example = "HIDRO_001")
    @NotBlank(message = "El ID de la estación no puede estar vacío")
    private String stationId;

    @Schema(description = "Nombre de la estación", example = "Río Ebro - Zaragoza")
    @NotBlank(message = "El nombre de la estación no puede estar vacío")
    private String stationName;

    @Schema(description = "Nivel actual del agua en metros", example = "2.45")
    @NotNull(message = "El nivel de agua no puede ser nulo")
    @Positive(message = "El nivel de agua debe ser positivo")
    private Double waterLevel;

    @Schema(description = "Caudal en metros cúbicos por segundo", example = "125.8")
    @Positive(message = "El caudal debe ser positivo")
    private Double flow;

    @Schema(description = "Tipo de fuente de agua")
    private WaterType type;

    @Schema(description = "Estado de alerta hidrológica")
    private AlertStatus alertStatus;

    @Schema(description = "Ubicación de la estación", example = "Zaragoza, Aragón")
    private String location;

    @Schema(description = "Fecha y hora de la última medición")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdated;

    @Schema(description = "Temperatura del agua en grados Celsius", example = "18.5")
    private Double temperature;

    @Schema(description = "Observaciones adicionales", example = "Condiciones normales")
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
