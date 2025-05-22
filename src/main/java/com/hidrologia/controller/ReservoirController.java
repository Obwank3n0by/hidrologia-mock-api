package com.hidrologia.controller;

import com.hidrologia.model.Reservoir;
import com.hidrologia.service.HidrologiaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/api/reservoirs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservoirController {

    @Inject
    HidrologiaService hidrologiaService;

    @GET
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