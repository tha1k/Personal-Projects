package com.example.familybudget.View.Family_Member.FamilyMemberOptions;

import com.example.familybudget.view.Family_Member.FamilyMemberOptions.FamilyMemberOptionsView;

public class FamilyMemberOptionsViewStub implements FamilyMemberOptionsView {
    String str;
    int msgcounter;
    public FamilyMemberOptionsViewStub(){
        str = "";
        msgcounter = 0;
    }
    public void startEdit(){

    }
    public void startCreate(){

    }
    public void Show_message(String msg){
        msgcounter++;
        str = msg;
    }
}
