package com.example.familybudget.View.Family_Manage.Family_Edit;

import com.example.familybudget.view.Family_Manage.Family_Edit.Family_EditView;

public class Family_EditViewStub implements Family_EditView {

    String famName, messageStr;
    int mid, fid;
    public Family_EditViewStub(){
        famName = messageStr = "";
        mid = fid = 0;
    }
    public String getFamilyName(){
        return famName;
    }
    public void Show_message(String str){
        messageStr = str;
    }
    public void setFamilyName(String s){
        famName = s;
    }
    public void logout(){}
}
