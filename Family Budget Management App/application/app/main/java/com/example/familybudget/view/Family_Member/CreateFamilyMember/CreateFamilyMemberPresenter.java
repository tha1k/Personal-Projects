package com.example.familybudget.view.Family_Member.CreateFamilyMember;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.view.Family.Login.LoginPresenter;

public class CreateFamilyMemberPresenter {
    private CreateFamilyMemberView view;
    private Family_Member_DAO members;
    private Family_Member member;
    private Family_DAO dao2;

    public CreateFamilyMemberPresenter(CreateFamilyMemberView view){
        this.view = view;
        this.members = new Family_Member_DAOMemory();
        this.dao2 = new Family_DAOMemory();
    }
    public void onSaveMember(){
        String name = view.getName();
        String username = view.getUsername();
        String password = view.getPassword();
        boolean isProtector = view.getIsProtector();
        int signed_member_id = LoginPresenter.FAMILYMEMBER_ID;
        member = members.find(signed_member_id);
        if(member.Is_protector()){
            Family_Member newMember = new Family_Member(name, username, password, isProtector );
            members.save(newMember);
            Family fam = dao2.find(LoginPresenter.FAMILY_ID);
            fam.addFamilyMember(newMember);
        }
        view.Show_message("Member Created");

    }
}
