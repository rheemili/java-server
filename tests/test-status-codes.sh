#!/bin/bash

BASE_URL="http://localhost:8080"

test_status(){
    local URL="$1"
    local EXPECTED="$2"
    local DESCRIPTION="$3"

    # -w outputs just the status code, -o discards the body
    local ACTUAL=$(curl -s -o /dev/null -w "%{http_code}" "$URL")

    if [ "$ACTUAL" = "$EXPECTED" ]; then
	     echo "PASS: $DESCRIPTION"
	else
  	     echo "FAIL: $DESCRIPTION (expected $EXPECTED, got $ACTUAL)"
	fi
}

test_status  "$BASE_URL/items" "200" "GET /items returns 200"
test_status  "$BASE_URL/items/1" "200" "GET /items/1 returns 200"
test_status  "$BASE_URL/items/999" "404" "GET /items/999 returns 404"
test_status  "$BASE_URL/nonexistent" "404" "GET /nonexistent returns 404"
