package day11;

public class Puzzle {

    public static void main(String[] args) {
        if (!runTestCases()) {
            System.out.println("Tests failed!");
            return;
        }

        System.out.println("Tests passed...");

        int serialNumber = 5235;
        int highestPower = 0;
        int x = 0;
        int y = 0;

        /******************
         * Part 1
         ******************/
        for (int i = 1; i <= 255; i++) {
            for (int j = 1; j < 255; j++) {
                int gridPower = get3x3GridPower(i, j, serialNumber);
                if (gridPower > highestPower) {
                    highestPower = gridPower;
                    x = i;
                    y = j;
                    System.out.println(highestPower + " @ " + x + "," + y);
                }
            }
        }

        if (highestPower != 28 || x != 33 || y != 54) {
            System.out.println("Failed!");
            return;
        }
    }

    public static int get3x3GridPower(int startX, int startY, int serialNumber) {
        return getSquareGridPower(startX, startY, 3, serialNumber);
    }

    public static int getSquareGridPower(int startX, int startY, int size, int serialNumber) {
        int powerLevel = 0;

        for (int i = startX; i < startX + size; i++) {
            for (int j = startY; j < startY + size; j++) {
                powerLevel += getPowerLevel(i, j, serialNumber);
            }
        }

        return powerLevel;
    }

    public static boolean runTestCases() {
        boolean test1 = getPowerLevel(3, 5, 8) == 4;
        boolean test2 = getPowerLevel(122, 79, 57) == -5;
        boolean test3 = getPowerLevel(217, 196, 39) == 0;
        boolean test4 = getPowerLevel(101, 153, 71) == 4;

        boolean test5 = get3x3GridPower(33, 45, 18) == 29;
        boolean test6 = get3x3GridPower(21, 61, 42) == 30;

        return test1 && test2 && test3 && test4 && test5 && test6;
    }

    public static int getPowerLevel(int x, int y, int serialNumber) {
        int rackId = x + 10;
        return (((((rackId * y) + serialNumber) * rackId) / 100) % 10) - 5;
    }
}
