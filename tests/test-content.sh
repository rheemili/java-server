#!/bin/bash
#Test reponse content

BASE_URL="http://localhost:8080"

test_contains(){
	local URL="$1"
	local EXPECTED="$2"
	local DESCRIPTION="$3"

	local RESPONSE=$(curl -s "$URL")

	# grep -q returns 0 if found, 1 if not found 
	if echo "$RESPONSE" | grep -q "$EXPECTED"; then
		echo "PASS: $DESCRIPTION"
	else
		echo "FAIL: $DESCRIPTION"
		echo " Expected to find: $EXPECTED"
		echo " Got: $RESPONSE"
	fi
}

test_contains "$BASE_URL/items" '"name":' \ "GET /items returns JSON with name field"
test_contains "$BASE_URL/items/1" '"id": "1"' \ "GET /items/1 returns item with id 1"
test_contains "$BASE_URL/items/1" '"name":' \ "GET /items/1 returns item with name field"

