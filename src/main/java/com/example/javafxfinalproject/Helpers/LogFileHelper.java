package com.example.javafxfinalproject.Helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class LogFileHelper {
    static public void log(Exception exception) {
        try {
            FileWriter myWriter = new FileWriter("src/main/java/com/example/javafxfinalproject/Helpers/log.txt");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            myWriter.write("DATE: " + timeStamp + "\n" + Arrays.toString(exception.getStackTrace()));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error logging..." + e.getLocalizedMessage());
        }
    }
}
