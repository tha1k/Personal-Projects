package com.example.familybudget.view.Family_Member.EditMembersDetails;

public interface EditMembersDetailsView {
    String getName();
    String getUsername();
    String getPassword();

    void setName(String s);
    void setUsername(String s);
    void setPassword(String s);
    void Show_message(String msg);
}
