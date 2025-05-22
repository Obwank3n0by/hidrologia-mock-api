# 🌊 Hidrología Mock API

API Mock para sistema de monitoreo hidrológico desarrollada con Quarkus. Proporciona datos simulados de niveles de agua, embalses y alertas hidrológicas para España.

## 🚀 Características

- **API REST completa** con endpoints para niveles de agua y embalses
- **Documentación OpenAPI 3.0.2+** compatible con Swagger UI
- **Datos hidrológicos realistas** de ríos y embalses españoles
- **Sistema de alertas** hidrológicas (NORMAL, PRECAUCIÓN, ALERTA, EMERGENCIA)
- **Health checks** integrados
- **Optimizado para OpenShift** con S2I

## 📋 Prerequisitos

- Java 17+
- Maven 3.8+
- Docker (opcional, para contenedores)
- OpenShift CLI (opcional, para despliegue)

## 🛠️ Instalación y Ejecución

### Desarrollo Local

```bash
# Compilar la aplicación
./mvnw clean package

# Ejecutar en modo desarrollo
./mvnw compile quarkus:dev

# La aplicación estará disponible en:
# - API: http://localhost:8080
# - Swagger UI: http://localhost:8080/swagger-ui
# - Health: http://localhost:8080/q/health
```

### Producción

```bash
# Compilar para producción
./mvnw clean package -Dquarkus.package.type=uber-jar

# Ejecutar JAR
java -jar target/hidrologia-mock-api-1.0.0-runner.jar
```

### Docker

```bash
# Construir imagen
docker build -t hidrologia-mock-api .

# Ejecutar contenedor
docker run -p 8080:8080 hidrologia-mock-api
```

## 🔗 Endpoints Principales

### Water Levels API (`/api/water`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/levels` | Todos los niveles de agua |
| GET | `/levels/{stationId}` | Nivel por estación específica |
| GET | `/levels/type/{type}` | Por tipo (RIO, EMBALSE, LAGO, ACUIFERO) |
| GET | `/levels/alerts/{alertStatus}` | Por estado de alerta |
| GET | `/health` | Health check del sistema |

### Reservoirs API (`/api/reservoirs`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/` | Todos los embalses |
| GET | `/{id}` | Embalse específico |
| GET | `/use/{primaryUse}` | Por uso principal |
| GET | `/status/{status}` | Por estado operativo |
| GET | `/statistics` | Estadísticas del sistema |

## 📊 Datos Mock Incluidos

### Estaciones Hidrológicas
- **HIDRO_001**: Río Ebro (Zaragoza)
- **HIDRO_002**: Río Tajo (Toledo)
- **HIDRO_003**: Río Guadalquivir (Sevilla)
- **HIDRO_004**: Embalse de Buendía (Cuenca)
- **HIDRO_005**: Río Duero (Zamora)

### Embalses Principales
- **EMB_001**: Mequinenza (Hidroeléctrica)
- **EMB_002**: Buendía (Abastecimiento)
- **EMB_003**: Alcántara (Hidroeléctrica)
- **EMB_004**: La Serena (Riego)
- **EMB_005**: Riaño (Abastecimiento)

## 🔧 Configuración

### Variables de Entorno

```bash
QUARKUS_HTTP_HOST=0.0.0.0
QUARKUS_HTTP_PORT=8080
JAVA_OPTIONS=-Dquarkus.http.host=0.0.0.0
```

### Propiedades Principales (`application.properties`)

```properties
quarkus.http.port=8080
quarkus.smallrye-openapi.info-title=Hidrología Mock API
quarkus.swagger-ui.always-include=true
quarkus.smallrye-health.ui.enable=true
```

## ☸️ Despliegue en OpenShift

### 1. Preparar repositorio Git

```bash
# Inicializar repositorio
git init
git add .
git commit -m "Initial commit - Hidrología Mock API"

# Subir a GitHub/GitLab (cambiar URL)
git remote add origin https://github.com/TU-USUARIO/hidrologia-mock-api.git
git push -u origin main
```

### 2. Desplegar en OpenShift

```bash
# Crear proyecto
oc new-project hidrologia-system

# Aplicar manifiestos (actualizar URL del repo primero)
oc apply -f openshift/hidrologia-manifests.yaml

# Iniciar build
oc start-build hidrologia-mock-api --follow

# Verificar despliegue
oc get pods
oc get routes
```

### 3. Acceder a la aplicación

```bash
# Obtener URL de la aplicación
ROUTE_URL=$(oc get route hidrologia-mock-api-route -o jsonpath='{.spec.host}')

echo "🌊 Hidrología API: https://$ROUTE_URL"
echo "📚 Swagger UI: https://$ROUTE_URL/swagger-ui"
echo "💚 Health Check: https://$ROUTE_URL/q/health"
```

## 🧪 Ejemplos de Uso

### Obtener todos los niveles de agua

```bash
curl -X GET "http://localhost:8080/api/water/levels" \
     -H "accept: application/json"
```

### Filtrar por tipo de fuente

```bash
curl -X GET "http://localhost:8080/api/water/levels/type/RIO" \
     -H "accept: application/json"
```

### Obtener embalses hidroeléctricos

```bash
curl -X GET "http://localhost:8080/api/reservoirs/use/HIDROELECTRICA" \
     -H "accept: application/json"
```

### Estadísticas del sistema

```bash
curl -X GET "http://localhost:8080/api/reservoirs/statistics" \
     -H "accept: application/json"
```

## 📈 Monitoreo

- **Health Checks**: `/q/health`
- **Métricas**: `/q/metrics`
- **OpenAPI Spec**: `/q/openapi`
- **Swagger UI**: `/swagger-ui`

## 🔍 Solución de Problemas

### Problemas Comunes

1. **Error de compilación**:
   ```bash
   ./mvnw clean install -U
   ```

2. **Puerto en uso**:
   ```bash
   # Cambiar puerto en application.properties
   quarkus.http.port=8081
   ```

3. **Problemas de memoria**:
   ```bash
   export MAVEN_OPTS="-Xmx1024m"
   ```

## 🤝 Contribuir

1. Fork el proyecto
2. Crear una rama (`git checkout -b feature/nueva-funcionalidad`)
3. Commit los cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver `LICENSE` para más detalles.

## 👥 Equipo

- **Desarrolladores**: Equipo de Hidrología
- **Contacto**: admin@hidrologia.com
- **Documentación**: [Wiki del proyecto](https://github.com/TU-USUARIO/hidrologia-mock-api/wiki)

---

🌊 **¡Disfruta monitoreando el agua con nuestra API!** 🌊
