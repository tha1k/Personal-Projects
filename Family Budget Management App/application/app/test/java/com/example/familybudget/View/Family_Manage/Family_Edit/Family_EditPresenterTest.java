package com.example.familybudget.View.Family_Manage.Family_Edit;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.view.Family.Login.LoginPresenter;
import com.example.familybudget.view.Family_Manage.Family_Edit.Family_EditPresenter;
import com.example.familybudget.view.Family_Manage.Family_Edit.Family_EditView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Family_EditPresenterTest {
    Family_EditPresenter presenter;
    Family_EditViewStub view;
    Initializer dataHelper;
    Family family;
    Family_Member member;
    Family_DAOMemory famdao = new Family_DAOMemory();
    Family_Member_DAOMemory memdao = new Family_Member_DAOMemory();
    @Before
    public void setup(){
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
        view = new Family_EditViewStub();
        member = new Family_Member("t","t","t",true);
        memdao.save(member);

        family = new Family("blues", member);
        famdao.save(family);
        presenter = new Family_EditPresenter(view, famdao);
    }
    @Test
    public void onRenameTest(){

        LoginPresenter.FAMILYMEMBER_ID = member.getID();
        LoginPresenter.FAMILY_ID = family.getId();
        presenter.onRename("blacks");
        Assert.assertEquals("blacks", family.getFamilyName());
    }
    @Test
    public void onDeleteTest(){
        LoginPresenter.FAMILY_ID = family.getId();
        presenter.onDelete(LoginPresenter.FAMILY_ID);
        Family fam = famdao.find(LoginPresenter.FAMILY_ID);
        Assert.assertNull(fam);
    }
}
