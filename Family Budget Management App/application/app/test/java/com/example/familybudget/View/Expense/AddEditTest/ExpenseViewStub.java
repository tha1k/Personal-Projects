package com.example.familybudget.View.Expense.AddEditTest;

import com.example.familybudget.view.Expense.AddEditExpense.AddEditExpenseView;

public class ExpenseViewStub implements AddEditExpenseView {
    public int message_counter = 0;
    public int id = -1;
    public String last_msg;
    public boolean success_expense_add = false;
    public boolean success_expense_edit = false;
    public boolean getSuccessAdd(){
        return success_expense_add;
    }
    public void successEdit(String msg, int id){
        this.success_expense_edit = true;
        message_counter ++;
        this.id=id;
    }
    public void successAdd(String msg){
        this.success_expense_add = true;
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
