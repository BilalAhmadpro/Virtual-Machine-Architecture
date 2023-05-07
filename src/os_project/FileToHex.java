/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_project;

/**
 *
 * @author Bilal Ahmad
 */
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileToHex 
{
   
    private static final String NEW_LINE = System.lineSeparator();
    private static final String UNKNOWN_CHARACTER = ".";
    private String file;
    FileToHex()
    {
        
    }
    public static void main(String[] args) throws IOException {

       // String file = "C:\\Users\\Khaleel Ahmad\\Documents\\5th-semester books\\OperatingSystem\\src\\operatingsystem\\flags.txt";

        
 //       System.out.println(s);
    }

    public static String convertFileToHex(Path path) throws IOException {

        if (Files.notExists(path)) {
            throw new IllegalArgumentException("File not found! " + path);
        }

        StringBuilder result = new StringBuilder();
        StringBuilder hex = new StringBuilder();
        StringBuilder input = new StringBuilder();

        int count = 0;
        int value;

        // path to inputstream....
        try (InputStream inputStream = Files.newInputStream(path)) {

            while ((value = inputStream.read()) != -1) {

                hex.append(String.format("%02X ", value));

                //If the character is unable to convert, just prints a dot "."
                if (!Character.isISOControl(value)) {
                    input.append((char) value);
                } else {
                    input.append(UNKNOWN_CHARACTER);
                }

                // After 15 bytes, reset everything for formatting purpose
                if (count == 14) {
                  //  result.append(String.format("%-60s | %s%n", hex));
                    result.append(hex);
                    result.append("\n");
                    hex.setLength(0);
                    input.setLength(0);
                    count = 0;
                } else {
                    count++;
                }

            }

            // if the count>0, meaning there is remaining content
            if (count > 0) {
                result.append(hex);
                    result.append("\n");
                //result.append(String.format("%-60s | %s%n", hex, input));
            }

        }

        return result.toString();
    }

}
