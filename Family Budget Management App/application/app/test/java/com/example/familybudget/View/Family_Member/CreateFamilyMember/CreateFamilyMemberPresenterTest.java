package com.example.familybudget.View.Family_Member.CreateFamilyMember;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.View.Family_Manage.Family_Register.Family_RegisterViewStub;
import com.example.familybudget.view.Family.Login.LoginPresenter;
import com.example.familybudget.view.Family_Member.CreateFamilyMember.CreateFamilyMemberPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateFamilyMemberPresenterTest {
    CreateFamilyMemberPresenter presenter;
    CreateFamilyMemberViewStub view;
    Initializer dataHelper;
    Family_Member member1, member2;
    Family_Member_DAO dao = new Family_Member_DAOMemory();
    Family_DAO dao2 = new Family_DAOMemory();
    Family family;
    @Before
    public void setup(){
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
        view = new CreateFamilyMemberViewStub();
        presenter = new CreateFamilyMemberPresenter(view);
        member1 = new Family_Member("a", "a", "a", true);
        dao.save(member1);
        family = new Family("afs", member1);
        dao2.save(family);
        member2 = new Family_Member("aa", "aa", "aa", false);
        dao.save(member2);
    }
    @Test
    public void test1(){
        LoginPresenter.FAMILYMEMBER_ID = member1.getID();
        LoginPresenter.FAMILY_ID = family.getId();
        presenter.onSaveMember();
        Assert.assertEquals(view.msgcounter, 1);
    }
}
