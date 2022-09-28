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

#### Done plot commands (not user instructions, just placeholder):
Plot Dyck bu:
`python CYK-parser-java/plot.py -t "Dyck language (bottom-up)" -f bu-dyck-inside.csv bu-dyck-repeat.csv bu-dyck-repeat-after.csv bu-dyck-repeat-before.csv -l "bottom-up ((...))" "bottom-up ()...()" "bottom-up ()...()(" "bottom-up )()...()" -p results` <br>
Solution:
`python CYK-parser-java/plot.py -t "Dyck language (bottom-up)" -f bu-dyck-inside.csv bu-dyck-repeat.csv bu-dyck-repeat-after.csv bu-dyck-repeat-before.csv -l "bottom-up ((...))" "bottom-up ()...()" "bottom-up ()...()(" "bottom-up )()...()" -p results -d 3 -x "length n" -y "time/g(n) [s/n^3]"` <br>

Plot Dyck td slow:
`python CYK-parser-java/plot.py -t "Dyck language (top-down, slow cases)" -f td-dyck-inside.csv td-dyck-repeat-after.csv -l "top-down ((...))" "top-down ()...()(" -p results` <br>
Solution:
`python CYK-parser-java/plot.py -t "Dyck language (top-down, slow cases)" -f td-dyck-inside.csv td-dyck-repeat-after.csv -l "top-down ((...))" "top-down ()...()(" -p results -d 3 -x "length n" -y "time/g(n) [s/n^3]"` <br>

Plot Dyck td fast:
`python CYK-parser-java/plot.py -t "Dyck language (top-down, fast cases)" -f td-dyck-repeat.csv td-dyck-repeat-before.csv -l "top-down ()...()" "top-down )()...()" -p results` <br>
Solution:
`python CYK-parser-java/plot.py -t "Dyck language (top-down, fast cases)" -f td-dyck-repeat.csv td-dyck-repeat-before.csv -l "top-down ()...()" "top-down )()...()" -p results -d 3 -x "length n" -y "time/g(n) [s/n^3]"` <br>

Plot stupid (empty language):
`python CYK-parser-java/plot.py -t "Empty language" -f bu-stupid.csv td-stupid.csv -l bottom-up top-down -p results` <br>
Solution:
`python CYK-parser-java/plot.py -t "Empty language" -f bu-stupid.csv td-stupid.csv -l bottom-up top-down -p results -d 3 -x "length n" -y "time/g(n) [s/n^3]"` <br>

Plot Dyck small inside:
`python CYK-parser-java/plot.py -t "Dyck language with small input on the form ((...))" -f bu-dyck-inside.csv td-dyck-inside.csv na-dyck-inside.csv -l bottom-up top-down naive -p "results small"` <br>

Plot Dyck small repeat:
`python CYK-parser-java/plot.py -t "Dyck language with small input on the form ()...()" -f bu-dyck-repeat.csv td-dyck-repeat.csv na-dyck-repeat.csv -l bottom-up top-down naive -p "results small"` <br>

Plot Dyck small repeat after:
`python CYK-parser-java/plot.py -t "Dyck language with small input on the form ()...()(" -f bu-dyck-repeat-after.csv td-dyck-repeat-after.csv na-dyck-repeat-after.csv -l bottom-up top-down naive -p "results small"` <br>

Plot Dyck small repeat before:
`python CYK-parser-java/plot.py -t "Dyck language with small input on the form )()...()" -f bu-dyck-repeat-before.csv td-dyck-repeat-before.csv na-dyck-repeat-before.csv -l bottom-up top-down naive -p "results small"` <br>

Plot Dyck small stupid (empty language):
`python CYK-parser-java/plot.py -t "Empty language with small input" -f bu-stupid.csv td-stupid.csv na-stupid.csv -l bottom-up top-down naive -p "results small"` <br>

Plot Dyck repeat naive + top-down:
`python CYK-parser-java/plot.py -t "Dyck language with input on the form ()...()" -f td-dyck-repeat.csv na-dyck-repeat.csv -l top-down naive -p results` <br>
