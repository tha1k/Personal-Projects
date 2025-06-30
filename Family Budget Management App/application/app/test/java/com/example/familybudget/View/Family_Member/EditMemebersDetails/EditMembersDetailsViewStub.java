package com.example.familybudget.View.Family_Member.EditMemebersDetails;

import com.example.familybudget.view.Family_Member.EditMembersDetails.EditMembersDetailsView;

public class EditMembersDetailsViewStub implements EditMembersDetailsView {
    String str1, str2, str3, str4;
    int msgcounter;
    public EditMembersDetailsViewStub(){
        str1 = str2 = str3 = str4 = "";
        msgcounter = 0;
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

    public void setName(String s){
        str1 = s;
    }
    public void setUsername(String s){
        str2 = s;
    }
    public void setPassword(String s){
        str3 = s;
    }
    public void Show_message(String msg){
        msgcounter++;
        str4 = msg;
    }
}
