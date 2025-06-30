package com.example.familybudget;

public class NotAMemberOfThisFamily extends RuntimeException{
    public NotAMemberOfThisFamily(){
        super ("Selected member is not a part of this family.");
    }
}
