#!/bin/bash

# Script de desarrollo para Hidrología Mock API
echo "🌊 Iniciando Hidrología Mock API en modo desarrollo..."

# Verificar Maven wrapper
if [ ! -x "./mvnw" ]; then
    echo "❌ Maven wrapper no encontrado"
    exit 1
fi

echo "🔥 Modo desarrollo con hot reload activado"
echo "📍 API: http://localhost:8080"
echo "📚 Swagger: http://localhost:8080/swagger-ui"
echo "💚 Health: http://localhost:8080/q/health"
echo ""
echo "Press Ctrl+C to stop..."

./mvnw compile quarkus:dev
