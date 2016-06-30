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
    
    public static void main (String[] args) {
        PicrossDetective picrossDetective = new PicrossDetective();
        Puzzle puzzle;
        Clues clues;

        Scanner in = new Scanner(System.in);

        //Get height of puzzle
        int input = 0;
        while(input == 0) {
            System.out.println("Enter height: ");
        
            input = in.nextInt();
            if (input <= 0 || input > Puzzle.maxHeight) {
                System.out.println("Must be positive non-zero value less than " + Puzzle. maxHeight + ".");
                input = 0;
            }
        }
        
        height = input;
        
        //Get width of puzzle
        input = 0;
        while(input == 0) {
            System.out.println("Enter width: ");
        
            input = in.nextInt();
            if (input <= 0 || input > Puzzle.maxWidth) {
                System.out.println("Must be positive non-zero value less than " + Puzzle.maxWidth + ".");
                input = 0;
            }
        }
        
        width = input;

        puzzle = new Puzzle(width, height);
        clues = new Clues(width, height);

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
        boolean solved = false;
        while (!cluesSatisfied()) {
            //Print puzzle
            //solve
            
        }
    }
    
    private static boolean cluesSatisfied() {
        return false;
    }
}
