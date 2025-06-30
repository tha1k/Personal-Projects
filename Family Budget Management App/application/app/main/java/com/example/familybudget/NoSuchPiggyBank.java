package com.example.familybudget;

public class NoSuchPiggyBank extends RuntimeException{
    public NoSuchPiggyBank(){
        super ("Could not locate that piggy bank.");
    }

}
