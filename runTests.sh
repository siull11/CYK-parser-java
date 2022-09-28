#!/bin/bash
#echo "TD inside"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt t small-dyck-inside.txt 10 > results/td-small-dyck-inside.csv
#echo "TD repeat"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt t small-dyck-repeat.txt 10 > results/td-small-dyck-repeat.csv
#echo "TD repeat after"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt t small-dyck-repeat-after.txt 10 > results/td-small-dyck-repeat-after.csv
#echo "TD repeat before"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt t small-dyck-repeat-before.txt 10 > results/td-small-dyck-repeat-before.csv
#echo "TD stupid"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt t small-stupid.txt 10 > results/td-small-stupid.csv
#
#echo "BU inside"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt b small-dyck-inside.txt 10 > results/bu-small-dyck-inside.csv
#echo "BU repeat"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt b small-dyck-repeat.txt 10 > results/bu-small-dyck-repeat.csv
#echo "BU repeat after"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt b small-dyck-repeat-after.txt 10 > results/bu-small-dyck-repeat-after.csv
#echo "BU repeat before"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt b small-dyck-repeat-before.txt 10 > results/bu-small-dyck-repeat-before.csv
#echo "BU stupid"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt b small-stupid.txt 10 > results/bu-small-stupid.csv
#
#echo "Na inside"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt n small-dyck-inside.txt 10 > results/na-small-dyck-inside.csv
#echo "Na repeat"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt n small-dyck-repeat.txt 10 > results/na-small-dyck-repeat.csv
#echo "Na repeat after"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt n small-dyck-repeat-after.txt 10 > results/na-small-dyck-repeat-after.csv
#echo "Na repeat before"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt n small-dyck-repeat-before.txt 10 > results/na-small-dyck-repeat-before.csv
#echo "Na stupid"
#java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt n small-stupid.txt 10 > results/na-small-stupid.csv

echo "Na repeat"
java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt n dyck-repeat.txt 10 > results/na-dyck-repeat.csv