package com.example.familybudget.View.Expense.ManageTest;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.view.Expense.ShowExpenses.ExpensePresenter;
import com.example.familybudget.view.Expense.ShowExpenses.ExpenseView;
import com.example.familybudget.view.Family.Login.LoginPresenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ManagePresenterTest {
    private ExpensePresenter presenter;
    private ManageStub view;
    @Before
    public void setup(){
        Initializer initializer = new Memory_Initializer();
        initializer.prepareDemoData();
        presenter = new ExpensePresenter();
        view = new ManageStub();
        presenter.setView(view);
    }
    /**
     * Tests the initialization of expenses for a guardian account.
     */
    @Test
    public void  guardian(){
        presenter.initExpenses();
        Assert.assertEquals(2, presenter.getExpenses().size());
        Assert.assertEquals("Guardian account", view.getLast());
        Assert.assertEquals(1, view.counter);
    }
    /**
     * Tests the initialization of expenses for a family member account.
     */
    @Test
    public void member(){
        LoginPresenter.FAMILYMEMBER_ID=2;
        presenter.initExpenses();
        Assert.assertEquals(0, presenter.getExpenses().size());
        Assert.assertEquals("Ward account", view.getLast());
        Assert.assertEquals(1, view.counter);
    }
    /**
     * Tests the redirection to add an expense.
     */
    @Test
    public void redirect(){
        presenter.addExpense();
        Assert.assertTrue(view.redirect);
    }
    @After
    public void cleanup(){
        LoginPresenter.FAMILYMEMBER_ID=1;
    }
}
