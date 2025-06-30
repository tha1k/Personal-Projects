package com.example.familybudget.View.Income.DetailsTest;
import com.example.familybudget.view.Income.Details.IncomeDetailsView;

public class IncomeDetailsStub  implements IncomeDetailsView {
    public int count = 0;
    public String last = "";
    public int id = 0;
    public boolean redirected = false;
    public void showMessage(String message){
        count ++;
        last = message;
    }
    public void removeIncome(int index){
        redirected = true;
    }
    public void editIncome(int index){
        redirected = true;
    }
}
