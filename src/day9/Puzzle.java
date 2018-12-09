package day9;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Puzzle {

    public static final long[] TEST_DATA_1 = new long[]{9, 25, 32};
    public static final long[] TEST_DATA_2 = new long[]{10, 1618, 8317};
    public static final long[] TEST_DATA_3 = new long[]{13, 7999, 146373};
    public static final long[] TEST_DATA_4 = new long[]{17, 1104, 2764};
    public static final long[] TEST_DATA_5 = new long[]{21, 6111, 54718};
    public static final long[] TEST_DATA_6 = new long[]{30, 5807, 37305};
    public static final long[] TEST_DATA_7 = new long[]{419, 72164, 423717};
    public static final long[] TEST_DATA_8 = new long[]{419, 7216400, 3553108197L};
    public static final List<long[]> DATA_SETS = Arrays.asList(TEST_DATA_1, TEST_DATA_2, TEST_DATA_3, TEST_DATA_4, TEST_DATA_5, TEST_DATA_6, TEST_DATA_7, TEST_DATA_8);

    public static void main(String[] args) {
        for (long[] input : DATA_SETS) {

            int numPlayers = (int) input[0];
            long highestMarble = input[1];

            LinkedList<Integer> marbles = new LinkedList<>();
            marbles.add(0);

            int currentMarble = 1;
            long[] playerScores = new long[numPlayers];

            while (currentMarble <= highestMarble) {

                if (currentMarble % 23 == 0) {
//                    System.out.println(currentMarble);
                    for (int i = 0; i < 7; i++) {
                        Integer m = marbles.remove();
                        marbles.addLast(m);
                    }

                    int currentPlayer = currentMarble % numPlayers;
                    playerScores[currentPlayer] += currentMarble + marbles.pop();
                    Integer m = marbles.removeLast();
                    marbles.addFirst(m);
                } else {
                    Integer m = marbles.removeLast();
                    marbles.addFirst(m);
                    marbles.push(currentMarble);
                }
                currentMarble++;
            }

            long highestScore = 0;
            for (int i = 0; i < playerScores.length; i++) {
                if (playerScores[i] > highestScore) {
                    highestScore = playerScores[i];
                }
            }

            boolean pass = false;
            System.out.println("Highest score is " + highestScore);
            if (input.length > 2) {
                pass = input[2] == highestScore;
            }
            System.out.println(pass);
            if (!pass) {
                System.out.println("Test failed!");
                break;
            }
        }
    }
}
