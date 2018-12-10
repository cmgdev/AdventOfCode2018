package day01;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Puzzle1 {

    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day01/input.txt";

    public static void main(String[] args) {
        int sum = 0;

        try {
            List<String> lines = Files.readAllLines(new File(INPUT_FILE).toPath());
//            for (String line : lines) {
//                int i = Integer.parseInt(line);
//                sum = sum + i;
//            }
            sum = lines.stream().mapToInt(Integer::parseInt).reduce(0, Integer::sum);

        } catch (Exception e) {
            System.out.println("Oh shit! " + e);
        }

        System.out.println("sum is " + sum);
    }
}
