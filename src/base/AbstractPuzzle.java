package base;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPuzzle {
    public static final String ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALPHABET_LOWER = ALPHABET_UPPER.toLowerCase();

    private boolean isTest;

    public AbstractPuzzle(boolean isTest) {
        this.isTest = isTest;
    }

    public List<String> readFile(int day) {
        String fileName = isTest ? "example.txt" : "input.txt";
        String dayString = day < 10 ? "0" + day : Integer.toString( day );

        String inputFile = System.getProperty("user.dir") + "/out/production/advent2018/day" + dayString + "/" + fileName;

        List<String> input = new ArrayList<>();

        try {
            input = Files.readAllLines(new File(inputFile).toPath());
        } catch (Exception e) {
            System.out.println("Oh shit! " + e);
        }
        return input;
    }

}
