package com.hidrologia.controller;

import com.hidrologia.model.WaterLevel;
import com.hidrologia.service.HidrologiaService;
import io.smallrye.openapi.annotations.Operation;
import io.smallrye.openapi.annotations.media.Content;
import io.smallrye.openapi.annotations.media.Schema;
import io.smallrye.openapi.annotations.responses.APIResponse;
import io.smallrye.openapi.annotations.responses.APIResponses;
import io.smallrye.openapi.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/api/water")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Water Levels API", description = "Operaciones para consultar niveles de agua en estaciones hidrológicas")
public class WaterController {

    @Inject
    HidrologiaService hidrologiaService;

    @GET
    @Path("/levels")
    @Operation(summary = "Obtener todos los niveles de agua", 
               description = "Retorna una lista completa de todas las estaciones con sus niveles actuales de agua")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Lista de niveles obtenida exitosamente",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = WaterLevel.class))),
        @APIResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Obtener nivel de agua por ID de estación", 
               description = "Retorna el nivel de agua de una estación específica")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Nivel de agua encontrado",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = WaterLevel.class))),
        @APIResponse(responseCode = "404", description = "Estación no encontrada"),
        @APIResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Obtener niveles por tipo de fuente", 
               description = "Retorna niveles de agua filtrados por tipo de fuente (RIO, EMBALSE, LAGO, ACUIFERO)")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Niveles filtrados por tipo obtenidos exitosamente",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = WaterLevel.class))),
        @APIResponse(responseCode = "400", description = "Tipo de fuente inválido"),
        @APIResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Obtener niveles por estado de alerta", 
               description = "Retorna niveles de agua filtrados por estado de alerta (NORMAL, PRECAUCION, ALERTA, EMERGENCIA)")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Niveles filtrados por alerta obtenidos exitosamente",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = WaterLevel.class))),
        @APIResponse(responseCode = "400", description = "Estado de alerta inválido"),
        @APIResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Health check del sistema hidrológico", 
               description = "Verifica el estado general del sistema de monitoreo")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Sistema funcionando correctamente"),
        @APIResponse(responseCode = "503", description = "Sistema con problemas")
    })
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
