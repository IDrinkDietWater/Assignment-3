package com.company;

public class SavingsAccount {
    private static double annualInterestRate;
    protected double savingsBalance;

    protected SavingsAccount() {
        savingsBalance = 0;
        annualInterestRate = 0;
    }

    protected SavingsAccount(double balance) {
        savingsBalance = balance;
        annualInterestRate = 0;
    }

    protected void calculateMonthlyInterest() {
        System.out.printf("Current savings balance: %.2f\n", savingsBalance);
        double monthlyInterest;
        monthlyInterest = (savingsBalance * annualInterestRate) / 12;
        savingsBalance += monthlyInterest;
        System.out.printf("New savings balance: %.2f\n", savingsBalance);
    }

    private double getBalance() {
        return savingsBalance;
    }

    class SpecialSavingsAccount extends SavingsAccount {
        protected void modifyInterestRate() {
            if (this.savingsBalance > 10000) {
                modifyInterestRate(.1);
            }
        }
    }

    protected static void modifyInterestRate(double newInterestRate) {
        annualInterestRate = newInterestRate;
    }


    static class Application {
        public static void main(String[] args) {
            int i;
            SavingsAccount saver1 = new SavingsAccount(2000);
            SavingsAccount saver2 = new SavingsAccount(3000);
            for (i = 0; i < 12; i++) {
                System.out.printf("Month %d\n", i + 1);
                saver1.modifyInterestRate(.04);
                saver1.calculateMonthlyInterest();
                saver2.modifyInterestRate(.04);
                saver2.calculateMonthlyInterest();

            }

            saver1.modifyInterestRate(.05);
            saver1.calculateMonthlyInterest();
            saver2.modifyInterestRate(.05);
            saver2.calculateMonthlyInterest();
        }
    }
}