package com.example.familybudget.view.Family_Member.FamilyMemberOptions;

public class FamilyMemberOptionsPresenter {
    private FamilyMemberOptionsView view;

    public void onStartEditMember(){
        view.startEdit();
        view.Show_message("Edit member's details");
    }

    public void onStartCreateMember(){
        view.startCreate();
        view.Show_message("Create a member");
    }

    public FamilyMemberOptionsPresenter(FamilyMemberOptionsView view){
        this.view = view;
    }
}
