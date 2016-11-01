import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by gako on 16/10/16.
 */
public class Decoder {
    public static boolean checkValid(ArrayList<String> lines) {
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

        List<Character> letters = new ArrayList<Character>();
        // Return FALSE if any of the lines between (number+2)th an the end do not follow the following pattern:
        // Upper-case: comma-separated lower-case strings
        for (int i = number + 2; i < lines.size(); i++) {
        	letters.add(lines.get(i).charAt(0));
        	
            if (!lines.get(i).matches("[A-Z]:([a-z]+,)*[a-z]+")) {
                return false;
            }
        }

        // Return FALSE if any of the lines between 3rd and (number+2)th contains anything different than upper and
        // lower-case letters
        for (int i = 2; i < number + 2; i++) {
            if (!lines.get(i).matches("[A-Za-z]+")) {
                return false;
            }
            
            // Check if only uppercase letters that are specified in R are contained within the t
            List<Character> lineLetters = new ArrayList<Character>();
            for (char c : lines.get(i).toCharArray()) {
            	lineLetters.add(c);
            }
            
            lineLetters.removeAll(letters);
            
            for (char c : lineLetters) {
            	if (!(c >= 'a' && c <= 'z')) {
            		return false;
            	}
            }
        }

        // return TRUE if all is valid
        return true;
    }
    
    public static Problem parse(List<String> lines) {
        Problem result = new Problem();
    	
    	int number = Integer.parseInt(lines.get(0));
    	result.s = lines.get(1);
    	
    	for (int i = 2; i < number + 2; i++) {
    		result.T.add(lines.get(i));
        }
    	
    	for (int i = number + 2; i < lines.size(); i++) {
    		String line = lines.get(i);
    		
    		char letter = line.substring(0, 1).toCharArray()[0];
    		List<String> substrings = new ArrayList<String>(Arrays.asList(line.substring(2).split(",")));
    		
    		result.R.put(letter, substrings);
        }
    	
    	return result;
    }
}