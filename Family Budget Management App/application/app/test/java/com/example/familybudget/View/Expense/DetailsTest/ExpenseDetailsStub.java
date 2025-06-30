package com.example.familybudget.View.Expense.DetailsTest;

import com.example.familybudget.view.Expense.Details.ExpenseDetailsView;

public class ExpenseDetailsStub  implements ExpenseDetailsView {
    public int count = 0;
    public String last = "";
    public int id = 0;
    public boolean redirected = false;
    public void showMessage(String message){
        count ++;
        last = message;
    }
    public void removeExpense(int index){
        redirected = true;
    }
    public void editExpense(int index){
        redirected = true;
    }
}
