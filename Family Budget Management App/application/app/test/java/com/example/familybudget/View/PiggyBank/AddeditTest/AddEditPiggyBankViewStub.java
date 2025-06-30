package com.example.familybudget.View.PiggyBank.AddeditTest;

import com.example.familybudget.view.PiggyBank.AddEdit.AddEditPiggyBankView;

public class AddEditPiggyBankViewStub implements AddEditPiggyBankView {
    public boolean isSuccessful_piggy_bank_creation() {
        return successful_piggy_bank_creation;
    }

    public int getMessage_counter() {
        return message_counter;
    }

    public int message_counter=0;
    public boolean successful_piggy_bank_creation=false;
    public int id =-1;
    public String last_msg;
    public void Show_message(String msg){
        message_counter++;
        last_msg=msg;
    }
    public void successfullyFinishActivityEdit(String message, int id){
        this.successful_piggy_bank_creation=true;
        message_counter++;
        this.id=id;
    }

    @Override
    public void SuccessfullyFinishAdd(String message) {
        this.successful_piggy_bank_creation=true;
        message_counter++;
        last_msg=message;
    }
}
