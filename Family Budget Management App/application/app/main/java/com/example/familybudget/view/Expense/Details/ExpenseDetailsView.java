package com.example.familybudget.view.Expense.Details;

public interface ExpenseDetailsView {
    void showMessage(String msg);
    void removeExpense(int index);
    void editExpense(int id);
}
