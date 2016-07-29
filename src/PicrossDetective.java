/* 
* Copyright (c) 2016 Michael Salter
* Email: salterm@pdx.edu
* This program is available under the "MIT license". Please see the LICENSE file for the full text.
* This file may not be copied, modified, or distributed except according to the terms of said license.
*/

import java.util.*;
import java.io.*;

public class PicrossDetective {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Welcome to Picross Detective!");
        System.out.println("Type \"exit\" to exit the program.");

        boolean readFromFile = false;

        //Check if the user wants to load a puzzle from a file
        userWantsRead:
        while (true) {
            System.out.println("Load puzzle from file? (y / n)");
            String response = userInput.nextLine();
            switch (response) {
                case "exit":
                    System.exit(0);
                case "y":
                    readFromFile = true;
                    break userWantsRead;
                case "n":
                    readFromFile = false;
                    break userWantsRead;
                default:
                    System.out.println("Please respond with \"y\" if you would like to load a puzzle from a file, or \"n\" if not.");
                    break;
            }
        }

        PicrossCanvas canvas = null;
        PicrossPuzzle puzzle = null;

        //Try to read a puzzle from a file
        while (readFromFile) {
            //Get the user to input a valid filename
            System.out.println("Enter a valid filepath (PicrossDetective files have a \".pxd\" extension):");
            String response = userInput.nextLine();
            if (response.equals("exit")) {
                System.exit(0);
            } else {
                PicrossFileHandler fileReader = new PicrossFileHandler(response);
                try {
                    puzzle = fileReader.readCluesFromFile();
                    canvas = new PicrossCanvas(puzzle.height, puzzle.width);
                    System.out.println("Puzzle successfully loaded from '" + response + "'.");
                    break;
                } catch (FileNotFoundException e) {
                    System.out.println("Error reading file '" + response + "'.");
                    readFromFile:
                    while (true) {
                        System.out.println("Try again? (y / n)");
                        response = userInput.nextLine();
                        switch (response) {
                            case "exit":
                                System.exit(0);
                            case "y":
                                readFromFile = true;
                                break readFromFile;
                            case "n":
                                readFromFile = false;
                                break readFromFile;
                            default:
                                System.out.println("Please respond with \"y\" if you would like to read the puzzle from a file, or \"n\" if not.");
                                break;
                        }
                    }
                }
            }
        }

        //Manually enter a puzzle
        if (!readFromFile) {
            //Get height of puzzle
            int input = 0;
            while (input == 0) {
                System.out.println("Enter height: ");

                input = userInput.nextInt();
                if (input < 1) {
                    System.out.println("Height must be a positive non-zero value.");
                    input = 0;
                }
            }

            int height = input;

            //Get width of puzzle
            input = 0;
            while (input == 0) {
                System.out.println("Enter width: ");

                input = userInput.nextInt();
                if (input < 1) {
                    System.out.println("Width must be a positive non-zero value.");
                    input = 0;
                }
            }

            int width = input;

            canvas = new PicrossCanvas(height, width);
            puzzle = new PicrossPuzzle(height, width);

            //DEBUG
            canvas.print();

            System.out.println("Enter clues from left to right, separated by spaces. If a row/column is empty, simply hit return.");
            //Get row clues
            for (int i = 0; i < puzzle.height; i++) {
                Vector<Integer> rowClues = new Vector<>();
                System.out.println("Row " + (i + 1) + ": ");
                BufferedReader clueInput = new BufferedReader(new InputStreamReader(System.in));
                String[] userRowClues;
                try {
                    userRowClues = clueInput.readLine().trim().split("\\s+");
                } catch (Exception e) {
                    return;
                }
                if (!(userRowClues.length == 1 && userRowClues[0].equals(""))) {

                    for (String s : userRowClues) {
                        int clue = Integer.parseInt(s);
                        if (clue <= 0) {
                            throw new IllegalArgumentException("PicrossPuzzle must be positive non-zero values.");
                        }
                        if (clue > puzzle.width) {
                            throw new IllegalArgumentException("PicrossPuzzle cannot exceed puzzle dimensions.");
                        }
                        rowClues.add(clue);
                    }
                }

                //Check that total puzzle and minimum spaces between does not exceed puzzle dimensions
                int impliedRowWidth = 0;
                for (Integer c : rowClues) {
                    impliedRowWidth += c;
                }
                impliedRowWidth += rowClues.size() - 1;
                if (impliedRowWidth > puzzle.width) {
                    throw new IllegalArgumentException("Minimum width implied by puzzle cannot exceed puzzle dimensions.");
                }
                //DEBUG
                System.out.println(rowClues);

                puzzle.rows.add(i, rowClues);
            }

            //Get column clues
            for (int i = 0; i < puzzle.width; i++) {
                Vector<Integer> columnClues = new Vector<>();
                System.out.println("Column " + (i + 1) + ": ");
                BufferedReader clueInput = new BufferedReader(new InputStreamReader(System.in));
                String[] userColumnClues;
                try {
                    userColumnClues = clueInput.readLine().trim().split("\\s+");
                } catch (Exception e) {
                    return;
                }
                for (String s : userColumnClues) {
                    int clue = Integer.parseInt(s);
                    if (clue <= 0) {
                        throw new IllegalArgumentException("PicrossPuzzle must be positive non-zero values.");
                    }
                    if (clue > puzzle.height) {
                        throw new IllegalArgumentException("PicrossPuzzle cannot exceed puzzle dimensions.");
                    }
                    columnClues.add(clue);
                }

                //Clear the input buffer
                userInput.nextLine();

                //Check that total puzzle and minimum spaces between does not exceed puzzle dimensions
                int impliedColumnHeight = 0;
                for (Integer c : columnClues) {
                    impliedColumnHeight += c;
                }
                impliedColumnHeight += columnClues.size() - 1;
                if (impliedColumnHeight > puzzle.height) {
                    throw new IllegalArgumentException("Minimum height implied by puzzle cannot exceed puzzle dimensions.");
                }
                //DEBUG
                System.out.println(columnClues);

                puzzle.columns.add(i, columnClues);
            }
        }

        //Check if the user wants to save the puzzle to a file
        boolean writeToFile = false;
        userWriteFile:
        while (true) {
            System.out.println("Save puzzle to file? (y / n)");
            String response = userInput.nextLine();
            switch (response) {
                case "exit":
                    System.exit(0);
                case "y":
                    writeToFile = true;
                    break userWriteFile;
                case "n":
                    writeToFile = false;
                    break userWriteFile;
                default:
                    System.out.println("Please respond with \"y\" if you would like to save the puzzle to a file, or \"n\" if not.");
                    break;
            }
        }

        //Get the user to input a valid filename
        while (writeToFile) {
            System.out.println("Enter a valid filepath (PicrossDetective files have a \".pxd\" extension):");
            String response = userInput.nextLine();
            if (response.equals("exit")) {
                System.exit(0);
            } else {
                PicrossFileHandler fileWriter = new PicrossFileHandler(response);
                try {
                    fileWriter.writeCluesToFile(puzzle);
                    System.out.println("Puzzle successfully saved to '" + response + "'.");
                    break;
                } catch (IOException e) {
                    System.out.println("Error writing file '" + response + "'.");
                    writeToFile:
                    while (true) {
                        System.out.println("Try again? (y / n)");
                        response = userInput.nextLine();
                        switch (response) {
                            case "exit":
                                System.exit(0);
                            case "y":
                                writeToFile = true;
                                break writeToFile;
                            case "n":
                                writeToFile = false;
                                break writeToFile;
                            default:
                                System.out.println("Please respond with \"y\" if you would like to save the puzzle to a file, or \"n\" if not.");
                                break;
                        }
                    }
                }
            }
        }

        //Begin solving
        System.out.println("Beginning solving.");

        //Find all possible rows for every row
        System.out.println("Creating list of all possible rows.");
        ArrayList<Vector<ArrayList<PicrossCanvas.Colors>>> allPossibleRowsEveryRow = new ArrayList<>(puzzle.height);
        for (int row = 0; row < puzzle.height; row++) {
            allPossibleRowsEveryRow.add(findAllPossibleRows(puzzle, row));
        }

        //Iterate through all possible solutions
        System.out.println("Searching all combinations of all possible rows.");
        boolean isPuzzleSatisfiable = solve(canvas, puzzle, allPossibleRowsEveryRow, 0);

        //Display canvas; either a solution, or blank if no solution found
        if (isPuzzleSatisfiable) {
            System.out.println("Solution found!");
            canvas.print();
        } else {
            System.out.println("No solution found.");
        }
    }

    //A very naive implementation that checks all possible combinations of colors (except VOID, of course) and squares
    private static Vector<ArrayList<PicrossCanvas.Colors>> findAllPossibleRows(final PicrossPuzzle puzzle, final int row) {
        Vector<ArrayList<PicrossCanvas.Colors>> allPossibleRows = new Vector<>();
        recursivelyBuildRows(puzzle.width, puzzle, row, allPossibleRows, new ArrayList<>());
        assert (allPossibleRows.size() > 0);
        return allPossibleRows;
    }

    private static void recursivelyBuildRows(final int width, final PicrossPuzzle puzzle, final int row, Vector<ArrayList<PicrossCanvas.Colors>> allPossibleRows, ArrayList<PicrossCanvas.Colors> partialRow) {
        //Base case
        if (partialRow.size() >= width) {
            if (puzzle.isRowSatisfied(partialRow, row)) {
                allPossibleRows.add(partialRow);
            }
        } else {
            //Recursive case
            ArrayList<PicrossCanvas.Colors> oldRow = new ArrayList<>(partialRow);
            for (PicrossCanvas.Colors c : PicrossCanvas.Colors.values()) {
                //Never try a row with an unfilled square
                if (c == PicrossCanvas.Colors.VOID) {
                    continue;
                }
                partialRow.add(c);
                recursivelyBuildRows(width, puzzle, row, allPossibleRows, partialRow);
                partialRow = new ArrayList<>(oldRow);
            }
        }
    }

    //Naive depth-first search-based solving algorithm, which iterates over all possible combinations of possible rows and returns the first solution, or exhausts all possibilities fruitlessly.
    private static boolean solve(PicrossCanvas canvas, final PicrossPuzzle puzzle, final ArrayList<Vector<ArrayList<PicrossCanvas.Colors>>> allPossibleRowsEveryRow, final int row) {
        if (row >= puzzle.height) {
            //No solution possible if we're outside the bounds of the puzzle
            return false;
        }
        for (ArrayList<PicrossCanvas.Colors> r : allPossibleRowsEveryRow.get(row)) {
            //Try this possible row
            canvas.fillRow(row, r);
            boolean wasASolutionFound = solve(canvas, puzzle, allPossibleRowsEveryRow, row + 1);

            if (wasASolutionFound) {
                //If a solution is found, return to the top of the stack
                return true;
            } else {
                //We may be at the last row, so check if the puzzle is satisfied
                if (puzzle.isPuzzleSatisfied(canvas)) {
                    return true;
                }
                //Otherwise, try other possible rows
            }
        }

        //If all possible rows at this depth are exhausted, report that no solution was found
        return false;
    }
}