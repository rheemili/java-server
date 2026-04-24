#!/bin/bash
# Test POST endpoint

BASE_URL="http://localhost:8080"

# Generate a unique test name Using timestamp
TEST_ITEM="TestItem_$(date +%s)"
echo "Creating test item: $TEST_ITEM"

#Create item and capture both response body and status code
# -w appends the status code after the body, seperated by a newline

OUTPUT=$(curl -s -w "\n%{http_code}" -X POST \
 -d "$TEST_ITEM" "$BASE_URL/items")
RESPONSE=$(echo "$OUTPUT" | head -n -1)
STATUS=$(echo "$OUTPUT" | tail -n 1)

# Check status code
if [ "$STATUS" = "201" ]; then
	echo "PASS: POST returns 201"
else
	echo "FAIL: POST returns $STATUS (expected 201)"
fi

# Check response contains the item name
if echo "$RESPONSE" | grep -q "$TEST_ITEM"; then
	echo "PASS: Response contains item name"
else
	echo "FAIL: Response doesn't contain item name"
	echo " Got: $RESPONSE"
fi

#Extract ID from response (basic parsing)
# Response format: {"id": "X", "name": "..."}
ITEM_ID=$(echo "$RESPONSE" | grep -o '"id": "[^"]*"' | cut -d '"' -f4)

if [ -n "$ITEM_ID" ]; then
	echo "PASS: Response contains ID ($ITEM_ID)"

	#Verify we can retrieve the item
	RETRIEVE_STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
 "$BASE_URL/items/$ITEM_ID")
if [ "$RETRIEVE_STATUS" = "200" ]; then
	echo "PASS: Can retrieve created item"
   else
	echo "FAIL: Cannot retrieve created item ($RETRIEVE_STATUS)"
   fi
else
	echo "FAIL: Could not extract ID from response"
fi
