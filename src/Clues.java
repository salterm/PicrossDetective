import java.util.*;

public class Clues {
    public ArrayList<Vector<Integer>> rows;
    public ArrayList<Vector<Integer>> columns;

    public Clues(int height, int width) {
        rows = new ArrayList<>(height);
        columns = new ArrayList<>(width);
    }

    public boolean isRowSatisfied(final Puzzle puzzle, final int row) {
        //List of blocks existing in the puzzle
        Vector<Integer> puzzleRow = new Vector<>();

        int currentBlockLength = 0;
        //Iterate through the row
        for (int column = 0; column < puzzle.width; column++) {
            switch (puzzle.getSquare(row, column)) {
                case VOID:
                    return false;
                case WHITE:
                    if (currentBlockLength > 0) {
                        puzzleRow.add(currentBlockLength);
                        currentBlockLength = 0;
                    }
                    break;
                case BLACK:
                    currentBlockLength++;
                    break;
            }
        }
        //If there was a block that ended at the last column, add it to the list
        if (currentBlockLength > 0) {
            puzzleRow.add(currentBlockLength);
        }

        //If the lengths, count, and order of blocks in the puzzle match that of the clues for the row
        return rows.get(row).equals(puzzleRow);
    }

    public boolean isRowSatisfied(final ArrayList<Puzzle.Colors> submittedRow, final int row) {
        //List of blocks existing in the puzzle
        Vector<Integer> puzzleRow = new Vector<>();

        int currentBlockLength = 0;
        //Iterate through the row
        for (int column = 0; column < submittedRow.size(); column++) {
            switch (submittedRow.get(column)) {
                case VOID:
                    return false;
                case WHITE:
                    if (currentBlockLength > 0) {
                        puzzleRow.add(currentBlockLength);
                        currentBlockLength = 0;
                    }
                    break;
                case BLACK:
                    currentBlockLength++;
                    break;
            }
        }
        //If there was a block that ended at the last column, add it to the list
        if (currentBlockLength > 0) {
            puzzleRow.add(currentBlockLength);
        }

        //If the lengths, count, and order of blocks in the puzzle match that of the clues for the row
        return rows.get(row).equals(puzzleRow);
    }

    public boolean isColumnSatisfied(final Puzzle puzzle, final int column) {
        //List of blocks existing in the puzzle
        Vector<Integer> puzzleColumn = new Vector<>();

        int currentBlockLength = 0;
        //Iterate through the row
        for (int row = 0; row < puzzle.height; row++) {
            switch (puzzle.getSquare(row, column)) {
                case VOID:
                    return false;
                case WHITE:
                    if (currentBlockLength > 0) {
                        puzzleColumn.add(currentBlockLength);
                        currentBlockLength = 0;
                    }
                    break;
                case BLACK:
                    currentBlockLength++;
                    break;
            }
        }
        //If there was a block that ended at the last row, add it to the list
        if (currentBlockLength > 0) {
            puzzleColumn.add(currentBlockLength);
        }

        //If the lengths, count, and order of blocks in the puzzle match that of the clues for the column
        return columns.get(column).equals(puzzleColumn);
    }

    public boolean isPuzzleSatisfied(final Puzzle puzzle) {
        //If the clues are satisfied in every row...
        for (int row = 0; row < puzzle.width; row++) {
            if (!isRowSatisfied(puzzle, row)) {
                return false;
            }
        }
        //...and in every column...
        for (int column = 0; column < puzzle.height; column++) {
            if (!isColumnSatisfied(puzzle, column)) {
                return false;
            }
        }
        //...then the puzzle is solved!
        return true;
    }
}
