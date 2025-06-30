package com.example.familybudget.View.Family_Manage.Family_Register;

import com.example.familybudget.view.Family_Manage.Family_Register.Family_RegisterView;

public class Family_RegisterViewStub implements Family_RegisterView {
    String famname, memname, memusername, mempass, messageStr;
    int msgcounter;
    public Family_RegisterViewStub(){
        famname = memname = memusername = mempass = messageStr = "";
        msgcounter = 0;
    }
    public void Show_message(String msg){
        msgcounter++;
        messageStr = msg;
    }
    public String getFamName(){
        return famname;
    }
    public String getMemName(){
        return memname;
    }
    public String getMemUsername(){
        return memusername;
    }
    public String getMemPassword(){
        return mempass;
    }
}
