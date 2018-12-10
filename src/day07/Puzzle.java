package day07;

import base.AbstractPuzzle;

import java.util.List;

public class Puzzle extends AbstractPuzzle {

    public static final boolean IS_TEST = false;
    public static final int NUM_WORKERS = IS_TEST ? 2 : 5;
    public static final int MIN_SECONDS = IS_TEST ? 0 : 60;

    public Puzzle() {
        super(IS_TEST);
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle();
        List<String> input = puzzle.readFile(7);

        StateMachine stateMachine = new StateMachine(MIN_SECONDS);
        stateMachine.build(input);

        /********************************
         * Part 1
         ********************************/
        String result = "";
        while (stateMachine.hasMoreSteps()) {
            String nextStep = stateMachine.getNextStep();
            result += nextStep;
            stateMachine.completeStep(nextStep);
        }

        System.out.println("Result is " + result);
        System.out.print("Pass? ");
        if (IS_TEST) {
            System.out.println(result.equals("CABDFE"));
        } else {
            System.out.println(result.equals("IJLFUVDACEHGRZPNKQWSBTMXOY"));
        }

        System.out.println();
        /********************************
         * Part 2
         ********************************/
        stateMachine = new StateMachine(MIN_SECONDS);
        stateMachine.build(input);

        WorkCrew workCrew = new WorkCrew(NUM_WORKERS);

        result = "";
        printHeaders();


        while (stateMachine.hasMoreSteps()) {
            System.out.print(workCrew.getTimeWorking() + "\t\t");
            List<WorkCrew.Worker> workers = workCrew.startIteration();
            for (WorkCrew.Worker worker : workers) {
                String currentStep = worker.doWork();
                if (currentStep.matches("[A-Z]") && worker.isAvailable()) {
                    result += currentStep;
                    stateMachine.completeStep(currentStep);
                }
                if (worker.isAvailable()) {
                    String nextStep = stateMachine.getNextStep();
                    if (nextStep != null) {
                        worker.startStep(nextStep, stateMachine.getStepCost(nextStep));
                    }
                }
            }

            for (WorkCrew.Worker worker : workCrew.workersInIDOrder()) {
                System.out.print(worker.getCurrentStep() + "\t\t\t");
            }
            System.out.println(result);
        }

        int seconds = workCrew.getTimeWorking() - 1;
        System.out.println("Result is " + result);
        System.out.println("Time is " + seconds); //1072
        System.out.println("Pass? ");
        if (IS_TEST)

        {
            System.out.println(result.equals("CABFDE"));
            System.out.println(seconds == 15);
        } else

        {
            System.out.println(result.equals("IJLVFDUHACERGZPNQKWSBTMXOY"));
            System.out.println(seconds == 1072);
        }

    }

    private static void printHeaders() {
        System.out.print("Second\t");
        for (int i = 0; i < NUM_WORKERS; i++) {
            System.out.print("Worker " + (i + 1) + "\t");
        }
        System.out.println("Done");
    }

}
