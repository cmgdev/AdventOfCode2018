package day6;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class Puzzle {

    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day6/input.txt";
    public static final boolean IS_TEST = false;
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int LARGEST_MANHATTAN_SUM = IS_TEST ? 32 : 10000;

    public static void main(String[] args) {
        List<String> input = Arrays.asList("1, 1", "1, 6", "8, 3", "3, 4", "5, 5", "8, 9");

        if (!IS_TEST) {
            try {
                input = Files.readAllLines(new File(INPUT_FILE).toPath());
            } catch (Exception e) {
                System.out.println("Oh shit! " + e);
            }
        }

        Map<String, String> map = new HashMap<>();

        int c = 0;
        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i).replace(" ", "");
            int alpha = i % ALPHABET.length();
            map.put(s, ALPHABET.substring(alpha, alpha + 1).concat(Integer.toString(c)));
            if (i == ALPHABET.length() - 1) {
                c++;
            }
        }

        /**********************
         * Part 1
         **********************/
        String[][] mapAsArray = mapToArray(map, true);
//        printMap(mapAsArray);

        System.out.println("Largest area is " + getSizeOfLargestFiniteArea(mapAsArray));

        /**********************
         * Part 2
         **********************/
        mapAsArray = mapToArray(map, false);
        printMap(mapAsArray);

        System.out.println("Size of region closest to coordinates is " + getSizeOfClosestRegion(mapAsArray, map));

    }

    private static int getSizeOfClosestRegion(String[][] mapAsArray, Map<String, String> map) {
        int size = 0;
        for (int i = 0; i < mapAsArray.length; i++) {
            for (int j = 0; j < mapAsArray[i].length; j++) {
                int sum = getSumOfManhattanDistances(i, j, map);
                if (sum != -1) {
                    size++;
                }
            }
        }

        return size;
    }

    private static int getSumOfManhattanDistances(int x, int y, Map<String, String> map) {
        int sum = 0;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String[] tokens = entry.getKey().split(",");
            int thisWidth = Integer.parseInt(tokens[0]);
            int thisHeight = Integer.parseInt(tokens[1]);
            sum += getManhattanDistance(x, y, thisWidth, thisHeight);
            if (sum >= LARGEST_MANHATTAN_SUM) {
                return -1;
            }
        }

        return sum;
    }

    private static void printMap(String[][] mapAsArray) {
        // the data is correct but got height/width mixed up somewhere
        // have to swap i & j on the array to print correctly
        for (int i = 0; i < mapAsArray[0].length; i++) {
            for (int j = 0; j < mapAsArray.length; j++) {
                System.out.print(mapAsArray[j][i]);
            }
            System.out.println();
        }
    }

    private static String[][] mapToArray(Map<String, String> map, boolean part1) {
        int[] arraySize = getArraySize(map.keySet());
        int width = arraySize[0];
        int height = arraySize[1];

        String[][] mapAsChars = new String[width][height];

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String[] tokens = entry.getKey().split(",");
            int thisWidth = Integer.parseInt(tokens[0]);
            int thisHeight = Integer.parseInt(tokens[1]);
            mapAsChars[thisWidth][thisHeight] = entry.getValue();
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (mapAsChars[i][j] == null) {
                    if (part1) {
                        mapAsChars[i][j] = getClosestLetter(i, j, map);
                    } else {
                        mapAsChars[i][j] = ".";
                    }
                }
            }
        }

        return mapAsChars;
    }

    private static int getSizeOfLargestFiniteArea(String[][] mapAsArray) {
        Map<String, Integer> areaSizes = new HashMap<>();
        int minX = 0;
        int minY = 0;
        int maxX = mapAsArray.length - 1;
        int maxY = mapAsArray[0].length - 1;

        for (int i = 0; i < mapAsArray.length; i++) {
            for (int j = 0; j < mapAsArray[i].length; j++) {
                String key = mapAsArray[i][j].toUpperCase();
                int areaSize = areaSizes.getOrDefault(key, 0);
                if (i == minX || i == maxX || j == minY || j == maxY) {
                    areaSize = -1;
                } else if (areaSize != -1) {
                    areaSize++;
                }
                areaSizes.put(key, areaSize);
            }
        }

        return areaSizes.values().stream().mapToInt(Integer::intValue).max().getAsInt();
    }

    private static String getClosestLetter(int x, int y, Map<String, String> map) {
        String closestLetter = ".";
        int smallestDistance = 99999;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String[] tokens = entry.getKey().split(",");
            int thisWidth = Integer.parseInt(tokens[0]);
            int thisHeight = Integer.parseInt(tokens[1]);
            int distance = getManhattanDistance(x, y, thisWidth, thisHeight);
            if (distance == smallestDistance) {
                closestLetter = ".";
            } else if (distance < smallestDistance) {
                smallestDistance = distance;
                closestLetter = entry.getValue().toLowerCase();
            }
        }

        return closestLetter;
    }

    private static int[] getArraySize(Set<String> keySet) {
        int[] arraySize = new int[2];

        for (String key : keySet) {
            String[] tokens = key.split(",");
            int thisWidth = Integer.parseInt(tokens[0]) + 1;
            int thisHeight = Integer.parseInt(tokens[1]) + 1;
            arraySize[0] = thisWidth > arraySize[0] ? thisWidth : arraySize[0];
            arraySize[1] = thisHeight > arraySize[1] ? thisHeight : arraySize[1];
        }

        return arraySize;
    }

    private static int getManhattanDistance(int i, int j, int x, int y) {
        int xDiff = Math.abs(i - x);
        int yDiff = Math.abs(j - y);
        return xDiff + yDiff;
    }

}
