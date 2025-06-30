package com.example.familybudget.view.Family_Manage.Family_MainPage;

public class Family_MainPagePresenter {
    Family_MainPageView view;

    public Family_MainPagePresenter(Family_MainPageView v){
        this.view = v;
    }
    public void ongoCreate(){
        view.Show_message("Create a family");
        view.onstartCreate();
    }
    public void ongoEdit(){
        view.Show_message("Edit family's details");
        view.onstartEdit();
    }
}
