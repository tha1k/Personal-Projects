package com.example.familybudget.View.PiggyBank.DetailsTest;


import com.example.familybudget.view.PiggyBank.Details.DetailsPiggyBankView;

public class DetailsPiggyBankViewStub implements DetailsPiggyBankView {
    public int count=0;
    public String last="";
    public int id;
    public boolean redirected= false;
    public void Show_message(String msg ){
        count++;
        last= msg;
    }
    public void removeBank(int index ){
        //id= index;
        redirected=true;
    }
    public void editBank(int id){this.id=id;
    redirected=true;}
    public void see_allocs(int id ){
        redirected=true;
    }

}
