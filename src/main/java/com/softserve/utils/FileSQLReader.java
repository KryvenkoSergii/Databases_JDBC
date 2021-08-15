package com.softserve.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileSQLReader {

    public static String getText(String filePath) throws IOException {
        File textFile = new File(filePath);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(textFile));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String st;
        while ((st = br.readLine()) != null) {
            sb.append(st);
        }

        br.close();

        return sb.toString();
    }

}
