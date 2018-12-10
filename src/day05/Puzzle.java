package day05;

import java.io.File;
import java.nio.file.Files;

public class Puzzle {

    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day05/input.txt";
    public static final boolean IS_TEST = false;
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        String input = "dabAcCaCBAcCcaDA";
        if (!IS_TEST) {
            try {
                input = Files.readAllLines(new File(INPUT_FILE).toPath()).get(0);
            } catch (Exception e) {
                System.out.println("Oh shit! " + e);
            }
        }

        int shortestLength = input.length();
        String removed = ALPHABET.substring(0, 1);

        for (int i = 0; i < ALPHABET.length(); i++) {
            String removedThisIteration = ALPHABET.substring(i, i + 1);
            String newInput = input.replace(removedThisIteration, "")
                    .replace(removedThisIteration.toUpperCase(), "");

            String result = doPolymerReaction(newInput);

//            System.out.println("Final polymer: " + result);
            System.out.println( "Removed this iteration: " + removedThisIteration);
            System.out.println("Final length: " + result.length());  // 9526

            if (result.length() < shortestLength) {
                shortestLength = result.length();
                removed = removedThisIteration;
            }
        }

        System.out.println( "Shortest length is " + shortestLength); // 6694
        System.out.println( "By removing " + removed ); // d/D
    }

    private static String doPolymerReaction(String input) {
        boolean changedThisIteration = true;
        do {
            int startLength = input.length();

            for (int i = 0; i < input.length() - 1; i++) {
                Character thisChar = input.charAt(i);
                Character nextChar = input.charAt(i + 1);
                if (Character.toUpperCase(thisChar) == Character.toUpperCase(nextChar)
                        && Character.isUpperCase(thisChar) != Character.isUpperCase(nextChar)) {
//                    System.out.println("Polymer reaction on: " + input);
                    input = input.replaceFirst(new String(new char[]{thisChar, nextChar}), "");
                    break;
                }
            }

            if (startLength == input.length()) {
                changedThisIteration = false;
            }
        } while (changedThisIteration);
        return input;
    }
}
