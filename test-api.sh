#!/bin/bash

# Script de pruebas básicas para Hidrología Mock API
BASE_URL="http://localhost:8080"

echo "🌊 Probando Hidrología Mock API..."
echo "Base URL: $BASE_URL"
echo ""

# Test 1: Health Check
echo "1. 💚 Health Check..."
curl -s "$BASE_URL/q/health" | jq '.' || echo "❌ Health check falló"
echo ""

# Test 2: Todos los niveles de agua
echo "2. 🌊 Niveles de agua..."
curl -s "$BASE_URL/api/water/levels" | jq 'length' || echo "❌ Niveles de agua falló"
echo ""

# Test 3: Estación específica
echo "3. 📍 Estación HIDRO_001..."
curl -s "$BASE_URL/api/water/levels/HIDRO_001" | jq '.stationName' || echo "❌ Estación específica falló"
echo ""

# Test 4: Todos los embalses
echo "4. 🏭 Embalses..."
curl -s "$BASE_URL/api/reservoirs" | jq 'length' || echo "❌ Embalses falló"
echo ""

# Test 5: Estadísticas
echo "5. 📊 Estadísticas..."
curl -s "$BASE_URL/api/reservoirs/statistics" || echo "❌ Estadísticas falló"
echo ""

# Test 6: OpenAPI
echo "6. 📋 OpenAPI Schema..."
curl -s "$BASE_URL/q/openapi" | jq '.info.title' || echo "❌ OpenAPI falló"
echo ""

echo "✅ Pruebas completadas!"
