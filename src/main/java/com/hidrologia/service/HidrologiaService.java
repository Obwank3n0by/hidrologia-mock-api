package com.hidrologia.service;

import com.hidrologia.model.Reservoir;
import com.hidrologia.model.WaterLevel;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class HidrologiaService {

    private final List<WaterLevel> waterLevels;
    private final List<Reservoir> reservoirs;

    public HidrologiaService() {
        this.waterLevels = initializeWaterLevels();
        this.reservoirs = initializeReservoirs();
    }

    // ========== Water Levels Methods ==========
    
    public List<WaterLevel> getAllWaterLevels() {
        return waterLevels;
    }

    public Optional<WaterLevel> getWaterLevelByStationId(String stationId) {
        return waterLevels.stream()
                .filter(level -> level.getStationId().equals(stationId))
                .findFirst();
    }

    public List<WaterLevel> getWaterLevelsByType(WaterLevel.WaterType type) {
        return waterLevels.stream()
                .filter(level -> level.getType() == type)
                .collect(Collectors.toList());
    }

    public List<WaterLevel> getWaterLevelsByAlertStatus(WaterLevel.AlertStatus alertStatus) {
        return waterLevels.stream()
                .filter(level -> level.getAlertStatus() == alertStatus)
                .collect(Collectors.toList());
    }

    // ========== Reservoirs Methods ==========

    public List<Reservoir> getAllReservoirs() {
        return reservoirs;
    }

    public Optional<Reservoir> getReservoirById(String reservoirId) {
        return reservoirs.stream()
                .filter(reservoir -> reservoir.getReservoirId().equals(reservoirId))
                .findFirst();
    }

    public List<Reservoir> getReservoirsByPrimaryUse(Reservoir.PrimaryUse primaryUse) {
        return reservoirs.stream()
                .filter(reservoir -> reservoir.getPrimaryUse() == primaryUse)
                .collect(Collectors.toList());
    }

    public List<Reservoir> getReservoirsByStatus(Reservoir.OperationalStatus status) {
        return reservoirs.stream()
                .filter(reservoir -> reservoir.getStatus() == status)
                .collect(Collectors.toList());
    }

    // ========== Statistics Methods ==========

    public String getSystemStatistics() {
        int totalStations = waterLevels.size();
        int totalReservoirs = reservoirs.size();
        long alertStations = waterLevels.stream()
                .filter(level -> level.getAlertStatus() != WaterLevel.AlertStatus.NORMAL)
                .count();
        
        double avgFillPercentage = reservoirs.stream()
                .mapToDouble(Reservoir::getFillPercentage)
                .average()
                .orElse(0.0);

        return String.format(
                "Sistema Hidrológico - Estadísticas:\n" +
                "- Total de estaciones: %d\n" +
                "- Total de embalses: %d\n" +
                "- Estaciones en alerta: %d\n" +
                "- Promedio de llenado de embalses: %.1f%%\n" +
                "- Última actualización: %s",
                totalStations, totalReservoirs, alertStations, 
                avgFillPercentage, LocalDateTime.now()
        );
    }

    // ========== Health Check ==========

    public boolean isSystemHealthy() {
        long criticalAlerts = waterLevels.stream()
                .filter(level -> level.getAlertStatus() == WaterLevel.AlertStatus.EMERGENCIA)
                .count();
        
        long reservoirsOutOfService = reservoirs.stream()
                .filter(reservoir -> reservoir.getStatus() == Reservoir.OperationalStatus.FUERA_SERVICIO)
                .count();

        return criticalAlerts == 0 && reservoirsOutOfService == 0;
    }

    // ========== Data Initialization ==========

    private List<WaterLevel> initializeWaterLevels() {
        LocalDateTime now = LocalDateTime.now();
        
        return Arrays.asList(
            new WaterLevel(
                "HIDRO_001", 
                "Río Ebro - Zaragoza", 
                2.45, 
                125.8, 
                WaterLevel.WaterType.RIO, 
                WaterLevel.AlertStatus.NORMAL,
                "Zaragoza, Aragón", 
                now.minusMinutes(15), 
                18.5, 
                "Condiciones normales de navegación"
            ),
            new WaterLevel(
                "HIDRO_002", 
                "Río Tajo - Toledo", 
                1.89, 
                78.2, 
                WaterLevel.WaterType.RIO, 
                WaterLevel.AlertStatus.PRECAUCION,
                "Toledo, Castilla-La Mancha", 
                now.minusMinutes(10), 
                19.2, 
                "Nivel ligeramente por debajo de lo normal"
            ),
            new WaterLevel(
                "HIDRO_003", 
                "Río Guadalquivir - Sevilla", 
                3.12, 
                89.5, 
                WaterLevel.WaterType.RIO, 
                WaterLevel.AlertStatus.NORMAL,
                "Sevilla, Andalucía", 
                now.minusMinutes(5), 
                21.8, 
                "Caudal estable, condiciones óptimas"
            ),
            new WaterLevel(
                "HIDRO_004", 
                "Embalse de Buendía", 
                15.67, 
                45.3, 
                WaterLevel.WaterType.EMBALSE, 
                WaterLevel.AlertStatus.ALERTA,
                "Cuenca, Castilla-La Mancha", 
                now.minusMinutes(20), 
                16.9, 
                "Nivel alto debido a lluvias recientes"
            ),
            new WaterLevel(
                "HIDRO_005", 
                "Río Duero - Zamora", 
                2.78, 
                156.7, 
                WaterLevel.WaterType.RIO, 
                WaterLevel.AlertStatus.NORMAL,
                "Zamora, Castilla y León", 
                now.minusMinutes(8), 
                17.3, 
                "Flujo normal hacia Portugal"
            )
        );
    }

    private List<Reservoir> initializeReservoirs() {
        LocalDateTime now = LocalDateTime.now();
        
        return Arrays.asList(
            new Reservoir(
                "EMB_001",
                "Embalse de Mequinenza",
                1534.0,
                980.5,
                63.9,
                Reservoir.PrimaryUse.HIDROELECTRICA,
                Reservoir.OperationalStatus.OPERATIVO,
                "Mequinenza, Zaragoza",
                "Río Ebro",
                1966,
                78.5,
                550.0,
                45.2,
                now.minusMinutes(30),
                "Central hidroeléctrica funcionando a capacidad normal"
            ),
            new Reservoir(
                "EMB_002",
                "Embalse de Buendía",
                1639.0,
                1245.8,
                76.0,
                Reservoir.PrimaryUse.ABASTECIMIENTO,
                Reservoir.OperationalStatus.OPERATIVO,
                "Cuenca, Castilla-La Mancha",
                "Río Guadiela",
                1958,
                78.0,
                340.0,
                32.1,
                now.minusMinutes(25),
                "Reservas para abastecimiento en niveles óptimos"
            ),
            new Reservoir(
                "EMB_003",
                "Embalse de Alcántara",
                3162.0,
                2456.7,
                77.7,
                Reservoir.PrimaryUse.HIDROELECTRICA,
                Reservoir.OperationalStatus.OPERATIVO,
                "Alcántara, Cáceres",
                "Río Tajo",
                1969,
                130.0,
                570.0,
                67.8,
                now.minusMinutes(40),
                "Mayor embalse de España, funcionamiento normal"
            ),
            new Reservoir(
                "EMB_004",
                "Embalse de La Serena",
                3219.0,
                1934.5,
                60.1,
                Reservoir.PrimaryUse.RIEGO,
                Reservoir.OperationalStatus.OPERATIVO,
                "Castuera, Badajoz",
                "Río Zújar",
                1989,
                104.0,
                470.0,
                28.9,
                now.minusMinutes(35),
                "Suministro de agua para regadíos extremeños"
            ),
            new Reservoir(
                "EMB_005",
                "Embalse de Riaño",
                664.0,
                523.4,
                78.8,
                Reservoir.PrimaryUse.ABASTECIMIENTO,
                Reservoir.OperationalStatus.MANTENIMIENTO,
                "Riaño, León",
                "Río Esla",
                1987,
                99.5,
                280.0,
                15.6,
                now.minusMinutes(50),
                "En mantenimiento preventivo programado"
            )
        );
    }
}
