package com.example.familybudget;

public class NotProtector extends RuntimeException{
    public NotProtector(){
        super ("Selected member is not a protector.");
    }
}
