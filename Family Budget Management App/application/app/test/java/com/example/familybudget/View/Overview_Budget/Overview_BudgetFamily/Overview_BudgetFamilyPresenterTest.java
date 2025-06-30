package com.example.familybudget.View.Overview_Budget.Overview_BudgetFamily;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.View.Family_Member.CreateFamilyMember.CreateFamilyMemberViewStub;
import com.example.familybudget.view.Family.Login.LoginPresenter;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetFamily.Overview_BudgetFamilyPresenter;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetFamily.Overview_BudgetFamilyView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Overview_BudgetFamilyPresenterTest {
    Overview_BudgetFamilyPresenter presenter;
    Overview_BudgetFamilyViewStub view;
    Initializer dataHelper;
    Family_Member lebron;
    Family_Member_DAO dao = new Family_Member_DAOMemory();
    Family_DAO dao2 = new Family_DAOMemory();
    Family family;
    @Before
    public void setup(){
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
        view = new Overview_BudgetFamilyViewStub();
        presenter = new Overview_BudgetFamilyPresenter(view);
        lebron = new Family_Member("lebron", "lebron1", "1234", true);
        dao.save(lebron);
        family = new Family("Lebrons", lebron);
        dao2.save(family);
    }
    @Test
    public void ongotest(){
        LoginPresenter.FAMILY_ID = family.getId();
        Assert.assertEquals(view.msgcounter, 0);
        presenter.onGo(LoginPresenter.FAMILY_ID);
        Assert.assertEquals(view.msgcounter, 1);
    }
}
