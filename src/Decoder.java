import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gako on 16/10/16.
 */
public class Decoder {
    private static boolean checkValid(ArrayList<String> lines) {
        int number;
        try {
            number = Integer.parseInt(lines.get(0));
        } catch (NumberFormatException up) {
            return false;
        }

        return true;
    }


    public static void main(String[] args) {
        String file_path = "input_files/test01.SWE";
        ArrayList<String> lines = new ArrayList<>();

        try {
            Scanner in = new Scanner(new FileReader(file_path));

            while (in.hasNext()) {
                lines.add(in.next());
            }

            System.out.print(checkValid(lines));

        } catch (FileNotFoundException up) {
            System.out.print("File not found: " + file_path);
        }
    }
}
