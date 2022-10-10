#!/bin/bash
declare -r jarPath="./out/artifacts/CYK_parser_java_jar/CYK-parser-java.jar"
declare -r resPath="results"

declare -r file=$1 # Test file
declare -r r=$2 # 10

echo "Testing: $file, runs: $r"

while read -r line; do
  echo "$line"
  IFS=' ' read -r -a args <<< "$line"

  java -XX:CompileThreshold=1 -jar "$jarPath" "${args[1]}" "${args[2]}" "$r" "${args[0]}" "${args[3]}" > "$resPath/${args[4]//[$'\t\r\n']}"
done <"$file"
