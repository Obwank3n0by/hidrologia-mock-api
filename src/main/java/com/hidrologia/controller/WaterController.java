package com.hidrologia.controller;

import com.hidrologia.model.WaterLevel;
import com.hidrologia.service.HidrologiaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/api/water")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WaterController {

    @Inject
    HidrologiaService hidrologiaService;

    @GET
    @Path("/levels")
    public Response getAllWaterLevels() {
        try {
            List<WaterLevel> levels = hidrologiaService.getAllWaterLevels();
            return Response.ok(levels).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener niveles de agua: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/levels/{stationId}")
    public Response getWaterLevelByStationId(@PathParam("stationId") String stationId) {
        try {
            Optional<WaterLevel> level = hidrologiaService.getWaterLevelByStationId(stationId);
            if (level.isPresent()) {
                return Response.ok(level.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Estación con ID " + stationId + " no encontrada")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener nivel de agua: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/levels/type/{type}")
    public Response getWaterLevelsByType(@PathParam("type") String type) {
        try {
            WaterLevel.WaterType waterType = WaterLevel.WaterType.valueOf(type.toUpperCase());
            List<WaterLevel> levels = hidrologiaService.getWaterLevelsByType(waterType);
            return Response.ok(levels).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Tipo de fuente inválido: " + type + ". Valores permitidos: RIO, EMBALSE, LAGO, ACUIFERO")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al filtrar por tipo: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/levels/alerts/{alertStatus}")
    public Response getWaterLevelsByAlertStatus(@PathParam("alertStatus") String alertStatus) {
        try {
            WaterLevel.AlertStatus status = WaterLevel.AlertStatus.valueOf(alertStatus.toUpperCase());
            List<WaterLevel> levels = hidrologiaService.getWaterLevelsByAlertStatus(status);
            return Response.ok(levels).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Estado de alerta inválido: " + alertStatus + ". Valores permitidos: NORMAL, PRECAUCION, ALERTA, EMERGENCIA")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al filtrar por alerta: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/health")
    public Response getWaterSystemHealth() {
        try {
            boolean isHealthy = hidrologiaService.isSystemHealthy();
            if (isHealthy) {
                return Response.ok("Sistema hidrológico funcionando correctamente").build();
            } else {
                return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Sistema hidrológico con alertas críticas")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error en health check: " + e.getMessage())
                    .build();
        }
    }
}