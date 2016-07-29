# Picross Detective

Copyright (c) 2016 Michael Salter

This program is available under the "MIT license". Please see the LICENSE file for the full text.
This file may not be copied, modified, or distributed except according to the terms of said license.

- - -

A tool to create and solve Picross (Nonograms, Griddlers, Hanjie, etc.) puzzles.

## To Use
Picross Detective is a command-line application that reads and writes standard input/output. After submitting clues for a given Picross puzzle, the application will attempt to solve it. If a solution is found, it will be printed to standard out; otherwise, a blank puzzle will be displayed.

Currently it is not recommended to attempt solving complicated puzzles larger than 10 x 10. The process is very, painfully, slow.

## To Build
Picross Detective can be compiled into a .jar using javac.

## What are Picross puzzles?

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
