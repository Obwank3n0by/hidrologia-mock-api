package com.hidrologia;

import io.smallrye.openapi.annotations.OpenAPIDefinition;
import io.smallrye.openapi.annotations.info.Contact;
import io.smallrye.openapi.annotations.info.Info;
import io.smallrye.openapi.annotations.info.License;
import io.smallrye.openapi.annotations.servers.Server;
import io.smallrye.openapi.annotations.tags.Tag;
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
public class HidrologiaApplication extends Application {
    // Configuración automática de Quarkus
}
