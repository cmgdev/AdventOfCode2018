package day1;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Puzzle2 {

    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day1/input.txt";

    public static void main(String[] args) {
        int sum = 0;
        Set<Integer> sums = new HashSet<>();
        boolean seenDup = false;

        try {
            List<String> lines = Files.readAllLines(new File(INPUT_FILE).toPath());

            while (!seenDup) {
                for (String line : lines) {
                    sum += Integer.parseInt(line);
                    if (!sums.add(sum)) {
                        System.out.println("Already seen " + sum);
                        seenDup = true;
                        break;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Oh shit! " + e);
        }

        System.out.println("sum is " + sum);
        System.out.println(sums.size());
    }
}
