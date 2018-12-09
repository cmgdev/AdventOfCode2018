package day9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle {

    public static final int[] TEST_DATA_1 = new int[]{9, 25, 32};
    public static final int[] TEST_DATA_2 = new int[]{10, 1618, 8317};
    public static final int[] TEST_DATA_3 = new int[]{13, 7999, 146373};
    public static final int[] TEST_DATA_4 = new int[]{17, 1104, 2764};
    public static final int[] TEST_DATA_5 = new int[]{21, 6111, 54718};
    public static final int[] TEST_DATA_6 = new int[]{30, 5807, 37305};
    public static final int[] TEST_DATA_7 = new int[]{419, 72164, 423717};
    public static final int[] TEST_DATA_8 = new int[]{419, 7216400};


    public static void main(String[] args) {
        int[] input = TEST_DATA_8;

        int numPlayers = input[0];
        int highestMarble = input[1];

        List<Integer> marbles = new ArrayList<>();
        marbles.add(0);

        System.out.println("[-] " + marbles);

        int currentMarble = 1;
        int currentPlayer = 0;
        int currentIndex = 0;

        Map<Integer, Integer> playerScores = new HashMap<>();

        while (currentMarble <= highestMarble) {
            System.out.println(currentMarble);
            if (currentMarble % 23 == 0) {
                int playerScore = playerScores.getOrDefault(currentPlayer + 1, 0);
                playerScore += currentMarble;

                currentIndex = (currentIndex - 7) % marbles.size();
                if(currentIndex < 0){
                    currentIndex = marbles.size() + currentIndex;
                }

                int additionalMarble = marbles.remove(currentIndex);
                playerScore += additionalMarble;

                playerScores.put(currentPlayer + 1, playerScore);

//                System.out.println("[" + (currentPlayer + 1) + "] " + marbles);
            } else {
                currentIndex = (currentIndex + 1) % marbles.size() + 1;
                marbles.add(currentIndex, currentMarble);
            }

            currentMarble++;
            currentPlayer = (currentPlayer + 1) % numPlayers;
        }

        System.out.println(playerScores);

        int highestScore = playerScores.values().stream()
                .mapToInt(Integer::intValue)
                .max().getAsInt();

        System.out.println("Highest score is " + highestScore);
        if (input.length > 2) {
            System.out.println(input[2] == highestScore);
        }
    }
}
