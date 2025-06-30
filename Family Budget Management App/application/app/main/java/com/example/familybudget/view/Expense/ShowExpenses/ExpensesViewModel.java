package com.example.familybudget.view.Expense.ShowExpenses;
import androidx.lifecycle.ViewModel;
import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.DaoMemory.Expense_DAOMemory;

public class ExpensesViewModel extends ViewModel{
    private final ExpensePresenter presenter;
    public ExpensesViewModel(){
        presenter = new ExpensePresenter();
        Expense_DAO expenseDao = new Expense_DAOMemory();
    }
    public ExpensePresenter getPresenter(){
        return this.presenter;
    }
    protected void onCleared(){
        super.onCleared();
    }
}
