#!/bin/bash

# Script para ejecutar con Docker
IMAGE_NAME="hidrologia-mock-api"
CONTAINER_NAME="hidrologia-api"
PORT="8080"

echo "🐳 Ejecutando Hidrología Mock API con Docker..."

# Verificar si el contenedor ya existe
if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
    echo "🛑 Deteniendo contenedor existente..."
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
fi

# Construir imagen
echo "🔨 Construyendo imagen Docker..."
docker build -t $IMAGE_NAME .

if [ $? -eq 0 ]; then
    echo "✅ Imagen construida exitosamente"
    
    # Ejecutar contenedor
    echo "🚀 Ejecutando contenedor..."
    docker run -d \
        --name $CONTAINER_NAME \
        -p $PORT:8080 \
        $IMAGE_NAME
    
    echo "🌊 Hidrología API ejecutándose en:"
    echo "📍 API: http://localhost:$PORT"
    echo "📚 Swagger: http://localhost:$PORT/swagger-ui"
    echo "💚 Health: http://localhost:$PORT/q/health"
    echo ""
    echo "Para ver logs: docker logs -f $CONTAINER_NAME"
    echo "Para detener: docker stop $CONTAINER_NAME"
else
    echo "❌ Error construyendo imagen Docker"
    exit 1
fi
