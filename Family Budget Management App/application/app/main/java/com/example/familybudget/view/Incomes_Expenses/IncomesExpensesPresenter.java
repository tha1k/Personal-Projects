package com.example.familybudget.view.Incomes_Expenses;

public class IncomesExpensesPresenter {
    private IncomesExpensesView view;
    public IncomesExpensesPresenter(IncomesExpensesView view){
        this.view = view;
    }
    void on_edit_income(){
        view.edit_income();
    }
    void on_edit_expense(){
        view.edit_expense();
    }
}
