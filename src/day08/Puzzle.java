package day08;

import base.AbstractPuzzle;

import java.util.Arrays;
import java.util.List;

public class Puzzle extends AbstractPuzzle {

    public static final boolean IS_TEST = false;

    public Puzzle() {
        super(IS_TEST);
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle();
        List<String> input = puzzle.readFile(8);

        String[] inputs = input.get(0).split(" ");

        Node tree = new Node(Arrays.asList(inputs).iterator());

        /*************************
         * Part 1
         *************************/
        int sumAllMetadata = tree.getSumAllMetadata();

        System.out.println("Sum is " + sumAllMetadata);
        if (IS_TEST) {
            System.out.println(sumAllMetadata == 138);
        } else {
            System.out.println(sumAllMetadata == 49180);
        }

        System.out.println();
        /**************************
         * Part 2
         **************************/
        int rootValue = tree.getNodeValue();

        System.out.println("Value is " + rootValue);
        if (IS_TEST) {
            System.out.println(rootValue == 66);
        } else {
            System.out.println(rootValue == 20611);
        }
    }

}
