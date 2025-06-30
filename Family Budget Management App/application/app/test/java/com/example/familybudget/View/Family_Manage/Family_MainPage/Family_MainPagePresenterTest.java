package com.example.familybudget.View.Family_Manage.Family_MainPage;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.View.Family_Manage.Family_Edit.Family_EditViewStub;
import com.example.familybudget.view.Family_Manage.Family_MainPage.Family_MainPagePresenter;
import com.example.familybudget.view.Family_Manage.Family_MainPage.Family_MainPageView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Family_MainPagePresenterTest {
    Family_MainPagePresenter presenter;
    Family_MainPageViewStub view;
    Initializer dataHelper;
    @Before
    public void setup(){
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
        view = new Family_MainPageViewStub();
        presenter = new Family_MainPagePresenter(view);
    }
    @Test
    public void ongocreate(){
        Assert.assertEquals(view.msgcounter, 0);
        presenter.ongoCreate();
        Assert.assertEquals(view.msgcounter, 1);
    }
    @Test
    public void ongoedit(){
        Assert.assertEquals(view.msgcounter, 0);
        presenter.ongoEdit();
        Assert.assertEquals(view.msgcounter, 1);
    }
}
