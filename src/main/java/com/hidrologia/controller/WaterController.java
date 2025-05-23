package com.hidrologia.controller;

import com.hidrologia.model.WaterLevel;
import com.hidrologia.service.HidrologiaService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
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