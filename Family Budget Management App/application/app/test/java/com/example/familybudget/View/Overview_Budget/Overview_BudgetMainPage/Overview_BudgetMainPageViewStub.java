package com.example.familybudget.View.Overview_Budget.Overview_BudgetMainPage;

import com.example.familybudget.view.Overview_Budget.Overview_BudgetMainPage.Overview_BudgetMainPageView;

public class Overview_BudgetMainPageViewStub implements Overview_BudgetMainPageView {
    String str;
    int msgcounter;
    public Overview_BudgetMainPageViewStub(){
        str = "";
        msgcounter = 0;
    }
    public void startSelf(){

    }
    public void startFamily(){

    }
    public void Show_message(String msg){
        msgcounter++;
        str = msg;
    }
}
