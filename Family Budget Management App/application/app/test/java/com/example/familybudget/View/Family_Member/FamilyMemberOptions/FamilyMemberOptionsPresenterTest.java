package com.example.familybudget.View.Family_Member.FamilyMemberOptions;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.View.Family_Manage.Family_Register.Family_RegisterViewStub;
import com.example.familybudget.view.Family_Member.FamilyMemberOptions.FamilyMemberOptionsPresenter;
import com.example.familybudget.view.Family_Member.FamilyMemberOptions.FamilyMemberOptionsView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FamilyMemberOptionsPresenterTest {
    FamilyMemberOptionsPresenter presenter;
    FamilyMemberOptionsViewStub view;
    Initializer dataHelper;
    @Before
    public void setup(){
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
        view = new FamilyMemberOptionsViewStub();
        presenter = new FamilyMemberOptionsPresenter(view);
    }
    @Test
    public void test1(){
        Assert.assertEquals(view.msgcounter, 0);
        presenter.onStartEditMember();
        Assert.assertEquals(view.msgcounter, 1);
    }
    @Test
    public void test2(){
        Assert.assertEquals(view.msgcounter, 0);
        presenter.onStartCreateMember();
        Assert.assertEquals(view.msgcounter, 1);
    }
}
