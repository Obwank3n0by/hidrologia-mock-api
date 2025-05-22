#!/bin/bash

# Script de build para Hidrología Mock API
echo "🌊 Compilando Hidrología Mock API..."

# Verificar Java
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado"
    exit 1
fi

# Verificar Maven
if ! command -v mvn &> /dev/null && ! [ -x "./mvnw" ]; then
    echo "❌ Maven no está disponible"
    exit 1
fi

# Usar Maven wrapper si está disponible
if [ -x "./mvnw" ]; then
    MAVEN_CMD="./mvnw"
else
    MAVEN_CMD="mvn"
fi

echo "📦 Limpiando proyecto..."
$MAVEN_CMD clean

echo "🔧 Compilando aplicación..."
$MAVEN_CMD package -DskipTests=true

if [ $? -eq 0 ]; then
    echo "✅ Compilación exitosa!"
    echo "🚀 Para ejecutar: java -jar target/quarkus-app/quarkus-run.jar"
    echo "🌐 Swagger UI estará en: http://localhost:8080/swagger-ui"
else
    echo "❌ Error en la compilación"
    exit 1
fi
