package com.example.familybudget;

public class Family_MemberException extends RuntimeException{
    public Family_MemberException(){
        super("This account does not have Protector priveledges");
    }
}
