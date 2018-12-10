package day04;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Puzzle {

    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day04/input.txt";

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(new File(INPUT_FILE).toPath());
            Collections.sort(lines);

            Map<Integer, int[]> guardSleep = new HashMap<>();

            for (int i = 0; i < lines.size(); i++) {
                String shiftBegin = lines.get(i);
                String[] tokens = shiftBegin.split(" ");
                Integer guardId = Integer.parseInt(tokens[3].replace("#", ""));

                while ((lines.size() - 1) > i && !lines.get(i + 1).contains("begins shift")) {
                    int fallAsleepMin = getMinuteFromLine(lines.get(++i));
                    int wakesMin = getMinuteFromLine(lines.get(++i));

                    int[] sleepMins = guardSleep.getOrDefault(guardId, new int[60]);
                    for (int m = fallAsleepMin; m < wakesMin; m++) {
                        sleepMins[m]++;
                    }
                    guardSleep.put(guardId, sleepMins);
                }
            }


            Map.Entry<Map.Entry<Integer, int[]>, Integer> maxGuardRecordWithTotal =
                    guardSleep.entrySet().stream()
                        .collect(Collectors.toMap(Function.identity(), e -> Arrays.stream(e.getValue()).sum()))
                        .entrySet().stream()
                            .collect(Collectors.maxBy(Comparator.comparingInt(e -> e.getValue()))).get();

//            int m = Arrays.stream(maxGuardRecordWithTotal.getKey().getValue()).max().getAsInt();

            Map.Entry<Integer, int[]> maxEntry = maxGuardRecordWithTotal.getKey();

            int maxMinute = 0;
            int maxMinuteAmount = 0;
            for (int i = 0; i < maxEntry.getValue().length; i++) {
                if (maxEntry.getValue()[i] > maxMinuteAmount) {
                    maxMinuteAmount = maxEntry.getValue()[i];
                    maxMinute = i;
                }
            }

            // 99911
//            System.out.println("First answer: " + maxEntry.getKey() + "x" + maxMinute + " = " + maxEntry.getKey() * maxMinute);
            System.out.println("First answer: " + maxGuardRecordWithTotal.getKey().getKey() + "x" + maxMinute + " = " + maxGuardRecordWithTotal.getKey().getKey() * maxMinute);


            /******************************************
             * Second answer
             ******************************************/
            maxEntry = null;
            maxMinute = 0;
            maxMinuteAmount = 0;
            for (Map.Entry<Integer, int[]> entry : guardSleep.entrySet()) {
                for (int i = 0; i < entry.getValue().length; i++) {
                    if (entry.getValue()[i] > maxMinuteAmount) {
                        maxMinuteAmount = entry.getValue()[i];
                        maxMinute = i;
                        maxEntry = entry;
                    }
                }
            }

            // 65854
            System.out.println("Second answer: " + maxEntry.getKey() + "x" + maxMinute + " = " + maxEntry.getKey() * maxMinute);


        } catch (Exception e) {
            System.out.println("Oh shit! " + e);
        }
    }

    private static int getMinuteFromLine(String line) {
        return Integer.parseInt(line.substring(15, 17));
    }
}
