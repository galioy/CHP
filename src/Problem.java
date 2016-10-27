import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Problem {
	public String s;
	public List<String> T;
	public Map<Character, List<String>> R;
	
	public Problem() {
		this.T = new ArrayList<String>();
		this.R = new HashMap<Character, List<String>>();
	}
	
	public Problem(String s, List<String> T, Map<Character, List<String>> R) {
		this.s = s;
		this.T = T;
		this.R = R;
	}
	
	public String toString() {
		String result;
		
		result = s + "\n";
		result += String.join("\n", T) + "\n";

		for (Map.Entry<Character, List<String>> entry : R.entrySet()) {
			result += entry.getKey() + ":" + String.join(",", entry.getValue()) + "\n";
		}
		
		return result;
	}
}
