package com.example.familybudget.View.Income.ManageTest;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.view.Family.Login.LoginPresenter;
import com.example.familybudget.view.Income.ShowIncomes.IncomePresenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ManagePresenterTest {
    private IncomePresenter presenter;
    private ManageStub view;
    @Before
    public void setup(){
        Initializer initializer = new Memory_Initializer();
        initializer.prepareDemoData();
        presenter = new IncomePresenter();
        view = new ManageStub();
        presenter.setView(view);
        LoginPresenter.FAMILY_ID=1;
        LoginPresenter.FAMILYMEMBER_ID =1 ;

    }
    /**
     * Tests the initialization of incomes for a guardian account.
     */
    @Test
    public void  guardian(){
        presenter.initIncomes();
        Assert.assertEquals(3, presenter.getIncomes().size());
        Assert.assertEquals("Guardian account", view.getLast());
        Assert.assertEquals(1, view.counter);
    }
    /**
     * Tests the initialization of incomes for a family member account.
     */
    @Test
    public void member(){
        LoginPresenter.FAMILYMEMBER_ID=2;
        presenter.initIncomes();
        Assert.assertEquals(1, presenter.getIncomes().size());
        Assert.assertEquals("Ward account", view.getLast());
        Assert.assertEquals(1, view.counter);
    }
    /**
     * Tests the redirection to add an income.
     */
    @Test
    public void redirect(){
        presenter.addIncome();
        Assert.assertTrue(view.redirect);
    }
    @After
    public void cleanup(){
        LoginPresenter.FAMILYMEMBER_ID=1;
    }
}
