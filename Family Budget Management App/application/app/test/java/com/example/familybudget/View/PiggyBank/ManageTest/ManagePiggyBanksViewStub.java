package com.example.familybudget.View.PiggyBank.ManageTest;

import com.example.familybudget.view.PiggyBank.Manage.ManagePiggyBanksView;

public class ManagePiggyBanksViewStub implements ManagePiggyBanksView {
    public int message_counter = 0;
    public boolean redirection = false;
    public String last_message= "";

    public int getMessage_counter() {
        return message_counter;
    }

    public void redirect_to_AddeditActivity(){
        redirection=true;
    }

    public String getlast_message() {
        return last_message;
    }

    public void Show_message(String msg){
        message_counter= message_counter+1;
        last_message=msg;
    }

}
