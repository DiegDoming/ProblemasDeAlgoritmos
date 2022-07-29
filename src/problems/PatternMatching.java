package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PatternMatching {

    /**
     * Given an array strings, determine whether it follows the sequence given in the patterns array. In
     * other words, there should be no i and j for which strings[i] = strings[j] and patterns[i] ≠ patterns[j]
     * or for which strings[i] ≠ strings[j] and patterns[i] = patterns[j].
     * @param strings An array of strings, each containing only lowercase English letters.
     * @param patterns An array of pattern strings, each containing only lowercase English letters.
     * @return Return true if strings follows patterns and false otherwise.
     */
    public boolean solution(String[] strings, String[] patterns) {
        HashMap<String, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < patterns.length; i++) {
            String p = patterns[i];
            map.computeIfAbsent(p, k -> new ArrayList<>()).add(i);
        }

        for (var entry : map.entrySet()) {
            List<Integer> value = entry.getValue();
            String sample = strings[value.get(0)];
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].equals(sample) != value.contains(i))
                    return false;
            }

        }


        return true;
    }

}
