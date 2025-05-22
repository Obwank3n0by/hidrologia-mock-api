#!/bin/bash

# Script de limpieza para Hidrología Mock API
echo "🧹 Limpiando proyecto Hidrología Mock API..."

# Limpiar Maven
if [ -x "./mvnw" ]; then
    echo "📦 Limpiando build Maven..."
    ./mvnw clean
else
    echo "📦 Limpiando build Maven..."
    mvn clean
fi

# Limpiar Docker (opcional)
read -p "¿Limpiar imágenes Docker? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🐳 Limpiando Docker..."
    docker stop hidrologia-api 2>/dev/null || true
    docker rm hidrologia-api 2>/dev/null || true
    docker rmi hidrologia-mock-api 2>/dev/null || true
fi

# Limpiar logs temporales
echo "📝 Limpiando archivos temporales..."
find . -name "*.log" -delete 2>/dev/null || true
find . -name ".DS_Store" -delete 2>/dev/null || true

echo "✅ Limpieza completada!"
