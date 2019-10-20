package com.company;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {

    private static final int NUMBER_OF_QUESTIONS = 10;
    private static final double CORRECT_ANSWER_THRESHOLD = 0.75;
    private static Scanner scanner = new Scanner(System.in);
    private static int numberOfCorrectAnswers = 0;

    public static void main(String[] args) {

        while(true) {
            Scanner scnr = new Scanner(System.in);
            int a;

            Question[] questions = new Question[NUMBER_OF_QUESTIONS];
            DifficultyLevel difficultyLevel = getDifficultyLevel();
            Operation operation = getOperation();

            for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
                questions[i] = new Question(difficultyLevel, operation);
                questions[i].generatePrivateNumbers();
                System.out.printf("\nQuestion %d: ", i + 1);
                questions[i].printQuestion();
                questions[i].promptUserForAnswer(scanner);
                questions[i].displayMessage();
            }
            for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
                if (questions[i].wasAnsweredCurrently()) {
                    numberOfCorrectAnswers += 1;
                }
            }

            double percentageOfCorrectAnswers = numberOfCorrectAnswers / NUMBER_OF_QUESTIONS;

            if (percentageOfCorrectAnswers > CORRECT_ANSWER_THRESHOLD) {
                System.out.println("Congratulations, you are ready to go to the next level!\n\n");
            } else {
                System.out.println("Please ask your teacher for extra help.\n\n");
            }
        }
    }

    private static DifficultyLevel getDifficultyLevel() {
        final int NOT_SET_VALUE = -1;
        final int LEVEL_ONE = 1;
        final int LEVEL_TWO = 2;
        final int LEVEL_THREE = 3;
        final int LEVEL_FOUR = 4;

        int difficultyLevel = NOT_SET_VALUE;

        while (difficultyLevel == NOT_SET_VALUE) {
            System.out.println("New student");
            System.out.println("Choose your difficulty level");
            System.out.println("1 --> One digit");
            System.out.println("2 --> Up to two digits");
            System.out.println("3 --> Up to three digits");
            System.out.println("4 --> Up to four digits");

            System.out.print("Please enter a difficulty level between 1 and 4 (inclusive): ");
            if(difficultyLevel == NOT_SET_VALUE) {
                try {
                    int tempDifficultyLevel = scanner.nextInt();
                    if (tempDifficultyLevel >= 1 && tempDifficultyLevel <= 4) {
                        difficultyLevel = tempDifficultyLevel;
                    } else {
                        difficultyLevel = NOT_SET_VALUE;
                    }
                } catch (Exception ex) {
                    System.out.printf("Error! %s\n", ex.getMessage());
                }
            }
        }

        switch (difficultyLevel) {
            case LEVEL_ONE:
                return DifficultyLevel.LEVEL_ONE;
            case LEVEL_TWO:
                return DifficultyLevel.LEVEL_TWO;
            case LEVEL_THREE:
                return DifficultyLevel.LEVEL_THREE;
            case LEVEL_FOUR:
                return DifficultyLevel.LEVEL_FOUR;
            default:
                throw new IllegalArgumentException("Value of difficultyLevel must be between 1 and 4, inclusive");
        }

    }

    private static Operation getOperation() {
        final int NOT_SET_VALUE = -1;
        final int ADDITION = 1;
        final int SUBTRACTION = 2;
        final int MULTIPLICATION = 3;
        final int DIVISION = 4;
        final int RANDOM = 5;

        int operation = NOT_SET_VALUE;

        while (operation == NOT_SET_VALUE) {
            System.out.println("1 --> ADDITION");
            System.out.println("2 --> SUBTRACTION");
            System.out.println("3 --> MULTIPLICATION");
            System.out.println("4 --> DIVISION");
            System.out.println("5 --> RANDOM");
            System.out.print("Please enter a operation type between 1 and 5 (inclusive): ");
            if(operation == NOT_SET_VALUE) {
                try {
                    int tempOperationType = scanner.nextInt();
                    if (tempOperationType >= 1 && tempOperationType <= 5) {
                        operation = tempOperationType;
                    } else {
                        operation = NOT_SET_VALUE;
                    }
                } catch (Exception ex) {
                    System.out.printf("Error! %s\n", ex.getMessage());
                }
            }
        }

        switch (operation) {
            case ADDITION:
                return Operation.ADD;
            case SUBTRACTION:
                return Operation.SUBTRACT;
            case MULTIPLICATION:
                return Operation.MULTIPLY;
            case DIVISION:
                return Operation.DIVIDE;
            case RANDOM:
                return Operation.RANDOM;
            default:
                throw new IllegalArgumentException("Value of operation must be between 1 and 5, inclusive");
        }
    }
}
