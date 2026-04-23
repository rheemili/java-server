#!/bin/bash 
# Test that the API server is reachable
echo "Testing API connection..."
curl -s http://localhost:8080/health
echo ""

