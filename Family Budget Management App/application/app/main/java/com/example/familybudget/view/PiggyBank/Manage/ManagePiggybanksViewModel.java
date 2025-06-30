package com.example.familybudget.view.PiggyBank.Manage;

import androidx.lifecycle.ViewModel;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Family_Member;

public class ManagePiggybanksViewModel extends ViewModel {
    private ManagePiggyBanksPresenter presenter;

    public ManagePiggybanksViewModel() {
        presenter = new ManagePiggyBanksPresenter();
        Family_Member_DAO fmd = new Family_Member_DAOMemory();
    }

    public ManagePiggyBanksPresenter getPresenter() {
        return this.presenter;
    }

    @Override
    protected void onCleared() {
         super.onCleared();
    }
}
