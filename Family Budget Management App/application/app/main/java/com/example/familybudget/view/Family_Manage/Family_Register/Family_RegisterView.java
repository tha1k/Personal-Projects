package com.example.familybudget.view.Family_Manage.Family_Register;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;

public interface Family_RegisterView {
    void Show_message(String msg);
    String getFamName();
    String getMemName();
    String getMemUsername();
    String getMemPassword();
}
