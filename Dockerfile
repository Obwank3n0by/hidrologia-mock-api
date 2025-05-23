# Dockerfile simple para S2I (como meteorología)
FROM registry.redhat.io/ubi8/openjdk-17:1.18

# Metadatos para OpenShift
LABEL name="hidrologia-mock-api" \
      version="1.0.0" \
      architecture="x86_64" \
      summary="API Mock de Hidrología desarrollada con Quarkus" \
      description="Microservicio mock para datos hidrológicos que proporciona endpoints REST para desarrollo y testing" \
      maintainer="Equipo de Hidrología <admin@hidrologia.com>" \
      io.k8s.description="API Mock de Hidrología con Quarkus" \
      io.k8s.display-name="Hidrología Mock API" \
      io.openshift.expose-services="8080:http" \
      io.openshift.tags="java,quarkus,api,mock,hidrologia"

# Variables de entorno
ENV LANGUAGE='en_US:en' \
    LANG='en_US.UTF-8' \
    LC_ALL='en_US.UTF-8'

# Configuración específica de Quarkus para OpenShift
ENV QUARKUS_HTTP_HOST=0.0.0.0 \
    QUARKUS_HTTP_PORT=8080

# Configuración optimizada de JVM para contenedores
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 \
                      -Djava.util.logging.manager=org.jboss.logmanager.LogManager \
                      -XX:+UseParallelGC \
                      -XX:MinHeapFreeRatio=10 \
                      -XX:MaxHeapFreeRatio=20 \
                      -XX:GCTimeRatio=4 \
                      -XX:AdaptiveSizePolicyWeight=90 \
                      -XX:+ExitOnOutOfMemoryError"

# Puerto expuesto
EXPOSE 8080

# El build y deployment se maneja por S2I