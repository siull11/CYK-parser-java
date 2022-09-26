#!/bin/bash
java -XX:CompileThreshold=1 -jar CYK-parser-java.jar dyck.txt t small-dyck-inside.txt 5 > td-small-dyck-inside.csv