# CYK-parser-java
This is a parser for context free grammars on CNF.

### Command to run jar file:
`java -XX:CompileThreshold=1 -jar CYK-parser-java.jar r n d` <br>
Where r is the number of runs, n is the max length of strings, d is the distance between sequential strings.
The program demands a folder named **'results'** in the current directory.

### Command to run plotting script:
`python3 plot.py <path>`
Where path is the path to the folder containing the .csv data files.
Path is optional and if not set the path will be: **'../results'**.