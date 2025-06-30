package com.example.familybudget.View.Family_Manage.Family_Register;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.View.Family_Manage.Family_Edit.Family_EditViewStub;
import com.example.familybudget.view.Family_Manage.Family_Register.Family_RegisterPresenter;
import com.example.familybudget.view.Family_Manage.Family_Register.Family_RegisterView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Family_RegisterPresenterTest {
    Family_RegisterPresenter presenter;
    Family_RegisterViewStub view;
    Initializer dataHelper;
    @Before
    public void setup(){
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
        view = new Family_RegisterViewStub();
        presenter = new Family_RegisterPresenter(view);
    }
    @Test
    public void onbuttontest(){
        presenter.onButtonClick(view.getFamName(), view.getMemName(), view.getMemUsername(), view.getMemPassword());
        Assert.assertEquals(view.msgcounter, 1);
    }

}
