package com.example.familybudget.View.Overview_Budget.Overview_BudgetFamily;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetFamily.Overview_BudgetFamilyView;

public class Overview_BudgetFamilyViewStub implements Overview_BudgetFamilyView {

    String str1, str2;
    int msgcounter;

    public Overview_BudgetFamilyViewStub(){
        str1 = str2 = "";
        msgcounter = 0;

    }
    /*public Family getMembersFamily(){
        return family;
    }*/
    public void setTxt(String s){
        str1 = s;
    }
    public void Show_message(String msg){
        msgcounter++;
        str2 = msg;
    }
}
