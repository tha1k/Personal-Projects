package com.example.familybudget;

import java.time.LocalDate;

public class Income extends Transactions {
    public Income(Money money, boolean isRecurring, LocalDate registrationDate, LocalDate endDate, String description) {
        super(money, isRecurring, registrationDate, endDate, description);
    }
    public Income(){}
    public String toString(){
        String recurring;
        if(getIsRecurring()){
            recurring = "The income is recurring";
        }else{
            recurring = "The income is not recurring";
        }
        return "Transaction - Income: \n" +
                "Money: " + getMoney() + "\n" +
                "Registration date: " + getRegistrationDate() + "\n" +
                "End date: " + getEndDate() + "\n" +
                "Effective for: " + getEffectiveRange() + "days" + "\n" +
                recurring + "\n" +
                "Description: " + getDescription() + "\n";
    }
}