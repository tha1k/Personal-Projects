package com.example.familybudget.View.Overview_Budget.Overview_BudgetSelf;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family_Member;
import com.example.familybudget.View.Overview_Budget.Overview_BudgetMainPage.Overview_BudgetMainPageViewStub;
import com.example.familybudget.view.Family.Login.LoginPresenter;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetSelf.Overview_BudgetSelfPresenter;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetSelf.Overview_BudgetSelfView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Overview_BudgetSelfPresenterTest {
    Overview_BudgetSelfPresenter presenter;
    Overview_BudgetSelfViewStub view;
    Initializer dataHelper;
    Family_Member memmber;
    Family_Member_DAO dao = new Family_Member_DAOMemory();
    @Before
    public void setup(){
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
        view = new Overview_BudgetSelfViewStub();
        presenter = new Overview_BudgetSelfPresenter(view);
        memmber = new Family_Member("","","",true);
        dao.save(memmber);
    }
    @Test
    public void test(){
        LoginPresenter.FAMILYMEMBER_ID = memmber.getID();
        Assert.assertEquals(view.msgcounter, 0);
        presenter.onGo(LoginPresenter.FAMILYMEMBER_ID);
        Assert.assertEquals(view.msgcounter, 1);
    }
}
