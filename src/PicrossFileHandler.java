/*
* Copyright (c) 2016 Michael Salter
* Email: salterm@pdx.edu
* This program is available under the "MIT license". Please see the LICENSE file for the full text.
* This file may not be copied, modified, or distributed except according to the terms of said license.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PicrossFileHandler {
    private String filepath;

    public PicrossFileHandler(String filepath) {
        this.filepath = filepath;
    }

    public Clues readCluesFromFile() throws FileNotFoundException {
        //Open the file, if it exists
        Scanner file = new Scanner(new File(filepath));

        //Read in the height and width
        int height = file.nextInt();
        int width = file.nextInt();

        for (int i = 0; i < height; i++) {

        }

        Clues clues = new Clues(height, width);
        return clues;
    }

    /*
        File Format:
        width height
        row 1 clues delimited by spaces
        row 2 clues delimited by spaces
        ...
        row (height) clues delimited by spaces
        column 1 clues delimited by spaces
        column 2 clues delimited by spaces
        ...
        column (width) clues delimited by spaces
        EOF
    */
    public void writeCluesToFile() {

    }
}
