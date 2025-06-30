package com.example.familybudget.View.Family_Member.EditMemebersDetails;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family_Member;
import com.example.familybudget.View.Family_Manage.Family_Register.Family_RegisterViewStub;
import com.example.familybudget.view.Family.Login.LoginPresenter;
import com.example.familybudget.view.Family_Member.EditMembersDetails.EditMembersDetailsPresenter;
import com.example.familybudget.view.Family_Member.EditMembersDetails.EditMembersDetailsView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditMembersDetailsPresenterTest {
    EditMembersDetailsPresenter presenter;
    EditMembersDetailsViewStub view;
    Initializer dataHelper;
    Family_Member member;
    Family_Member_DAO dao = new Family_Member_DAOMemory();
    @Before
    public void setup(){
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
        view = new EditMembersDetailsViewStub();
        presenter = new EditMembersDetailsPresenter(view);
        member = new Family_Member("john","john1","1234",true);
        dao.save(member);
    }
    @Test
    public void onupdate(){
        LoginPresenter.FAMILYMEMBER_ID = member.getID();
        presenter.onUpdateMember(view.str1, view.str2, view.str3);
        Assert.assertNotEquals(member.getName(), "john");
        Assert.assertNotEquals(member.getUsername(), "john1");
        Assert.assertNotEquals(member.getPassword(), "1234");
    }
}
