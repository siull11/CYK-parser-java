#!/usr/bin/python
import csv
import argparse
import matplotlib.pyplot as plt

def main():
	parser = argparse.ArgumentParser(description='plot header, files to plot (csv), labels for files')
	parser.add_argument('-t', '--title', type=str, required=True)
	parser.add_argument('-f', '--files', type=str, nargs='+', required=True, help="files to plot, same amount and order as labels")
	parser.add_argument('-l', '--labels', type=str, nargs='+', required=True, help="labels for plots, same amount and order as files")
	parser.add_argument('-p', '--path', type=str, help='path for folder location of all files (not including ending \'/\')')
	parser.add_argument('-d', '--degree', type=int, default=0, help='degree of n to divide values with (default value = 0)')
	parser.add_argument('-x', '--xlabel', type=str, default='length')
	parser.add_argument('-y', '--ylabel', type=str, default='time [s]')

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
	plt.xlabel(args.xlabel)
	plt.ylabel(args.ylabel)
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
