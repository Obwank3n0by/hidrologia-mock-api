# Hidrología Mock API - Dockerfile optimizado para OpenShift
FROM registry.access.redhat.com/ubi8/openjdk-17-runtime:1.18

# Metadatos
LABEL name="hidrologia-mock-api" \
      version="1.0.0" \
      description="API Mock para sistema de monitoreo hidrológico" \
      maintainer="admin@hidrologia.com"

# Variables de entorno para Quarkus
ENV QUARKUS_HTTP_HOST=0.0.0.0 \
    QUARKUS_HTTP_PORT=8080 \
    JAVA_OPTS="-Dquarkus.http.host=0.0.0.0"

# Crear directorio de trabajo
WORKDIR /deployments

# Copiar el JAR compilado (debe estar en target/)
COPY target/quarkus-app/lib/ /deployments/lib/
COPY target/quarkus-app/*.jar /deployments/
COPY target/quarkus-app/app/ /deployments/app/
COPY target/quarkus-app/quarkus/ /deployments/quarkus/

# Exponer puerto
EXPOSE 8080

# Health checks
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/q/health || exit 1

# Comando de inicio (compatible con cualquier usuario)
CMD ["java", "-jar", "/deployments/quarkus-run.jar"]
