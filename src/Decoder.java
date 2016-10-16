import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gako on 16/10/16.
 */
public class Decoder {
    private static boolean checkValid(ArrayList<String> lines) {
        // check first line is an integer
        int number;
        try {
            number = Integer.parseInt(lines.get(0));
        } catch (NumberFormatException up) {
            return false;
        }

        // Return FALSE if the 2nd string contains anything different than lowercase chars
        if (!lines.get(1).matches("[a-z]+")) {
            return false;
        }

        // Return FALSE if any of the lines between 3rd and (number+2)th contains anything different than upper and
        // lower-case letters
        for (int i = 2; i < number + 2; i++) {
            if (!lines.get(i).matches("[A-Za-z]+")) {
                return false;
            }
        }

        // Return FALSE if any of the lines between (number+2)th an the end do not follow the following pattern:
        // Upper-case: comma-separated lower-case strings
        for (int i = number + 2; i < lines.size(); i++) {
            if (!lines.get(i).matches("[A-Z]:([a-z]+,)*[a-z]+")) {
                return false;
            }
        }

        // return TRUE if all is valid
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

            if (checkValid(lines)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        } catch (FileNotFoundException up) {
            System.out.print("File not found: " + file_path);
        }
    }
}
