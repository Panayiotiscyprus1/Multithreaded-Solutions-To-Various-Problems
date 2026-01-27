#!/usr/bin/env bash
set -euo pipefail

RUNS=10

exe="${1:-queens}"

# If no path given, assume current directory
if [[ "$exe" != */* ]]; then
  exe="./$exe"
fi

if [[ ! -x "$exe" ]]; then
  echo "Error: '$exe' is not executable"
  exit 1
fi

sum=0

for i in $(seq 1 "$RUNS"); do
  echo "Run $i..."
  out="$("$exe")"
  echo "$out"

  t=$(echo "$out" \
      | awk -F': ' '/WALL TIME \(sequential\):/ {print $2}' \
      | awk '{print $1}')

  sum=$((sum + t))
done

avg=$((sum / RUNS))
echo "Average WALL TIME (sequential): $avg microseconds over $RUNS runs"
