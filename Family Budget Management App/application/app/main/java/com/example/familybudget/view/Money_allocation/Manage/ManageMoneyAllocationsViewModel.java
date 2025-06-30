package com.example.familybudget.view.Money_allocation.Manage;

import androidx.lifecycle.ViewModel;

import com.example.familybudget.view.PiggyBank.Manage.ManagePiggyBanksPresenter;

public class ManageMoneyAllocationsViewModel extends ViewModel {
    private ManageMoneyAllocationsPresenter presenter;
    public ManageMoneyAllocationsViewModel(){
        presenter= new ManageMoneyAllocationsPresenter();
    }
    public ManageMoneyAllocationsPresenter getPresenter() {
        return this.presenter;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
