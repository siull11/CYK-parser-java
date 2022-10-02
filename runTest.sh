#!/bin/bash
declare -r jarPath="./out/artifacts/CYK_parser_java_jar/CYK-parser-java.jar"
declare -r resPath="results"

declare -r file=$1 # Test file
declare -r g=$2 # dyck.txt
declare -r r=$3 # 10

echo "Testing: $file, grammar: $g, runs: $r"

while read line; do
  echo "$line"
  IFS=' ' read -r -a args <<< "$line"

  java -XX:CompileThreshold=1 -jar "$jarPath" "$g" "$r" "${args[0]}" "${args[1]}" > "$resPath/${args[2]//[$'\t\r\n']}"
done <"$file"
