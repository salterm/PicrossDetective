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
        //DEBUG
        PicrossFileHandler fileHandler = new PicrossFileHandler("testPuzzle.pxd");

        boolean fileFound;
        PicrossCanvas canvas = null;
        PicrossPuzzle puzzle = null;

        try {
            puzzle = fileHandler.readCluesFromFile();
            canvas = new PicrossCanvas(puzzle.height, puzzle.width);
            fileFound = true;
        } catch (FileNotFoundException e) {
            System.out.println(e);
            fileFound = false;
        }

        if (!fileFound) {
            Scanner in = new Scanner(System.in);

            //Get height of puzzle
            int input = 0;
            while (input == 0) {
                System.out.println("Enter height: ");

                input = in.nextInt();
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

                input = in.nextInt();
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

        //DEBUG
        //Write to file
        try {
            fileHandler.writeCluesToFile(puzzle);
        } catch (IOException e) {
            System.out.println(e);
        }

        //Begin solving

        //Find all possible rows for every row
        ArrayList<Vector<ArrayList<PicrossCanvas.Colors>>> allPossibleRowsEveryRow = new ArrayList<>(puzzle.height);
        for (int row = 0; row < puzzle.height; row++) {
            allPossibleRowsEveryRow.add(findAllPossibleRows(puzzle, row));
        }

        //Iterate through all possible solutions
        boolean isPuzzleSatisfiable = solve(canvas, puzzle, allPossibleRowsEveryRow, 0);

        //Display canvas; either a solution, or blank if no solution found
        if (isPuzzleSatisfiable) {
            canvas.print();
        } else {
            canvas.clearCanvas();
            canvas.print();
        }
    }

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
                if (c == PicrossCanvas.Colors.VOID) {
                    continue;
                }
                partialRow.add(c);
                recursivelyBuildRows(width, puzzle, row, allPossibleRows, partialRow);
                partialRow = new ArrayList<>(oldRow);
            }
        }
    }

    private static boolean solve(PicrossCanvas canvas, final PicrossPuzzle puzzle, final ArrayList<Vector<ArrayList<PicrossCanvas.Colors>>> allPossibleRowsEveryRow, final int row) {
        if (row >= puzzle.height) {
            return false;
        }
        for (ArrayList<PicrossCanvas.Colors> r : allPossibleRowsEveryRow.get(row)) {
            canvas.fillRow(row, r);
            boolean x = solve(canvas, puzzle, allPossibleRowsEveryRow, row + 1);
            if (!x) {
                if (puzzle.isPuzzleSatisfied(canvas)) {
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
    }
}