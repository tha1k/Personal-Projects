package com.example.familybudget;

public class NotInstanceOfFamilyMember extends RuntimeException{
    public NotInstanceOfFamilyMember(){
        super ("Selected object is not a part of the Family_Member class.");
    }
}
