package com.example.familybudget.view.Family_Member.EditMembersDetails;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.view.Family.Login.LoginPresenter;


public class EditMembersDetailsPresenter {

    private EditMembersDetailsView view;
    private Family_Member_DAO members;

    public EditMembersDetailsPresenter(EditMembersDetailsView view){
        this.view = view;
        this.members = new Family_Member_DAOMemory();

    }
    public void onUpdateMember(String name, String username, String password){
        int id = LoginPresenter.FAMILYMEMBER_ID;
        Family_Member fm = members.find(id);
        fm.setName(name);
        fm.setUsername(username);
        fm.setPassword(password);
        view.Show_message("Changes Saved");
    }
}
