package com.example.familybudget.View.Income.AddEditTest;

import com.example.familybudget.view.Income.AddEditIncome.AddEditIncomeView;

public class IncomeViewStub implements AddEditIncomeView {
    public int message_counter = 0;
    public int id = -1;
    public String last_msg;
    public boolean success_income_add = false;
    public boolean success_income_edit = false;
    public boolean getSuccessAdd(){
        return success_income_add;
    }
    public void successEdit(String msg, int id){
        this.success_income_edit = true;
        message_counter ++;
        this.id=id;
    }
    public void successAdd(String msg){
        this.success_income_add= true;
        message_counter ++;
        last_msg = msg;
    }
    public int returnCounter(){
        return message_counter;
    }
    public void showMessage(String msg){
        message_counter++;
        last_msg = msg;
    }
}
