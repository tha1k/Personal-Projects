package com.example.familybudget.View.Family_Manage.Family_MainPage;

import com.example.familybudget.view.Family_Manage.Family_MainPage.Family_MainPageView;

public class Family_MainPageViewStub implements Family_MainPageView {
    String msg;
    int msgcounter;
    public Family_MainPageViewStub(){
        msgcounter = 0;
        msg = "";
    }
    public void Show_message(String msg){
        msgcounter++;
        this.msg = msg;
    }
    public void onstartEdit(){

    }
    public void onstartCreate(){

    }
}
