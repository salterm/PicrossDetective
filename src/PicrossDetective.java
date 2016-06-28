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
        
        //Get row clues
        
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
