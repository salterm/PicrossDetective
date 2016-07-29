# Picross Detective

Copyright (c) 2016 Michael Salter

This program is available under the "MIT license". Please see the LICENSE file for the full text.
This file may not be copied, modified, or distributed except according to the terms of said license.

- - -

Picross Detective is a tool to solve Picross (Nonograms, Griddlers, Hanjie, etc.) puzzles.

## To Build
Picross Detective requires that Java 1.8 be installed on your Unix device.
After cloning the repository, navigate to the src directory and type "make". This will compile the necessary files. To remove the compiled files, type "make clean".

## To Run
The compiled class can be run from a Unix command line with "java PicrossDetective".

## To Use
Picross Detective is a command-line Java application that reads and writes to/from standard input/output. A user can opt to open a preexisting puzzle from a file, submit clues for a new puzzle, and then save either to a new file. The application will then attempt to solve the loaded puzzle. If solutions are found, they will be printed to standard out. The program will then exit.

Currently it is not recommended to attempt solving complicated puzzles larger than 10 x 10. The process is very, painfully, slow.

## What are Picross puzzles?
Picross (also known as Nonograms, Griddlers, Hanjie, and others) are a type of logic puzzle that, when solved, form a picture. They are composed of a grid with numerical clues for every row and column. Players use logical reasoning to determine what color goes in what square. More information can be found here: [Wikipedia: Nonogram](https://en.wikipedia.org/wiki/Nonogram)

## Current features
* Command-line interface
* Puzzle can be saved to a file
* Puzzle can be read in from a file
* Input puzzles of arbitrary size
* Solve puzzles of arbitrary size (eventually)
* Displays solutions to puzzle (if they exist)

## Future plans
* Better documentation
* A GUI application
* Allow user to create and save puzzles
* Multiple puzzle colors
* Show progress of solver
* Option to create puzzle from an existing image
* Human-like logical solving
* Hybrid human-like solving combined with random solution generator (should be faster for some puzzles?)
