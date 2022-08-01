package problems;

import java.util.*;


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

    /**
     * You have a collection of coins, and you know the values of the coins and the quantity of each <br>
     * type of coin in it. You want to know how many distinct sums you can make from non-empty groupings of these coins.
     *
     * @param coins    An array containing the values of the coins in your collection.
     * @param quantity An array containing the quantity of each type of coin in your collection. quantity[i] indicates the number of coins that have a value of coins[i].
     * @return The number of different possible sums that can be created from non-empty groupings of your coins.
     */
    public int possibleSums(int[] coins, int[] quantity) {
        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < coins.length; i++)
            for (int j = 1; j <= quantity[i]; j++)
                possibleSumsStep(i + 1, coins[i] * j, coins, quantity, set);

        return set.size();
    }

    private void possibleSumsStep(int index, int total, int[] coins, int[] quantity, HashSet<Integer> set) {
        set.add(total);
        for (int i = index; i < coins.length; i++) {
            for (int j = 1; j <= quantity[i]; j++)
                possibleSumsStep(i + 1, total + (coins[i] * j), coins, quantity, set);
        }
    }

    /**
     * Given a binary tree t and an integer s, determine whether there is a root to leaf path in t such that the sum of vertex values equals s.
     *
     * @param t A binary tree of integers
     * @param s An integer.
     * @return Return true if there is a path from root to leaf in t such that the sum of node values in it is equal to s, otherwise return false.
     */
    public boolean hasPathWithGivenSum(Tree<Integer> t, int s) {
        if (t == null)
            return false;
        return hasPathWithGivenSumStep(t, 0, s);
    }

    private boolean hasPathWithGivenSumStep(Tree<Integer> t, int acum, int s) {
        acum += t.value;
        if (t.left != null)
            if (hasPathWithGivenSumStep(t.left, acum, s))
                return true;
        if (t.right != null)
            if (hasPathWithGivenSumStep(t.right, acum, s))
                return true;

        return (t.left == null && t.right == null && acum == s);
    }

    /**
     * The pixels in the input image are represented as integers. The algorithm distorts the input image in the following way: <br>
     * Every pixel x in the output image has a value equal to the average value of the pixel values from the 3 × 3 square that has <br>
     * its center at x, including x itself. All the pixels on the border of x are then removed.<br>
     * <br>
     * Return the blurred image as an integer, with the fractions rounded down.
     *
     * @param image An image, stored as a rectangular matrix of non-negative integers.
     * @return A blurred image represented as integers, obtained through the process in the description.
     */
    public int[][] boxBlur(int[][] image) {
        int[][] e = new int[image.length - 2][image[0].length - 2];
        for (int i = 1; i < image.length - 1; i++) {
            for (int j = 1; j < image[0].length - 1; j++) {
                e[i - 1][j - 1] = boxBlurAverage(i, j, image);
            }
        }

        return e;
    }

    private int boxBlurAverage(int x, int y, int[][] image) {
        int avg = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                avg += image[i][j];
            }
        }

        return avg / 9;
    }

    /**
     * You are given an array of integers representing coordinates of obstacles situated on a straight line.<br>
     * <br>
     * Assume that you are jumping from the point with coordinate 0 to the right. You are allowed only to make jumps of the same length represented by some integer.<br>
     * <br>
     * Find the minimal length of the jump enough to avoid all the obstacles.
     *
     * @param inputArray Non-empty array of positive integers.
     * @return The desired length.
     */
    public int avoidObstacles(int[] inputArray) {
        HashSet<Integer> set = new HashSet<>();
        int max = Integer.MIN_VALUE;
        for (int i : inputArray) {
            set.add(i);
            max = Math.max(max, i);
        }

        int c = 1;
        while (true) {
            int i = 0;
            while (true) {
                if (set.contains(i))
                    break;
                else {
                    if (i > max)
                        return c;
                }
                i += c;
            }
            c++;
        }

    }

    /**
     * Given an array of integers, find the maximal absolute difference between any two of its adjacent elements.
     *
     * @param inputArray An array of integers
     * @return The maximal absolute difference.
     */
    public int arrayMaximalAdjacentDifference(int[] inputArray) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < inputArray.length - 1; i++)
            max = Math.max(max, Math.abs(inputArray[i] - inputArray[i + 1]));

        return max;

    }

    /**
     * Given a string, find out if its characters can be rearranged to form a palindrome.
     *
     * @param inputString A string consisting of lowercase English letters.
     * @return true if the characters of the inputString can be rearranged to form a palindrome, false otherwise.
     */
    public boolean palindromeRearranging(String inputString) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < inputString.length(); i++) {
            map.putIfAbsent(inputString.charAt(i), 0);
            map.put(inputString.charAt(i), map.get(inputString.charAt(i)) + 1);
        }

        int count = 0;

        for (var entry : map.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                if (inputString.length() % 2 == 0)
                    return false;
                count++;
                if (count > 2)
                    return false;
            }
        }

        return true;
    }

    /**
     * You are given an array of integers. On each move you are allowed to increase exactly one <br>
     * of its element by one. Find the minimal number of moves required to obtain a strictly increasing sequence from the input.
     *
     * @param inputArray An array of integers
     * @return The minimal number of moves needed to obtain a strictly increasing sequence from inputArray. <br>
     * It's guaranteed that for the given test cases the answer always fits signed 32-bit integer type.
     */
    public int arrayChange(int[] inputArray) {
        int count = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i] >= inputArray[i + 1]) {
                count += inputArray[i] - inputArray[i + 1] + 1;
                inputArray[i + 1] += inputArray[i] - inputArray[i + 1] + 1;
            }
        }

        return count;
    }

    /**
     * Two arrays are called similar if one can be obtained from another by swapping<br>
     * at most one pair of elements in one of the arrays.<br>
     * <br>
     * Given two arrays a and b, check whether they are similar
     *
     * @param a Array of integers.
     * @param b Array of integers of the same length as a.
     * @return true if a and b are similar, false otherwise.
     */
    public boolean areSimilar(int[] a, int[] b) {
        int count = 0;
        int[][] swap = new int[2][2];
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                count++;
                if (count > 2)
                    return false;
                swap[count - 1][0] = a[i];
                swap[count - 1][1] = b[i];
            }
        }

        if (count == 0)
            return true;

        if (count == 1)
            return false;

        return swap[0][0] == swap[1][1] && swap[1][0] == swap[0][1];
    }

    /**
     * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
     *
     * @param picture A non-empty array of non-empty equal-length strings.
     * @return The same matrix of characters, framed with a border of asterisks of width 1.
     */
    public String[] addBorder(String[] picture) {
        String[] s = new String[picture.length + 2];
        s[0] = s[s.length - 1] = new String(new char[picture[0].length() + 2]).replace('\0', '*');

        for (int i = 0; i < picture.length; i++) {
            s[i + 1] = "*" + picture[i] + "*";
        }

        return s;
    }

    /**
     * Several people are standing in a row and need to be divided into two teams. <br>
     * The first person goes into team 1, the second goes into team 2, the third goes into team 1 <br>
     * again, the fourth into team 2, and so on.<br>
     * <br>
     * You are given an array of positive integers - the weights of the people. Return an array of<br>
     * two integers, where the first element is the total weight of team 1, and the second element is <br>
     * the total weight of team 2 after the division is complete.<br>
     *
     * @param a An integer array
     * @return The sums
     */
    public int[] alternatingSums(int[] a) {
        int[] sol = new int[2];
        for (int i = 0; i < a.length; i++)
            sol[i % 2] += a[i];

        return sol;
    }

    /**
     * Some people are standing in a row in a park. There are trees between them which cannot be moved.<br>
     * Your task is to rearrange the people by their heights in a non-descending order without moving the trees.
     *
     * @param a If a[i] = -1, then the ith position is occupied by a tree. Otherwise a[i] is the height of a person<br>
     *          standing in the ith position.
     * @return Sorted array a with all the trees untouched.
     */
    public int[] sortByHeight(int[] a) {
        int[] b = Arrays.copyOf(a, a.length);
        Arrays.sort(b);
        int index = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1) {
                while (index < b.length && b[index] == -1)
                    index++;
                a[i] = b[index];
                index++;
            }
        }

        return a;
    }

    private static class ListNode<T> {
        final T value;
        ListNode<T> next;
        ListNode(T x) {
            value = x;
        }
    }

    private static class Tree<T> {
        final T value;
        Tree<T> left;
        Tree<T> right;
        Tree(T x) {
            value = x;
        }
    }

}
