package com.example.familybudget.View.Expense.ManageTest;

import com.example.familybudget.view.Expense.ShowExpenses.ExpenseView;

public class ManageStub implements ExpenseView {
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
