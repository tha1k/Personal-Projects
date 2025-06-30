package com.example.familybudget.view.Family_Member.CreateFamilyMember;

import com.example.familybudget.Family_Member;

public interface CreateFamilyMemberView {
    String getName();
    String getUsername();
    String getPassword();
    boolean getIsProtector();


    void Show_message(String msg);
    void setName(String s);
    void setUsername(String s);
    void setPassword(String s);
    void setIsProtector(boolean b);

}
