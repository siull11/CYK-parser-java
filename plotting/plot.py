#!/usr/bin/python
import sys
import matplotlib.pyplot as plt

def main():
	plt.plot([1, 2, 3, 4], [2, 4, 6, 8])
	plt.ylabel('some numbers')
	plt.show()


if __name__ == '__main__':
    main()