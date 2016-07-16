/*
* Copyright (c) 2016 Michael Salter
* Email: salterm@pdx.edu
* This program is available under the "MIT license". Please see the LICENSE file for the full text.
* This file may not be copied, modified, or distributed except according to the terms of said license.
*/

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

class PicrossFileHandler {
    private String filepath;

    PicrossFileHandler(final String filepath) {
        this.filepath = filepath;
    }

    PicrossPuzzle readCluesFromFile() throws FileNotFoundException {
        //Open the file, if it exists
        Scanner file = new Scanner(new File(filepath));

        //Read in the height and width
        int height = file.nextInt();
        int width = file.nextInt();
        //Consume newline
        file.nextLine();

        PicrossPuzzle puzzle = new PicrossPuzzle(height, width);

        //Add row puzzle
        for (int i = 0; i < height; i++) {
            String[] rowCluesString = file.nextLine().split("\\s+");
            Vector<Integer> rowClues = new Vector<>();
            for (String s : rowCluesString) {
                rowClues.add(Integer.parseInt(s));
            }
            puzzle.rows.add(i, rowClues);
        }
        //Add column puzzle
        for (int i = 0; i < width; i++) {
            String[] columnCluesString = file.nextLine().split("\\s+");
            Vector<Integer> columnClues = new Vector<>();
            for (String s : columnCluesString) {
                columnClues.add(Integer.parseInt(s));
            }
            puzzle.columns.add(i, columnClues);
        }

        assert (!file.hasNext());
        file.close();

        return puzzle;
    }

    /*
        File Format:
        width height
        row 1 puzzle delimited by spaces
        row 2 puzzle delimited by spaces
        ...
        row (height) puzzle delimited by spaces
        column 1 puzzle delimited by spaces
        column 2 puzzle delimited by spaces
        ...
        column (width) puzzle delimited by spaces
        EOF
    */
    void writeCluesToFile(final PicrossPuzzle puzzle) throws IOException {
        FileWriter file = new FileWriter(new File(filepath));
        StringBuilder output = new StringBuilder();

        //Add the height and width
        output.append(puzzle.height).append(" ").append(puzzle.width).append("\n");

        //Add row puzzle
        for (int i = 0; i < puzzle.height; i++) {
            for (int j = 0; j < puzzle.rows.get(i).size(); j++) {
                if (j != 0) {
                    output.append(" ").append(puzzle.rows.get(i).elementAt(j));
                } else {
                    output.append(puzzle.rows.get(i).elementAt(j));
                }
            }
            output.append("\n");
        }

        //Add column puzzle
        for (int i = 0; i < puzzle.width; i++) {
            for (int j = 0; j < puzzle.columns.get(i).size(); j++) {
                if (j != 0) {
                    output.append(" ").append(puzzle.columns.get(i).elementAt(j));
                } else {
                    output.append(puzzle.columns.get(i).elementAt(j));
                }
            }
            output.append("\n");
        }

        //Write to file
        file.write(output.toString());
        file.close();
    }
}