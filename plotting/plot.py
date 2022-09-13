#!/usr/bin/python
import csv
import matplotlib.pyplot as plt

def main():
	prefix = '../results/'
	buDyckInside = getFileData(prefix + 'bu-dyck-inside.csv')
	buDyckRepeat = getFileData(prefix + 'bu-dyck-repeat.csv')
	buDyckRepeatBefore = getFileData(prefix + 'bu-dyck-repeat-before.csv')
	buDyckRepeatAfter = getFileData(prefix + 'bu-dyck-repeat-after.csv')
	buStupid = getFileData(prefix + 'bu-stupid.csv')
	tdDyckInside = getFileData(prefix + 'td-dyck-inside.csv')
	tdDyckRepeat = getFileData(prefix + 'td-dyck-repeat.csv')
	tdDyckRepeatBefore = getFileData(prefix + 'td-dyck-repeat-before.csv')
	tdDyckRepeatAfter = getFileData(prefix + 'td-dyck-repeat-after.csv')
	tdStupid = getFileData(prefix + 'td-stupid.csv')

	plt.figure()
	plt.title('Dyck language (bottom up)')
	plt.plot(buDyckInside[0], buDyckInside[1], label='bottom-up ((...))')
	plt.plot(buDyckRepeat[0], buDyckRepeat[1], label='bottom-up ()...()')
	plt.plot(buDyckRepeatBefore[0], buDyckRepeatBefore[1], label='bottom-up )()...()')
	plt.plot(buDyckRepeatAfter[0], buDyckRepeatAfter[1], label='bottom-up ()...()(')
	plt.legend()
	plt.ylabel('time')
	plt.xlabel('length')

	plt.figure()
	plt.title('Dyck language (top-down, slow cases)')
	plt.plot(tdDyckInside[0], tdDyckInside[1], label='top-down ((...))')
	plt.plot(tdDyckRepeatAfter[0], tdDyckRepeatAfter[1], label='top-down ()...()(') #ksk nt denna som e seg???
	plt.legend()
	plt.ylabel('time')
	plt.xlabel('length')

	plt.figure()
	plt.title('Dyck language (top-down, fast cases)')
	plt.plot(tdDyckRepeat[0], tdDyckRepeat[1], label='top-down ()...()')
	plt.plot(tdDyckRepeatBefore[0], tdDyckRepeatBefore[1], label='top-down )()...()')
	plt.legend()
	plt.ylabel('time')
	plt.xlabel('length')

	plt.figure()
	plt.title('Parse times for the \"stupid\" grammar')
	plt.plot(buStupid[0], buStupid[1], label='bottom-up')
	plt.plot(tdStupid[0], tdStupid[1], label='top-down')
	plt.legend()
	plt.ylabel('time')
	plt.xlabel('length')

	plt.show()

def getFileData(filepath):
	file = open(filepath, 'r')
	file.readline()
	lines = csv.reader(file)
	dataX = []
	dataY = []
	for line in lines:
		dataX.append(int(line[0]))
		dataY.append(float(line[3]))
	file.close()
	return (dataX, dataY)

if __name__ == '__main__':
    main()