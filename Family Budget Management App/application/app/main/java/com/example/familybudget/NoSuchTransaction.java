package com.example.familybudget;

public class NoSuchTransaction extends RuntimeException{
    public NoSuchTransaction(){
        super ("Could not locate such a transaction.");
    }
}
