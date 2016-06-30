/* 
* Copyright (c) 2016 Michael Salter
* Email: salterm@pdx.edu
* This program is available under the "MIT license". Please see the LICENSE file for the full text.
* This file may not be copied, modified, or distributed except according to the terms of said license.
*/
 
import java.util.Scanner;
 
public class PicrossDetective {
    public final int height;
    public final int width;
    
    private final int maxHeight = 10;
    private final int maxWidth = 10;
    
    private class Clues {
        private Vector<int>[] rows;
        private Vector<int>[] columns;
    }
    
    private enum Colors {VOID, WHITE, BLACK};
    
    private class Puzzle {
        private Colors[][] puzzle; 
        
        private void fill(int row, int column) {
            
        }
        
        private void print() {
            StringBuilder sb = new StringBuilder();
            for (Colors[] ca; puzzle) {
                for (Colors c; ca) {
                    switch (c) {
                        case VOID {
                            sb.append('?');
                            break;
                        }
                        case WHITE {
                            sb.append('0');
                            break;
                        }
                        case BLACK {
                            sb.append('#');
                            break;
                        }
                    }
                }
                sb.append('/n');
            }
            
            System.out.println(sb);
        }
    }
    
    private Clues clues;
    private Puzzle puzzle;
    
    void main () {
        
        Scanner in = new Scanner(System.in); 
        
        //Get height of puzzle
        int input = 0;
        while(input == 0) {
            System.out.println("Enter input: ");
        
            input = in.nextInt();
            if (input <= 0 || input > maxHeight) {
                System.out.println("Invalid number.");
                input = 0;
            }
        }
        
        height = input;
        
        //Get width of puzzle
        input = 0;
        while(input == 0) {
            System.out.println("Enter input: ");
        
            input = in.nextInt();
            if (input <= 0 || input > maxWidth) {
                System.out.println("Invalid number.");
                input = 0;
            }
        }
        
        width = input;
        
        System.out.println("Enter clues from left to right, separated by spaces. If a row/column is empty, simply hit return.");
        //Get row clues
        boolean validClue = false;
        for (int i = 0; i < height; i++) {
            Vector<Integer> rowClues = new Vector<Integer>();
            System.out.println("Row " + (i + 1) + ": ");
            String[] userRowClues = in.next().trim().split("\\s+");
            for (String s : userRowClues) {
                int clue = Integer.parseInt(s);
                if (clue <= 0) {
                    throw new IllegalArgumentException("Clues must be positive non-zero values.");
                }
                if (clue >= width) {
                    throw new IllegalArgumentException("Clues cannot exceed puzzle dimensions.");
                }
                rowClues.add(clue);
            }
 
            //Check that total clues and minimum spaces between does not exceed puzzle dimensions
            int impliedRowWidth = 0;
            for (Integer i : rowClues) {
                impliedRowWidth += i;
            }
            impliedRowWidth += rowClues.size - 1
            if (impliedRowWidth > width) {
                throw new IllegalArgumentException("Minimum width implied by clues cannot exceed puzzle dimensions.");
            }
            //DEBUG
            System.out.println(rowClues);
        }
        
        //Get column clues
        
        //Begin solving
        boolean solved = false;
        while (!cluesSatisfied()) {
            //solve
            
        }
    }
    
    boolean cluesSatisfied() {
        return false;
    }
}
