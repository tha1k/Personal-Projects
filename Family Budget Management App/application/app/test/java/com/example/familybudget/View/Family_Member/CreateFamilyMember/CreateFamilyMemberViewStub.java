package com.example.familybudget.View.Family_Member.CreateFamilyMember;

import com.example.familybudget.view.Family_Member.CreateFamilyMember.CreateFamilyMemberView;

public class CreateFamilyMemberViewStub implements CreateFamilyMemberView {
    String str1, str2, str3, str4;
    int msgcounter;
    boolean b;
    public CreateFamilyMemberViewStub(){
        str1 = str2 = str3 = str4 = "";
        msgcounter = 0;
        b = true;
    }
    public String getName(){
        return str1;
    }
    public String getUsername(){
        return str2;
    }
    public String getPassword(){
        return str3;
    }
    public boolean getIsProtector(){
        return b;
    }


    public void Show_message(String msg){
        msgcounter++;
        str4 = msg;
    }
    public void setName(String s){
        str1 = s;
    }
    public void setUsername(String s){
        str2 = s;
    }
    public void setPassword(String s){
        str3 = s;
    }
    public void setIsProtector(boolean b){
        this.b = b;
    }
}
