package com.example.familybudget.View.Overview_Budget.Overview_BudgetMainPage;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.View.Overview_Budget.Overview_BudgetFamily.Overview_BudgetFamilyViewStub;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetMainPage.Overview_BudgetMainPagePresenter;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetMainPage.Overview_BudgetMainPageView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Overview_BudgetMainPagePresenterTest {
    Overview_BudgetMainPageViewStub view;
    Overview_BudgetMainPagePresenter presenter;
    Initializer dataHelper;
    @Before
    public void setup(){
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
        view = new Overview_BudgetMainPageViewStub();
        presenter = new Overview_BudgetMainPagePresenter(view);
    }
    @Test
    public void selftest(){
        Assert.assertEquals(view.msgcounter, 0);
        presenter.onstartSelf();
        Assert.assertEquals(view.msgcounter, 1);
    }
    @Test
    public void familytest(){
        Assert.assertEquals(view.msgcounter, 0);
        presenter.onstartFamily();
        Assert.assertEquals(view.msgcounter, 1);
    }
}
