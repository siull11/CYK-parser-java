# CYK-parser-java
This is a parser for context-free grammars on CNF.
 FIXA create jar COMMANDS + compile and execute commands + run jar commands för alla mains!!!
 FIXA oxå exempel mm från rapport 1!!!
### Command to run jar file:
`java -XX:CompileThreshold=1 -jar CYK-parser-java.jar r n d` <br>
Where r is the number of runs, n is the max length of strings, d is the distance between sequential strings.
The program demands a folder named **'results'** in the current directory.

### Command to run plotting script: (UPDATERA DENNA!!!)
`python3 plot.py <path>` <br>
Where path is the path to the folder containing the .csv data files.
Path is optional and if not set the path will be: **'../results'**.

#### Ex commands:
`python3 CYK-parser-java/plotting/plot.py -t "Dyck language wiht input on the form ((...))" -f bu-dyck-inside.csv td-dyck-inside.csv na-dyck-inside.csv -l bottom-up top-down naive -p "results small"` <br>
