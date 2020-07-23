package com.assembler;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException{

        // create an empty list to add all instructions including labels like MAYDATA:
        List<String> list = new ArrayList<>();

        // create another empty list for actual instructions without labels.
        // such as "LOOP1:"  "MAYDATA:"
        List<String> list2 = new ArrayList<>();
        FileReader r = new FileReader(args[0]);
        // reads a txt
        BufferedReader buf = new BufferedReader(r);
        // reads a line in the txt
        String line = buf.readLine();
        // adds the uppercase version of the first line to list
        list.add(line.toUpperCase());

        // adds the next line in the txt to the list as long as the line is not null.
        while(line != null){
            line = buf.readLine();
            if(line != null) {
                list.add(line.toUpperCase());

            }
        }

        // fill the second list with only the actual instructions without labels.
        for(String c:list){
            int j = c.length()-1;
            if(c.charAt(j) != ':'){
                list2.add(c);
            }
        }

        // creates an object from Assembler class
        Assembler a = new Assembler();

        // creates a Bufferedwriter object to write the output to a txt.
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"))){
            // takes each instruction in list 2 to use as an argument for instruction method
            for(String c: list2){
                String wline = a.instruction(c, list);
                // writes binary code of the instruction o the txt file.
                writer.write(wline);
                writer.newLine();
            }
        }

    }
}



