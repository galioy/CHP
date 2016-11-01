import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Solver {
	private static final String path = "gitcode/CHP/input_files/";
	
	public static void main(String[] args) {
        System.out.print("Which input file to check? Number: ");
        Scanner scanner = new Scanner(System.in);
        String filenumber = scanner.nextLine();
        String file_path = path + "test" + filenumber + ".SWE";

        ArrayList<String> lines = new ArrayList<>();

        try {
            Scanner in = new Scanner(new FileReader(file_path));

            while (in.hasNext()) {
                lines.add(in.next());
            }

            if (Decoder.checkValid(lines)) {
                Problem problem = Decoder.parse(lines);
                
                Map<Character, String> solution = solve(problem);
                if (solution != null) {
                	System.out.println("Problem was successfully solved!");
                	System.out.println("The correct combination is " + solution);
                	
                	saveSolution(filenumber, solution);

                	System.out.println("The solution has been saved to a file.");
                } else {
                	System.out.println("No solution exists for this problem.");
                }
            } else {
                System.out.println("The content of the file is not in SWE format.");
            }
        } catch (FileNotFoundException e) {
            System.out.print("File not found: " + file_path);
        } catch (IOException e) {
        	System.out.println("Could not save solution to a file.");
        }
	}
	
	// Solves the problem and saves the solution in a .SOL file.
	public static Map<Character, String> solve(Problem p) {
		// Apply heuristic techniques on this instance of the problem and simplify it
		simplify(p);
		
		// Check if the problem can have a solution based on the t-strings and whether they contain
		// lowercase letters that appear in s
		if (containsImpossibleTSubstrings(p))
			return null;
		
		Map<Character, Integer> curIndices = new HashMap<Character, Integer>();
		
		List<Integer> lengths = new ArrayList<Integer>();
		for (Map.Entry<Character, List<String>> entry : p.R.entrySet()) {
			curIndices.put(entry.getKey(), 0);
			lengths.add(entry.getValue().size());
		}
		
		boolean stop = false;
		while (true) {
			Map<Character, String> selection = convertToStringCombination(p, curIndices);
			if (verifyCombination(p, selection)) {
				return selection;
			}
			int i = 0;
			for (Map.Entry<Character, Integer> entry : curIndices.entrySet()) {
				curIndices.put(entry.getKey(), entry.getValue() + 1);
				
				if (entry.getValue() >= lengths.get(i) && i != curIndices.size() - 1) {
					curIndices.put(entry.getKey(), 0);
				} else if (i == curIndices.size() - 1 && entry.getValue() == lengths.get(i)) {
					stop = true;
					break;
				} else {
					break;
				}
				
				i++;
			}
			
			if (stop) {
				break;
			}
		}
		
		return null;
	}
	
	public static Map<Character, String> convertToStringCombination(Problem p, Map<Character, Integer> indices) {
		Map<Character, String> strings = new HashMap<Character, String>();
		
		// Get a string for each R for this combination
		int i = 0;
		for (Map.Entry<Character, Integer> entry : indices.entrySet()) {
			if (i >= indices.size())
				break;
			
			strings.put(entry.getKey(), p.R.get(entry.getKey()).get(entry.getValue()));
			i++;
		}
		
		return strings;
	}
	
	// Returns true if the given combination satisfies the problem
	public static boolean verifyCombination(Problem p, Map<Character, String> combinations) {
		boolean result = true;
		for (String t : p.T) {
			char[] tChars = t.toCharArray();
			
			String expansion = "";
			for (char letter : tChars) {
				if (combinations.containsKey(letter))
					expansion += combinations.get(letter);
				else if (letter >= 'a' && letter <= 'z')
					expansion += letter;
			}
			
			if (!p.s.contains(expansion)) {
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	// Saves the solution to a .SOL file
	public static void saveSolution(String filenumber, Map<Character, String> solution) throws IOException {
		String solutionAsString = "";
		
		for (Map.Entry<Character, String> entry : solution.entrySet()) {
			solutionAsString += entry.getKey() + ":" + entry.getValue() + "\n";
		}
		
		solutionAsString = solutionAsString.substring(0, solutionAsString.length() - 1);
		
		Files.write(Paths.get(path + "test" + filenumber + ".SOL"), solutionAsString.getBytes());
	}
	
	// Simplifies the problem using heuristic techniques
	public static void simplify(Problem p) {
		removeNonexistentLetters(p);
		removeImpossibleRSubstrings(p);
	}

	// Removes elements of R that are not substrings of s
	public static void removeImpossibleRSubstrings(Problem p) {
		for (Map.Entry<Character, List<String>> entry : p.R.entrySet()) {
			Iterator<String> iterator = entry.getValue().iterator();
			
			while (iterator.hasNext()) {
				if (!p.s.contains(iterator.next())) {
					iterator.remove();
				}
			}
		}
	}
	
	// Removes ts that contain a lowercase letter that doesn't exist in the string s
	public static boolean containsImpossibleTSubstrings(Problem p) {
		Iterator iterator = p.T.iterator();
		
		while (iterator.hasNext()) {
			String t = (String) iterator.next();
			
			for (char c : t.toCharArray()) {
				if (c >= 'a' && c <= 'z' && !p.s.contains(c + "")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	// Removes Rs (letters) that don't appear in any of the ts.
	public static void removeNonexistentLetters(Problem p) {
		Iterator iterator = p.R.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<Character, List<String>> entry = (Map.Entry) iterator.next();
			char letter = entry.getKey();
			
			boolean letterExists = false;
			for (String t : p.T) {
				if (t.contains(letter + "")) {
					letterExists = true;
					break;
				}
			}
			
			if (!letterExists) {
				iterator.remove();
			}
		}
	}
}
