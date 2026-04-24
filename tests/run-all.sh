#!/bin/bash
# Run all test scripts

BASE_DIR="$(dirname "$0")"

for test_file in "$BASE_DIR"/test-*.sh; do
	echo "Running $(basename "$test_file")"
	bash "$test_file"
	echo ""
done

