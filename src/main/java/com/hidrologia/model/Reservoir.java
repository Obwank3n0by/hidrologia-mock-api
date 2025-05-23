package com.hidrologia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class Reservoir {

    @NotBlank(message = "El ID del embalse no puede estar vacío")
    private String reservoirId;

    @NotBlank(message = "El nombre del embalse no puede estar vacío")
    private String name;

    @NotNull(message = "La capacidad máxima no puede ser nula")
    @Positive(message = "La capacidad máxima debe ser positiva")
    private Double maxCapacity;

    @NotNull(message = "El volumen actual no puede ser nulo")
    @Positive(message = "El volumen actual debe ser positivo")
    private Double currentVolume;

    @Min(value = 0, message = "El porcentaje no puede ser menor a 0")
    @Max(value = 100, message = "El porcentaje no puede ser mayor a 100")
    private Double fillPercentage;

    private PrimaryUse primaryUse;
    private OperationalStatus status;
    private String location;
    private String mainRiver;
    private Integer constructionYear;

    @Positive(message = "La altura debe ser positiva")
    private Double damHeight;

    @Positive(message = "La longitud debe ser positiva")
    private Double damLength;

    private Double outflow;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdated;

    private String observations;

    // Enums
    public enum PrimaryUse {
        HIDROELECTRICA("Hidroeléctrica"),
        ABASTECIMIENTO("Abastecimiento urbano"),
        RIEGO("Riego agrícola"),
        CONTROL_AVENIDAS("Control de avenidas"),
        RECREATIVO("Uso recreativo"),
        INDUSTRIAL("Uso industrial");

        private final String displayName;

        PrimaryUse(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum OperationalStatus {
        OPERATIVO("Operativo"),
        MANTENIMIENTO("En mantenimiento"),
        FUERA_SERVICIO("Fuera de servicio"),
        EMERGENCIA("Emergencia");

        private final String displayName;

        OperationalStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructor por defecto
    public Reservoir() {}

    // Constructor completo
    public Reservoir(String reservoirId, String name, Double maxCapacity, Double currentVolume,
                    Double fillPercentage, PrimaryUse primaryUse, OperationalStatus status,
                    String location, String mainRiver, Integer constructionYear,
                    Double damHeight, Double damLength, Double outflow,
                    LocalDateTime lastUpdated, String observations) {
        this.reservoirId = reservoirId;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentVolume = currentVolume;
        this.fillPercentage = fillPercentage;
        this.primaryUse = primaryUse;
        this.status = status;
        this.location = location;
        this.mainRiver = mainRiver;
        this.constructionYear = constructionYear;
        this.damHeight = damHeight;
        this.damLength = damLength;
        this.outflow = outflow;
        this.lastUpdated = lastUpdated;
        this.observations = observations;
    }

    // Getters y Setters
    public String getReservoirId() { return reservoirId; }
    public void setReservoirId(String reservoirId) { this.reservoirId = reservoirId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Double maxCapacity) { this.maxCapacity = maxCapacity; }

    public Double getCurrentVolume() { return currentVolume; }
    public void setCurrentVolume(Double currentVolume) { this.currentVolume = currentVolume; }

    public Double getFillPercentage() { return fillPercentage; }
    public void setFillPercentage(Double fillPercentage) { this.fillPercentage = fillPercentage; }

    public PrimaryUse getPrimaryUse() { return primaryUse; }
    public void setPrimaryUse(PrimaryUse primaryUse) { this.primaryUse = primaryUse; }

    public OperationalStatus getStatus() { return status; }
    public void setStatus(OperationalStatus status) { this.status = status; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getMainRiver() { return mainRiver; }
    public void setMainRiver(String mainRiver) { this.mainRiver = mainRiver; }

    public Integer getConstructionYear() { return constructionYear; }
    public void setConstructionYear(Integer constructionYear) { this.constructionYear = constructionYear; }

    public Double getDamHeight() { return damHeight; }
    public void setDamHeight(Double damHeight) { this.damHeight = damHeight; }

    public Double getDamLength() { return damLength; }
    public void setDamLength(Double damLength) { this.damLength = damLength; }

    public Double getOutflow() { return outflow; }
    public void setOutflow(Double outflow) { this.outflow = outflow; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
}