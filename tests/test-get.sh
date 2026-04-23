#!/bin/bash
# Test GET endpoints

# Variables
BASE_URL="http://localhost:8080"
ITEMS_ENDPOINT="$BASE_URL/items"

echo "Testing GET /items"
echo "URL: $ITEMS_ENDPOINT"
echo ""

# Store curl ouput in a variable using command substitution
RESPONSE=$(curl -s "$ITEMS_ENDPOINT")

echo "Response"
echo "$RESPONSE"
