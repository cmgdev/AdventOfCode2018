package day2;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle2 {

    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day2/input.txt";

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(new File(INPUT_FILE).toPath());
            List<String> copyOfLines = new ArrayList<>(lines);

            for (String line : lines) {
                for (String line2 : copyOfLines) {
                    String common = differenceBy1(line.toCharArray(), line2.toCharArray());
                    if (common != null) {
//                    if (isDifferentBy1(line, line2)) {
//                        System.out.println(line);
//                        System.out.println(line2);
                        System.out.println(common);
                        return;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Oh shit! " + e);
        }
    }

    private static boolean isDifferentBy1(String line, String line2) {
        int numDifferences = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != line2.charAt(i)) {
                numDifferences++;

                if (numDifferences > 1) {
                    return false;
                }
            }
        }
        return numDifferences == 1;
    }

    private static String differenceBy1(char[] line, char[] line2) {
        int numDifferences = 0;
        char[] newString = new char[line.length];
        for (int i = 0; i < line.length; i++) {
            if (line[i] != line2[i]) {
                numDifferences++;

                if (numDifferences > 1) {
                    return null;
                }
            } else {
                newString[i - numDifferences] = line[i];
            }
        }
        if (numDifferences == 1) {
            return String.valueOf(newString).trim();
        }
        return null;
    }
}
