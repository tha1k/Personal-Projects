package com.example.familybudget;

public class Piggy_bankException extends RuntimeException{
    public Piggy_bankException(){super("Can't revert a allocation bigger than any previous one try a smaller amount,Or amounts have to be greater than 0");}

}
