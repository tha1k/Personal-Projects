package com.example.familybudget.View.Income.ManageTest;

import com.example.familybudget.view.Income.ShowIncomes.IncomeView;

;

public class ManageStub implements IncomeView {
    public int counter;
    public boolean redirect = false;
    public String last = "";
    public void redirectToActivity(){
        redirect = true;
    }
    public String getLast(){
        return last;
    }
    public void showMessage(String msg){
        counter ++;
        last = msg;
    }

}
