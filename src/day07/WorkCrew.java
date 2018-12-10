package day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WorkCrew {

    List<Worker> workCrew = new ArrayList<>();
    int timeWorking = 0;
    String notWorking = ".";

    public WorkCrew(int numWorkers) {
        for (int i = 0; i < numWorkers; i++) {
            Worker worker = new Worker(i);
            workCrew.add(worker);
        }
    }

    public List<Worker> workersInIDOrder() {
        return workCrew.stream()
                .sorted(Comparator.comparingInt(Worker::getId))
                .collect(Collectors.toList());
    }

    public List<Worker> startIteration() {
        Collections.sort(workCrew);
        timeWorking++;
        return workCrew;
    }

    public int getTimeWorking() {
        return timeWorking;
    }

    public class Worker implements Comparable<Worker> {

        private String currentStep;
        private int timeRemaining;
        private int id;

        public Worker(int id) {
            this.id = id;
            this.currentStep = notWorking;
            this.timeRemaining = 0;
        }

        public String getCurrentStep() {
            return currentStep;
        }

        public int getId() {
            return id;
        }

        public boolean isAvailable() {
            return currentStep.equals(notWorking);
        }

        public void startStep(String step, int timeRemaining) {
            this.currentStep = step;
            this.timeRemaining = timeRemaining;
        }

        public String doWork() {
            String stepAtStart = currentStep;
            if (timeRemaining > 0) {
                timeRemaining--;
            }
            if (timeRemaining == 0) {
                currentStep = notWorking;
            }
            return stepAtStart;
        }

        @Override
        public int compareTo(Worker o) {
            return Integer.compare(o.timeRemaining, this.timeRemaining);
        }

        @Override
        public String toString() {
            return "Worker{" +
                    "currentStep='" + currentStep + '\'' +
                    ", timeRemaining=" + timeRemaining +
                    ", id=" + id +
                    '}';
        }
    }
}
