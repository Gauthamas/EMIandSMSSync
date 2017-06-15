package com.example.gauthama.emiandsmssync;


public class Utils {

    public static double getEmi(double loan, double interest, double months){
        double emi;
        emi = loan*interest;

        double interestRate =Math.pow(interest+1, months);
        interestRate = (interestRate/(interestRate-1));
        emi = emi*interestRate;

        return emi;

    }

    public static double getAmount(double months, double emi){
        return emi*months;

    }

    public static double getTotalInterest(double loan, double emi, double months){
        return  (getAmount(months, emi) - loan);
    }
}
