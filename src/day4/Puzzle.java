package day4;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle {

    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day4/input.txt";
    public static final String OUTPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day4/output.txt";

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(new File(INPUT_FILE).toPath());
            lines.sort(Comparator.comparing(String::new));

            Files.write(new File(OUTPUT_FILE).toPath(), lines);

            Map<String, int[]> guardSleep = new HashMap<>();

            for (int i = 0; i < lines.size(); i++) {
                String shiftBegin = lines.get(i);
                String[] tokens = shiftBegin.split(" ");
                String guardId = tokens[3].replace("#", "");

                while ((lines.size() - 1) > i && !lines.get(i + 1).contains("begins shift")) {
                    i++;
                    String fallsAsleep = lines.get(i);
                    int fallAsleepMin = Integer.parseInt(fallsAsleep.split(" ")[1].split(":")[1].replace("]", ""));

                    i++;
                    String wakes = lines.get(i);
                    int wakesMin = Integer.parseInt(wakes.split(" ")[1].split(":")[1].replace("]", ""));

                    int[] sleepMins = guardSleep.getOrDefault(guardId, new int[60]);
                    for (int m = fallAsleepMin; m < wakesMin; m++) {
                        sleepMins[m]++;
                    }
                    guardSleep.put(guardId, sleepMins);
                }
            }

            Map.Entry<String, int[]> maxEntry = null;
            int maxSum = 0;
            for( Map.Entry<String, int[]> entry : guardSleep.entrySet()){
                int sum = 0;
                for( int i = 0; i < entry.getValue().length; i++){
                    sum += entry.getValue()[i];
                }
                if( maxEntry == null || sum > maxSum){
                    maxEntry = entry;
                    maxSum = sum;
                }
            }

            int maxMinute = 0;
            int maxMinuteAmount = 0;
            for( int i = 0; i < maxEntry.getValue().length; i++){
                if( maxEntry.getValue()[i] > maxMinuteAmount){
                    maxMinuteAmount = maxEntry.getValue()[i];
                    maxMinute = i;
                }
            }

            // 99911
            System.out.println("First answer: " + maxEntry.getKey() + "x" + maxMinute);


            /******************************************
             * Second answer
             ******************************************/

        } catch (Exception e) {
            System.out.println("Oh shit! " + e);
        }
    }
}
