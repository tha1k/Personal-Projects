package com.example.familybudget.View.MoneyAlloc.Manage;


import com.example.familybudget.view.Money_allocation.Manage.ManageAllocationsView;

public class ManageMoneyAllocViewStub implements ManageAllocationsView {
    public String last;
    public int bank= -100;
    public void Show_message(String msg){
        last= msg;
    }
    public void addMoneyAlloc(int id){
        bank= id;
    }
}
