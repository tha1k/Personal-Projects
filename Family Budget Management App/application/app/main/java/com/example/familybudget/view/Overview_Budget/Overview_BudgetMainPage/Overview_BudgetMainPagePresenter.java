package com.example.familybudget.view.Overview_Budget.Overview_BudgetMainPage;

public class Overview_BudgetMainPagePresenter {

    private Overview_BudgetMainPageView view;

    public Overview_BudgetMainPagePresenter(Overview_BudgetMainPageView view){
        this.view = view;
    }
    public void onstartSelf(){
        view.startSelf();
        view.Show_message("Projecting self stats");
    }
    public void onstartFamily(){
        view.startFamily();
        view.Show_message("Projecting family stats");
    }
}
