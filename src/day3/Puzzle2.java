package day3;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Puzzle2 {
    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day3/input.txt";

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(new File(INPUT_FILE).toPath());

            Fabric fabric = new Fabric(1000, 1000);
            fabric.slice(lines);
            System.out.println(fabric.getIntactSlices());

        } catch (Exception e) {

        }
    }
}
