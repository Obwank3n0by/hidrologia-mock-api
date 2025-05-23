package com.hidrologia;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
    info = @Info(
        title = "Hidrología Mock API",
        version = "1.0.0",
        description = "API Mock para sistema de monitoreo hidrológico. " +
                     "Proporciona datos simulados de niveles de agua, embalses y alertas hidrológicas " +
                     "para España. Incluye información de ríos principales, embalses y sistemas de alerta.",
        contact = @Contact(
            name = "Equipo de Hidrología",
            email = "admin@hidrologia.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(url = "/", description = "Servidor local"),
        @Server(url = "https://hidrologia-api.example.com", description = "Servidor de producción")
    },
    tags = {
        @Tag(name = "Water Levels API", description = "Gestión de niveles de agua en estaciones hidrológicas"),
        @Tag(name = "Reservoirs API", description = "Gestión de información de embalses y reservorios")
    }
)