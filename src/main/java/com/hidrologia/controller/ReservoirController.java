package com.hidrologia.controller;

import com.hidrologia.model.Reservoir;
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

@Path("/api/reservoirs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Reservoirs API", description = "Operaciones para consultar información de embalses y reservorios")
public class ReservoirController {

    @Inject
    HidrologiaService hidrologiaService;

    @GET
    @Operation(summary = "Obtener todos los embalses", 
               description = "Retorna una lista completa de todos los embalses y reservorios")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Lista de embalses obtenida exitosamente",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = Reservoir.class))),
        @APIResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response getAllReservoirs() {
        try {
            List<Reservoir> reservoirs = hidrologiaService.getAllReservoirs();
            return Response.ok(reservoirs).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener embalses: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener embalse por ID", 
               description = "Retorna la información detallada de un embalse específico")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Embalse encontrado",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = Reservoir.class))),
        @APIResponse(responseCode = "404", description = "Embalse no encontrado"),
        @APIResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response getReservoirById(@PathParam("id") String reservoirId) {
        try {
            Optional<Reservoir> reservoir = hidrologiaService.getReservoirById(reservoirId);
            if (reservoir.isPresent()) {
                return Response.ok(reservoir.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Embalse con ID " + reservoirId + " no encontrado")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener embalse: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/use/{primaryUse}")
    @Operation(summary = "Obtener embalses por uso principal", 
               description = "Retorna embalses filtrados por su uso principal (HIDROELECTRICA, ABASTECIMIENTO, RIEGO, etc.)")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Embalses filtrados por uso obtenidos exitosamente",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = Reservoir.class))),
        @APIResponse(responseCode = "400", description = "Uso principal inválido"),
        @APIResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response getReservoirsByPrimaryUse(@PathParam("primaryUse") String primaryUse) {
        try {
            Reservoir.PrimaryUse use = Reservoir.PrimaryUse.valueOf(primaryUse.toUpperCase());
            List<Reservoir> reservoirs = hidrologiaService.getReservoirsByPrimaryUse(use);
            return Response.ok(reservoirs).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Uso principal inválido: " + primaryUse + 
                           ". Valores permitidos: HIDROELECTRICA, ABASTECIMIENTO, RIEGO, CONTROL_AVENIDAS, RECREATIVO, INDUSTRIAL")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al filtrar por uso: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/status/{status}")
    @Operation(summary = "Obtener embalses por estado operativo", 
               description = "Retorna embalses filtrados por su estado operativo (OPERATIVO, MANTENIMIENTO, FUERA_SERVICIO, EMERGENCIA)")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Embalses filtrados por estado obtenidos exitosamente",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = Reservoir.class))),
        @APIResponse(responseCode = "400", description = "Estado operativo inválido"),
        @APIResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response getReservoirsByStatus(@PathParam("status") String status) {
        try {
            Reservoir.OperationalStatus operationalStatus = Reservoir.OperationalStatus.valueOf(status.toUpperCase());
            List<Reservoir> reservoirs = hidrologiaService.getReservoirsByStatus(operationalStatus);
            return Response.ok(reservoirs).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Estado operativo inválido: " + status + 
                           ". Valores permitidos: OPERATIVO, MANTENIMIENTO, FUERA_SERVICIO, EMERGENCIA")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al filtrar por estado: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/statistics")
    @Operation(summary = "Obtener estadísticas del sistema", 
               description = "Retorna estadísticas generales del sistema hidrológico")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente"),
        @APIResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response getSystemStatistics() {
        try {
            String statistics = hidrologiaService.getSystemStatistics();
            return Response.ok(statistics).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener estadísticas: " + e.getMessage())
                    .build();
        }
    }
}
