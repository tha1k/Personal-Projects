package com.example.familybudget.view.Income.ShowIncomes;

import androidx.lifecycle.ViewModel;

import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.DaoMemory.Income_DAOMemory;

public class IncomesViewModel extends ViewModel {
    private final IncomePresenter presenter;
    public IncomesViewModel(){
        presenter = new IncomePresenter();
        Income_DAO incomeDao = new Income_DAOMemory();
    }
    public IncomePresenter getPresenter() {
        return this.presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
