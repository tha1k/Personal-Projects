package com.example.familybudget.View.MoneyAlloc.AddTest;

import com.example.familybudget.view.Money_allocation.AddEdit.AddMoneyAllocationsView;

public class AddMoneyAllocViewStub implements AddMoneyAllocationsView {
    public String last=" ";
    public int counter=0;
    public int bankid=0;
    public void Show_message(String msg){
        last=msg;
        counter++;
    }
    public void suceessful_allocation(int id ){
        bankid=id;
    }

}
