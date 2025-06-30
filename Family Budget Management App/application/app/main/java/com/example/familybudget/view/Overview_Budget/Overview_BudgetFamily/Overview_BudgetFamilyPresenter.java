package com.example.familybudget.view.Overview_Budget.Overview_BudgetFamily;


import android.content.Intent;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Money;
import com.example.familybudget.view.Family.Login.LoginPresenter;

public class Overview_BudgetFamilyPresenter {
    Overview_BudgetFamilyView view;

    Family fam;
    Family_Member member;

    String printStr;
    Family_DAO dao;

    public Overview_BudgetFamilyPresenter(Overview_BudgetFamilyView view){
        this.view = view;
        this.dao = new Family_DAOMemory();
        fam = dao.find(LoginPresenter.FAMILY_ID);
    }

    public void onGo(int famid) {//TODO: sta kainouria members bgazei null object
        fam = dao.find(famid);
        if (this.fam.getDisposableIncome() == null || this.fam.getTotalIncome() == null || this.fam.getTotalExpenses() == null) {
            this.printStr = "Not enough data to show statistics. Register some income/expense information.";
        }
        else{
            Money disincome = this.fam.getDisposableIncome();
            Money totincome = this.fam.getTotalIncome();
            Money totexpenses = this.fam.getTotalExpenses();
            this.printStr = "The family with id: " + fam.getId() + " and name: " + fam.getFamilyName() + " has disposable income equal to: " + disincome.toString() + ", total income equal to: " + totincome.toString() +
                    " and total expenses equal to: " + totexpenses.toString();

        }
        view.Show_message("Projecting Family Stats");
        view.setTxt(printStr);
    }
}
