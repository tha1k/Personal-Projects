package com.example.familybudget.view.Overview_Budget.Overview_BudgetSelf;

import android.content.Intent;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Money;
import com.example.familybudget.view.Family.Login.LoginPresenter;

import java.math.BigDecimal;

public class Overview_BudgetSelfPresenter {
    Overview_BudgetSelfView view;
    String printString;
    Family_Member member;
    Family_Member_DAO dao;
    public Overview_BudgetSelfPresenter(Overview_BudgetSelfView view){
        this.view = view;
        dao = new Family_Member_DAOMemory();
        member = dao.find(LoginPresenter.FAMILYMEMBER_ID);
    }

    public void onGo(int memid){
        member = dao.find(memid);
        Money disposableInc = member.getDisposableIncome();
        Money totalInc = member.getTotalIncome();
        Money totalExp = member.getTotalExpenses();
        int pososto;
        if(totalInc.getAmount().intValue() != 0){
            pososto = (totalExp.getAmount().intValue() * 100) / totalInc.getAmount().intValue();
        }
        else{
            pososto = 0;
        }
        printString = "The member with id: " + String.valueOf(member.getID()) + " and name: " + member.getName() + " has disposable income: "
                + disposableInc.toString() + ", exei total income iso me: " + totalInc.toString() +
                " and total income equal to: " + totalExp.toString() + ". The percentage of the income that " +
                "is expenses is equal to: " + String.valueOf(pososto) + "%";
        view.setTxt(printString);
        view.Show_message("Projecting Self Stats");
    }
}
