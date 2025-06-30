package com.example.familybudget;

import java.time.LocalDate;

public class Expense extends Transactions {
    public Expense(Money money, boolean isRecurring, LocalDate registrationDate, LocalDate endDate, String description) {
        super(money, isRecurring, registrationDate, endDate, description);
    }
    public Expense(){}
    public String toString(){
        String recurring;
        if(getIsRecurring()){
            recurring = "The expense is recurring";
        }else{
            recurring = "The expense is not recurring";
        }
        return "Transaction - Expense: \n" +
                "Money: " + getMoney() + "\n" +
                "Registration date: " + getRegistrationDate() + "\n" +
                "End date: " + getEndDate() + "\n" +
                "Effective for: " + getEffectiveRange() + "days" + "\n" +
                recurring + "\n" +
                "Description: " + getDescription() + "\n";
    }
}
