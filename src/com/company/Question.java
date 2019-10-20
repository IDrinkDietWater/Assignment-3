package com.company;

import java.security.SecureRandom;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Question {
    private int numberOne;
    private int numberTwo;
    private double solution;
    private static final int NUMBER_OF_MESSAGES = 4;
    private static final int NUMBER_OF_DECIMAL_PLACES = 2;
    private double providedAnswer;
    private static final int numberOfOperations = 4;
    private boolean isInitialized = false;
    private DifficultyLevel difficultyLevel;
    private Operation operation;
    private Operation currentOperation = null;

    private static SecureRandom  randomNumberGenerator = new SecureRandom();

    public Question(DifficultyLevel difficultyLevel, Operation operation) {
        this.difficultyLevel = difficultyLevel;
        this.operation = operation;
    }
    public void generatePrivateNumbers(){
        int base = 10;
        int numberOfDigits;

        switch (this.difficultyLevel) {
            case LEVEL_ONE:
                numberOfDigits = 1;
                break;
            case LEVEL_TWO:
                numberOfDigits = 2;
                break;
            case LEVEL_THREE:
                numberOfDigits = 3;
                break;
            case LEVEL_FOUR:
                numberOfDigits = 4;
                break;
            default:
                throw new IllegalArgumentException("Difficulty level not defined");
        }

        int maxNumber = (int)Math.pow(base, numberOfDigits) - 1;

        switch (this.operation) {
            case SUBTRACT:
            case MULTIPLY:
            case DIVIDE:
            case ADD:
                this.currentOperation = this.operation;
                break;
            case RANDOM:
                int randomOperationInteger = randomNumberGenerator.nextInt(numberOfOperations);
                switch (randomOperationInteger) {
                    case 0:
                        this.currentOperation = Operation.MULTIPLY;
                        setNumbersAndSolution(maxNumber);
                        break;
                    case 1:
                        this.currentOperation = Operation.DIVIDE;
                        setNumbersAndSolution(maxNumber);
                        break;
                    case 2:
                        this.currentOperation = Operation.ADD;
                        setNumbersAndSolution(maxNumber);
                        break;
                    case 3:
                        this.currentOperation = Operation.SUBTRACT;
                        setNumbersAndSolution(maxNumber);
                        break;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown Operation type chosen");
        }

        setNumbersAndSolution(maxNumber);
        this.isInitialized = true;
    }

    public boolean wasAnsweredCurrently(){
        return this.providedAnswer == this.solution;
    }

    private void displayCorrectAnswer(){
        int randomCorrectAnswer = randomNumberGenerator.nextInt(NUMBER_OF_MESSAGES) + 1;
        switch (randomCorrectAnswer) {
            case 1:
                System.out.println("Very good!");
                break;
            case 2:
                System.out.println("Excellent!");
                break;
            case 3:
                System.out.println("Nice work!");
                break;
            case 4:
                System.out.println("Keep up the good work!");
                break;
        }
    }

    private void displayIncorrectAnswer(){
        int randomIncorrectAnswer = randomNumberGenerator.nextInt(NUMBER_OF_MESSAGES) + 1;
        switch (randomIncorrectAnswer) {
            case 1:
                System.out.println("No. Please try again.");
                break;
            case 2:
                System.out.println("Wrong. Try once more.");
                break;
            case 3:
                System.out.println("Don't give up!");
                break;
            case 4:
                System.out.println("No. Keep trying.");
                break;
        }
    }
    private void setNumbersAndSolution(int maxNumber) {
        switch (this.currentOperation) {
            case ADD:
                this.numberOne = randomNumberGenerator.nextInt(maxNumber) + 1;
                this.numberTwo = randomNumberGenerator.nextInt(maxNumber) + 1;
                this.solution = this.numberOne + this.numberTwo;
                break;
            case DIVIDE:
                this.numberOne = randomNumberGenerator.nextInt(maxNumber) + 1;
                this.numberTwo = randomNumberGenerator.nextInt(maxNumber) + 1;
                this.solution = round(((double)this.numberOne / (double)this.numberTwo), NUMBER_OF_DECIMAL_PLACES);
                break;
            case MULTIPLY:
                this.numberOne = randomNumberGenerator.nextInt(maxNumber) + 1;
                this.numberTwo = randomNumberGenerator.nextInt(maxNumber) + 1;
                this.solution = this.numberOne * this.numberTwo;
                break;
            case SUBTRACT:
                this.numberOne = randomNumberGenerator.nextInt(maxNumber) + 1;
                this.numberTwo = randomNumberGenerator.nextInt(maxNumber) + 1;
                this.solution = this.numberOne - this.numberTwo;
                break;
        }
    }

    public void printQuestion() {
        System.out.printf("How much is %d %s %d?\n", this.numberOne, this.getQuestionAsString(), this.numberTwo );
    }

    private String getQuestionAsString() {
        if(!this.isInitialized) {
            generatePrivateNumbers();
        }

        String operationString;
        switch (this.currentOperation) {
            case ADD:
                operationString = "plus";
                break;
            case DIVIDE:
                operationString = "divided by";
                break;
            case MULTIPLY:
                operationString = "times";
                break;
            case SUBTRACT:
                operationString = "minus";
                break;
            default:
                throw new IllegalArgumentException("Could not determine operation string");
        }
        return operationString;
    }

    public void promptUserForAnswer(Scanner scanner) {

        Double userAnswer = null;

        while (userAnswer == null) {
            try {
                double tempUserAnswer = scanner.nextDouble();
                userAnswer = tempUserAnswer;
                this.providedAnswer = round(tempUserAnswer, NUMBER_OF_DECIMAL_PLACES);

            } catch (Exception ex) {
                System.out.printf("Error! %s\n", ex.getMessage());
                System.out.print("Please enter an integer solution! (Truncate value in division) \n");
            }
        }
    }

    public void displayMessage() {
        if(this.providedAnswer == this.solution) {
            displayCorrectAnswer();
        }
        else{
            displayIncorrectAnswer();
        }
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
