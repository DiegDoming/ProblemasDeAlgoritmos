package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class CodingInterviews {

    /**
     * Given an array strings, determine whether it follows the sequence given in the patterns array. In<br>
     * other words, there should be no i and j for which strings[i] = strings[j] and patterns[i] ≠ patterns[j]<br>
     * or for which strings[i] ≠ strings[j] and patterns[i] = patterns[j].<br>
     * <p>
     * p -- Pattern.length<br>
     * d -- DifferentPatterns<br>
     * n -- String.length<br>
     * Time Complexity. p+(d*n)<br>
     * O-notation: O(dn)<br>
     *
     * @param strings  An array of strings, each containing only lowercase English letters.
     * @param patterns An array of pattern strings, each containing only lowercase English letters.
     * @return Return true if strings follows patterns and false otherwise.
     */
    public boolean patternMatching(String[] strings, String[] patterns) {
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

    /**
     * Given an array a that contains only numbers in the range from 1 to a.length, find the first <br>
     * duplicate number for which the second occurrence has the minimal index. In other words, if there<br>
     * are more than 1 duplicated numbers, return the number for which the second occurrence has a smaller <br>
     * index than the second occurrence of the other number does. If there are no such elements, return -1.<br>
     *
     * @param a array a that contains only numbers in the range from 1 to a.length
     * @return The element in a that occurs in the array more than once and has the minimal index for its second occurrence. If there are no such elements, return -1.
     */
    public int firstDuplicate(int[] a) {
        boolean[] map = new boolean[a.length + 1];
        for (int i : a)
            if (map[i])
                return i;
            else
                map[i] = true;

        return -1;

    }

    /**
     * Given a string s consisting of small English letters, find and return <br>
     * the first instance of a non-repeating character in it. If there is no such character, return '_'.
     *
     * @param s A string that contains only lowercase English letters.
     * @return The first non-repeating character in s, or '_' if there are no characters that do not repeat.
     */
    public char firstNotRepeatingCharacter(String s) {
        int count;
        for (int i = 0; i < s.length(); i++) {
            count = 0;
            for (int j = 0; j < s.length(); j++) {
                if (i != j && s.charAt(i) == s.charAt(j))
                    break;
                count++;
            }
            if (count == s.length())
                return s.charAt(i);
        }

        return '_';
    }

    /**
     * You are given an n x n 2D matrix that represents an image. Rotate the image by 90 degrees (clockwise).
     *
     * @param a nxn matrix
     * @return nxn rotated matrix
     */
    public int[][] rotateImage(int[][] a) {
        int[][] b = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                b[j][(a.length - 1) - i] = a[i][j];
            }
        }
        return b;
    }

    /**
     * Sudoku is a number-placement puzzle. The objective is to fill a 9 × 9 grid with numbers in such a way that each column,<br>
     * each row, and each of the nine 3 × 3 sub-grids that compose the grid all contain all of the numbers from 1 to 9 one time.<br>
     * <br>
     * Implement an algorithm that will check whether the given grid of numbers represents a valid Sudoku puzzle according to <br>
     * the layout rules described above. Note that the puzzle represented by grid does not have to be solvable.<br>
     *
     * @param grid A 9 × 9 array of characters, in which each character is either a digit from '1' to '9' or a period '.'
     * @return Return true if grid represents a valid Sudoku puzzle, otherwise return false.
     */
    public boolean checkSudokuValidity(char[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == '.')
                    continue;
                for (int x = (i / 3) * 3; x < (i / 3) * 3 + 3; x++) {
                    for (int y = (j / 3) * 3; y < (j / 3) * 3 + 3; y++) {
                        if (i != x && y != j && grid[i][j] == grid[x][y])
                            return false;
                    }
                }

                for (int x = 0; x < 9; x++) {
                    if (x != i && grid[i][j] == grid[x][j])
                        return false;
                }

                for (int x = 0; x < 9; x++) {
                    if (x != j && grid[i][j] == grid[i][x])
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * A cryptarithm is a mathematical puzzle for which the goal is to find the correspondence between letters and digits, <br>
     * such that the given arithmetic equation consisting of letters holds true when the letters are converted to digits.<br>
     * <br>
     * You have an array of strings crypt, the cryptarithm, and an an array containing the mapping of letters and digits, <br>
     * solution. The array crypt will contain three non-empty strings that follow the structure: [word1, word2, word3], <br>
     * which should be interpreted as the word1 + word2 = word3 cryptarithm.<br>
     * <br>
     * If crypt, when it is decoded by replacing all of the letters in the cryptarithm with digits using the mapping in <br>
     * solution, becomes a valid arithmetic equation containing no numbers with leading zeroes, the answer is true.<br>
     * If it does not become a valid arithmetic solution, the answer is false.<br>
     * <p>
     * Note that number 0 doesn't contain leading zeroes (while for example 00 or 0123 do).
     *
     * @param crypt    An array of three non-empty strings containing only uppercase English letters.
     * @param solution An array consisting of pairs of characters that represent the correspondence between letters and numbers in the cryptarithm. <br>
     *                 The first character in the pair is an uppercase English letter, and the second one is a digit in the range from 0 to 9.<br>
     *                 It is guaranteed that solution only contains entries for the letters present in crypt and that different letters have different values.
     * @return Return true if the solution represents the correct solution to the cryptarithm crypt, otherwise return false.
     */
    public boolean isCryptSolution(String[] crypt, char[][] solution) {

        char c = ' ';
        for (char[] c2 : solution)
            if (c2[1] == '0')
                c = c2[0];

        for (String s : crypt)
            if (s.length() > 1 && s.charAt(0) == c)
                return false;

        int value = 0;
        for (int m = 0; m < crypt.length - 1; m++) {
            String s = crypt[m];
            for (int i = 0; i < s.length(); i++)
                for (char[] c2 : solution)
                    if (c2[0] == s.charAt(i))
                        value += ((int) Math.pow(10, (s.length() - i - 1))) * (c2[1] - 48);
        }

        String s = crypt[crypt.length - 1];
        for (int i = 0; i < s.length(); i++)
            for (char[] c2 : solution)
                if (c2[0] == s.charAt(i))
                    value -= ((int) Math.pow(10, (s.length() - i - 1))) * (c2[1] - 48);
        return value == 0;
    }


    /**
     * Given a singly linked list of integers l and an integer k, remove all elements from list l that have a value equal to k
     *
     * @param l A singly linked list of integers.
     * @param k An integer.
     * @return Return l with all the values equal to k removed.
     */
    public ListNode<Integer> removeKFromList(ListNode<Integer> l, int k) {
        if (l == null)
            return null;

        ListNode<Integer> ini = l;
        while (ini != null && ini.value == k)
            ini = ini.next;

        if (ini == null)
            return null;

        while (l.next != null)
            if (l.next.value == k) {
                l.next = l.next.next;
                if (l.next == null)
                    break;
            } else
                l = l.next;

        return ini;
    }

    private static class ListNode<T> {
        ListNode(T x) {
            value = x;
        }

        final T value;
        ListNode<T> next;
    }

    /**
     * You have a collection of coins, and you know the values of the coins and the quantity of each <br>
     * type of coin in it. You want to know how many distinct sums you can make from non-empty groupings of these coins.
     * @param coins An array containing the values of the coins in your collection.
     * @param quantity An array containing the quantity of each type of coin in your collection. quantity[i] indicates the number of coins that have a value of coins[i].
     * @return The number of different possible sums that can be created from non-empty groupings of your coins.
     */
    public int possibleSums(int[] coins, int[] quantity) {
        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < coins.length; i++)
            for (int j = 1; j <= quantity[i]; j++)
                possibleSumsstep(i + 1, coins[i] * j, coins, quantity, set);

        return set.size();
    }

    private void possibleSumsstep(int index, int total, int[] coins, int[] quantity, HashSet<Integer> set) {
        set.add(total);
        for (int i = index; i < coins.length; i++) {
            for (int j = 1; j <= quantity[i]; j++)
                possibleSumsstep(i + 1, total + (coins[i] * j), coins, quantity, set);
        }
    }

}
