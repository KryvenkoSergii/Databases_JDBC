package com.softserve;

import java.io.IOException;

import com.softserve.utils.FileSQLReader;

public class TestReadFile {

    public static void main(String[] args) throws IOException {

        String filePath = "src\\test\\resources\\queries\\update_film.sql";
        String text = FileSQLReader.getText(filePath);
        String query = String.format(text, "description", "testD1", "film_id", 11);
        
        System.out.println(query);
    }

}
