package day02;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Puzzle1 {

    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day02/input.txt";

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(new File(INPUT_FILE).toPath());
            int num2s = 0;
            int num3s = 0;
            int asciiOffset = 97;

            for (String line : lines) {

                int[] charCounts = new int[26];
                line.chars().forEach(c -> charCounts[c - asciiOffset]++);
                if (Arrays.stream(charCounts).anyMatch(c -> c == 2)) {
                    num2s++;
                }
                if (Arrays.stream(charCounts).anyMatch(c -> c == 3)) {
                    num3s++;
                }
            }

            System.out.println(num2s);
            System.out.println(num3s);
            System.out.println("Check sum is " + num2s * num3s);

        } catch (Exception e) {
            System.out.println("Oh shit! " + e);
        }
    }
}
