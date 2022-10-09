#!/bin/bash
declare -r jarPath="./out/artifacts/CYK_parser_java_jar/CYK-parser-java.jar"
declare -r resPath="results"

declare -r file=$1 # Test file
declare -r r=$2 # 10

echo "Testing: $file, runs: $r"

while read line; do # ÄNDRA HÄR???
  echo "$line"
  IFS=' ' read -r -a args <<< "$line"

  java -XX:CompileThreshold=1 -jar "$jarPath" "${args[1]}" "$r" "${args[0]}" "${args[2]}" > "$resPath/${args[3]//[$'\t\r\n']}"
done <"$file"
