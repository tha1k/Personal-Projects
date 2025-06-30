package com.example.familybudget.View.Overview_Budget.Overview_BudgetSelf;

import com.example.familybudget.view.Overview_Budget.Overview_BudgetSelf.Overview_BudgetSelfView;

public class Overview_BudgetSelfViewStub implements Overview_BudgetSelfView {
    String str1, str2;
    int msgcounter;
    public Overview_BudgetSelfViewStub(){
        str1 = str2 = "";
        msgcounter = 0;
    }
    public void setTxt(String s){
        str1 = s;
    }
    public void Show_message(String msg){
        msgcounter++;
        str2 = msg;
    }
}
