import java.util.*;

public class Puzzle {
    public enum Colors{VOID, WHITE, BLACK};

    public final int height;
    public final int width;

    public static final int maxHeight = 10;
    public static final int maxWidth = 10;

    private Colors[][] puzzle;

    public Puzzle(int width, int height) {
        if (height > maxHeight || height <= 0) {
            throw new IllegalArgumentException("Invalid value for puzzle height.");
        } else {
            this.height = height;
        }
        if (width > maxWidth || width <= 0) {
            throw new IllegalArgumentException("Invalid value for puzzle width.");
        } else {
            this.width = width;
        }

        puzzle = new Colors[width][height];

        for (Colors[] ca : puzzle) {
            Arrays.fill(ca, Colors.VOID);
        }
    }

    public void fill(int row, int column, Colors c) {
        if (row < 0 || row >= height || column < 0 || column >= width) {
            throw new IllegalArgumentException("Invalid row/column dimension!");
        } else {
            puzzle[row][column] = c;
        }
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        for (Colors[] ca: puzzle) {
            for (Colors c: ca) {
                switch (c) {
                   case VOID:                                                                                          sb.append("?");
                       break;
                   case WHITE:
                       sb.append("0");
                       break;
                   case BLACK:
                       sb.append("#");
                       break;
                }
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }
}
