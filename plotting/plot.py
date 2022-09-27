#!/usr/bin/python
import sys
import csv
import argparse
import matplotlib.pyplot as plt

def main():
	parser = argparse.ArgumentParser(description='plot header, files to plot (csv), labels for files')
	parser.add_argument('-t', '--title', type=str, required=True)
	parser.add_argument('-f', '--files', type=str, nargs='+', required=True)
	parser.add_argument('-l', '--labels', type=str, nargs='+', required=True)
	parser.add_argument('-p', '--path', type=str, help='path for folder location of all files (not including ending \'/\')')
	parser.add_argument('-d', '--degree', type=int, default=0, help='degree of n to divide values with (default value = 0)')

	args = parser.parse_args()
	print(args)

	if len(args.files) != len(args.labels):
		print('Amount of files needs to mach amount of labels')
		exit(1)

	path = args.path+'/' if args.path else ''

	plt.figure()
	plt.title(args.title)
	for i in range(len(args.files)):
		data = processFileData(path + args.files[i], args.degree)
		plt.plot(data[0], data[1], label=args.labels[i])
	plt.legend()
	plt.ylabel('time [s]')
	plt.xlabel('length')
	plt.show()

def processFileData(filepath, degree):
	file = open(filepath, 'r')
	file.readline()
	lines = csv.reader(file)
	dataX = []
	dataY = []
	for line in lines:
		elementX = int(line[0])
		elementY = float(line[3]) / (elementX ** degree)
		dataX.append(elementX)
		dataY.append(elementY)
	file.close()
	return (dataX, dataY)

if __name__ == '__main__':
	main()


# def main():
# 	if len(sys.argv) == 2:
# 		prefix = sys.argv[1] + '/'
# 	else:
# 		prefix = '../results/'

# 	buDyckInside = getFileData(prefix + 'bu-dyck-inside.csv')
# 	buDyckRepeat = getFileData(prefix + 'bu-dyck-repeat.csv')
# 	buDyckRepeatBefore = getFileData(prefix + 'bu-dyck-repeat-before.csv')
# 	buDyckRepeatAfter = getFileData(prefix + 'bu-dyck-repeat-after.csv')
# 	buStupid = getFileData(prefix + 'bu-stupid.csv')
# 	tdDyckInside = getFileData(prefix + 'td-dyck-inside.csv')
# 	tdDyckRepeat = getFileData(prefix + 'td-dyck-repeat.csv')
# 	tdDyckRepeatBefore = getFileData(prefix + 'td-dyck-repeat-before.csv')
# 	tdDyckRepeatAfter = getFileData(prefix + 'td-dyck-repeat-after.csv')
# 	tdStupid = getFileData(prefix + 'td-stupid.csv')

# 	plt.figure()
# 	plt.title('Dyck language (bottom up)')
# 	plt.plot(buDyckInside[0], buDyckInside[1], label='bottom-up ((...))')
# 	plt.plot(buDyckRepeat[0], buDyckRepeat[1], label='bottom-up ()...()')
# 	plt.plot(buDyckRepeatBefore[0], buDyckRepeatBefore[1], label='bottom-up )()...()')
# 	plt.plot(buDyckRepeatAfter[0], buDyckRepeatAfter[1], label='bottom-up ()...()(')
# 	plt.legend()
# 	plt.ylabel('time')
# 	plt.xlabel('length')

# 	plt.figure()
# 	plt.title('Dyck language (top-down, slow cases)')
# 	plt.plot(tdDyckInside[0], tdDyckInside[1], label='top-down ((...))')
# 	plt.plot(tdDyckRepeatAfter[0], tdDyckRepeatAfter[1], label='top-down ()...()(') #ksk nt denna som e seg???
# 	plt.legend()
# 	plt.ylabel('time')
# 	plt.xlabel('length')

# 	plt.figure()
# 	plt.title('Dyck language (top-down, fast cases)')
# 	plt.plot(tdDyckRepeat[0], tdDyckRepeat[1], label='top-down ()...()')
# 	plt.plot(tdDyckRepeatBefore[0], tdDyckRepeatBefore[1], label='top-down )()...()')
# 	plt.legend()
# 	plt.ylabel('time')
# 	plt.xlabel('length')

# 	plt.figure()
# 	plt.title('Parse times for the \"stupid\" grammar')
# 	plt.plot(buStupid[0], buStupid[1], label='bottom-up')
# 	plt.plot(tdStupid[0], tdStupid[1], label='top-down')
# 	plt.legend()
# 	plt.ylabel('time')
# 	plt.xlabel('length')

# 	plt.show()

# def getFileData(filepath):
# 	file = open(filepath, 'r')
# 	file.readline()
# 	lines = csv.reader(file)
# 	dataX = []
# 	dataY = []
# 	for line in lines:
# 		dataX.append(int(line[0]))
# 		dataY.append(float(line[3]))
# 	file.close()
# 	return (dataX, dataY)