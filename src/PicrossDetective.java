/* 
* Copyright (c) 2016 Michael Salter
* Email: salterm@pdx.edu
* This program is available under the "MIT license". Please see the LICENSE file for the full text.
* This file may not be copied, modified, or distributed except according to the terms of said license.
*/

import java.util.*;
import java.io.*;

public class PicrossDetective {
    public static int height;
    public static int width;

    public static void main(String[] args) {
        PicrossDetective picrossDetective = new PicrossDetective();
        Puzzle puzzle;
        Clues clues;

        Scanner in = new Scanner(System.in);

        //Get height of puzzle
        int input = 0;
        while (input == 0) {
            System.out.println("Enter height: ");

            input = in.nextInt();
            if (input <= 0 || input > Puzzle.maxHeight) {
                System.out.println("Must be positive non-zero value less than " + Puzzle.maxHeight + ".");
                input = 0;
            }
        }

        height = input;

        //Get width of puzzle
        input = 0;
        while (input == 0) {
            System.out.println("Enter width: ");

            input = in.nextInt();
            if (input <= 0 || input > Puzzle.maxWidth) {
                System.out.println("Must be positive non-zero value less than " + Puzzle.maxWidth + ".");
                input = 0;
            }
        }

        width = input;

        puzzle = new Puzzle(height, width);
        clues = new Clues(height, width);

        //DEBUG
        puzzle.print();

        System.out.println("Enter clues from left to right, separated by spaces. If a row/column is empty, simply hit return.");
        //Get row clues
        for (int i = 0; i < puzzle.height; i++) {
            Vector<Integer> rowClues = new Vector<Integer>();
            System.out.println("Row " + (i + 1) + ": ");
            BufferedReader clueInput = new BufferedReader(new InputStreamReader(System.in));
            String[] userRowClues = null;
            try {
                userRowClues = clueInput.readLine().trim().split("\\s+");
            } catch (Exception e) {
                return;
            }
            if (!(userRowClues.length == 1 && userRowClues[0].equals(""))) {

                for (String s : userRowClues) {
                    int clue = Integer.parseInt(s);
                    if (clue <= 0) {
                        throw new IllegalArgumentException("Clues must be positive non-zero values.");
                    }
                    if (clue > puzzle.width) {
                        throw new IllegalArgumentException("Clues cannot exceed puzzle dimensions.");
                    }
                    rowClues.add(clue);
                }
            }

            //Check that total clues and minimum spaces between does not exceed puzzle dimensions
            int impliedRowWidth = 0;
            for (Integer c : rowClues) {
                impliedRowWidth += c;
            }
            impliedRowWidth += rowClues.size() - 1;
            if (impliedRowWidth > puzzle.width) {
                throw new IllegalArgumentException("Minimum width implied by clues cannot exceed puzzle dimensions.");
            }
            //DEBUG
            System.out.println(rowClues);

            clues.rows.add(i, rowClues);
        }

        //Get column clues
        for (int i = 0; i < puzzle.width; i++) {
            Vector<Integer> columnClues = new Vector<Integer>();
            System.out.println("Column " + (i + 1) + ": ");
            BufferedReader clueInput = new BufferedReader(new InputStreamReader(System.in));
            String[] userColumnClues = null;
            try {
                userColumnClues = clueInput.readLine().trim().split("\\s+");
            } catch (Exception e) {
                return;
            }
            for (String s : userColumnClues) {
                int clue = Integer.parseInt(s);
                if (clue <= 0) {
                    throw new IllegalArgumentException("Clues must be positive non-zero values.");
                }
                if (clue > puzzle.height) {
                    throw new IllegalArgumentException("Clues cannot exceed puzzle dimensions.");
                }
                columnClues.add(clue);
            }

            //Check that total clues and minimum spaces between does not exceed puzzle dimensions
            int impliedColumnHeight = 0;
            for (Integer c : columnClues) {
                impliedColumnHeight += c;
            }
            impliedColumnHeight += columnClues.size() - 1;
            if (impliedColumnHeight > puzzle.height) {
                throw new IllegalArgumentException("Minimum height implied by clues cannot exceed puzzle dimensions.");
            }
            //DEBUG
            System.out.println(columnClues);

            clues.columns.add(i, columnClues);
        }

        //Begin solving

        //Find all possible rows for every row
        ArrayList<Vector<ArrayList<Puzzle.Colors>>> allPossibleRowsEveryRow = new ArrayList<>(puzzle.height);
        for (int row = 0; row < puzzle.height; row++) {
            allPossibleRowsEveryRow.add(findAllPossibleRows(puzzle, clues, row));
        }

        //Iterate through all possible solutions
        boolean isPuzzleSatisfiable = solve(puzzle, clues, allPossibleRowsEveryRow, 0);

        //Display puzzle; either a solution, or a blank puzzle if no solution found
        if (isPuzzleSatisfiable) {
            puzzle.print();
        } else {
            puzzle.clearPuzzle();
            puzzle.print();
        }
    }

    private static Vector<ArrayList<Puzzle.Colors>> findAllPossibleRows(final Puzzle puzzle, final Clues clues, final int row) {
        Vector<ArrayList<Puzzle.Colors>> allPossibleRows = new Vector<>();
        recursivelyBuildRows(puzzle.width, clues, row, allPossibleRows, new ArrayList<>());
        assert (allPossibleRows.size() > 0);
        return allPossibleRows;
    }

    private static void recursivelyBuildRows(final int width, final Clues clues, final int row, Vector<ArrayList<Puzzle.Colors>> allPossibleRows, ArrayList<Puzzle.Colors> partialRow) {
        //Base case
        if (partialRow.size() >= width) {
            if (clues.isRowSatisfied(partialRow, row)) {
                allPossibleRows.add(partialRow);
            }
        } else {
            //Recursive case
            ArrayList<Puzzle.Colors> oldRow = new ArrayList<>(partialRow);
            for (Puzzle.Colors c : Puzzle.Colors.values()) {
                if (c == Puzzle.Colors.VOID) {
                    continue;
                }
                partialRow.add(c);
                recursivelyBuildRows(width, clues, row, allPossibleRows, partialRow);
                partialRow = new ArrayList<>(oldRow);
            }
        }
    }

    private static void depthFirstSearch(Puzzle puzzle, Clues clues, ArrayList<Vector<ArrayList<Puzzle.Colors>>> allPossibleRowsEveryRow) {
        int row = 0;
        //Go as deeply as possible
        for (int i = 0; i < puzzle.height - 1; ) {

        }
        for (int i = 0; i < allPossibleRowsEveryRow.get(row).size(); i++) {
            puzzle.fillRow(row, allPossibleRowsEveryRow.get(row).get(i));
            if (clues.isPuzzleSatisfied(puzzle)) {
                return;
            }
        }

        //Puzzle not satisfiable
        puzzle.clearPuzzle();
        return;
    }

    private static boolean solve(Puzzle puzzle, Clues clues, ArrayList<Vector<ArrayList<Puzzle.Colors>>> allPossibleRowsEveryRow, int row) {
        if (row >= puzzle.height) {
            return false;
        }
        for (ArrayList<Puzzle.Colors> r : allPossibleRowsEveryRow.get(row)) {
            puzzle.fillRow(row, r);
            boolean x = solve(puzzle, clues, allPossibleRowsEveryRow, row + 1);
            if (!x) {
                if (clues.isPuzzleSatisfied(puzzle)) {
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
    }
}



/*solve(puzzle, row) {
    if (row > height) {
        return false
    }
    if puzzle is solved {
        return true
    }
    for each possible row p
    {
        fillRow (row, p);
        x = solve(puzzle, row + 1);
        clearRow (row);
        if (x) {
            return true;
        }
    }
}*/
