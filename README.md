# CYK-parser-java
This is a parser for context-free grammars on CNF.

### Command to compile & run:
!!!
Parse, RuntTest, TestStrings

### Command for jar file (/files???):
!!! (inkl create jar, run jar)


### Format for grammar file:
(first nt = start nt!)
!!!!

### Format for input string file:
!!!!

### Command to run tests:
`./runAllTests.sh` <br>
Runs a series of tests. <br>

`./runTest.sh <testFile> <runs>` <br>
Runs the tests in testFile (in the tests folder) with amount of runs to average over. <br>
**Ex:** `./runTest.sh tests/tests.txt 10` <br>


### Format for row in test file:
`<method> <grammarFile> <inputFile> <outputFile>` <br>
**Ex:** `n dyck.txt dyck-repeat.txt na-dyck-repeat.csv` <br>
**NOTE:** The test file needs to have an empty row in the end.


### Command to run plotting script: (UPDATERA DENNA!!!)
`python3 plot.py` <br>
**Flags:**
- `-h` or `--help` for help <br>
- `-t` or `--title` to set title, required <br>
- `-f` or `--files` to set list of names of files to plot, required <br>
- `-l` or `--labels` to set list of labels for plots, required <br>
- `-p` or `--path` to set path to files to plot <br>
- `-d` or `--degree` to set degree of x to divide y with for plots, default=0 <br>
- `-x` or `--xlabel` to set xlabel for figure, default='length' <br>
- `-y` or `--ylabel` to set ylabel for figure, default='time [s]' <br>

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
`python CYK-parser-java/plot.py -t "Dyck language with small input on the form ((...))" -f bu-small-dyck-inside.csv td-small-dyck-inside.csv na-small-dyck-inside.csv -l bottom-up top-down naive -p results` <br>

Plot Dyck small repeat:
`python CYK-parser-java/plot.py -t "Dyck language with small input on the form ()...()" -f bu-small-dyck-repeat.csv td-small-dyck-repeat.csv na-small-dyck-repeat.csv -l bottom-up top-down naive -p results` <br>

Plot Dyck small repeat after:
`python CYK-parser-java/plot.py -t "Dyck language with small input on the form ()...()(" -f bu-small-dyck-repeat-after.csv td-small-dyck-repeat-after.csv na-small-dyck-repeat-after.csv -l bottom-up top-down naive -p results` <br>

Plot Dyck small repeat before:
`python CYK-parser-java/plot.py -t "Dyck language with small input on the form )()...()" -f bu-small-dyck-repeat-before.csv td-small-dyck-repeat-before.csv na-small-dyck-repeat-before.csv -l bottom-up top-down naive -p results` <br>

Plot Dyck small stupid (empty language):
`python CYK-parser-java/plot.py -t "Empty language with small input" -f bu-small-stupid.csv td-small-stupid.csv na-small-stupid.csv -l bottom-up top-down naive -p results` <br>

Plot Dyck repeat naive + top-down:
`python CYK-parser-java/plot.py -t "Dyck language with input on the form ()...()" -f td-dyck-repeat.csv na-dyck-repeat.csv -l top-down naive -p results` <br>


**For school computer**
`python3 CYK-parser-java/plot.py -t "Dyck language with small input on the form ((...))" -f bu-small-dyck-inside.csv td-small-dyck-inside.csv na-small-dyck-inside.csv -l bottom-up top-down naive -p "full run" -b 5 -y ""` <br>