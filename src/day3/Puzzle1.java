package day3;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Puzzle1 {

    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day3/input.txt";

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(new File(INPUT_FILE).toPath());

            int[][] fabric = new int[1000][1000];

            for (String line : lines) {
                String[] tokens = line.split(" ");
                String[] coordinates = (tokens[2].replace(":", "")).split(",");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                String[] size = (tokens[3]).split("x");
                int length = Integer.parseInt(size[0]);
                int height = Integer.parseInt(size[1]);

                for (int i = x; i < x + length; i++) {
                    for (int j = y; j < y + height; j++) {
                        fabric[i][j]++;
                    }
                }
            }

            int count = 0;
            for (int i = 0; i < fabric.length; i++) {
                for (int j = 0; j < fabric[i].length; j++) {
                    if (fabric[i][j] > 1) {
                        count++;
                    }
                }
            }

            System.out.println(count);

        } catch (Exception e) {

        }
    }
}
