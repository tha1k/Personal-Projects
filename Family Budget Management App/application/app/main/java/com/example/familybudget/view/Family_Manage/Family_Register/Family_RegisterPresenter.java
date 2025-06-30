package com.example.familybudget.view.Family_Manage.Family_Register;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;

public class Family_RegisterPresenter{
    Family_RegisterView view;
    Family_DAO famdao;
    Family_Member_DAO memdao;

    public Family_RegisterPresenter(Family_RegisterView v){
        this.view = v;
        this.famdao = new Family_DAOMemory();
        this.memdao = new Family_Member_DAOMemory();
    }
    public void onButtonClick(String famname, String memname, String memusername, String mempass){
        Family_Member member = new Family_Member(memname, memusername, mempass, true);
        Family family = new Family(famname, member);
        memdao.save(member);
        famdao.save(family);
        view.Show_message("Protector and Family created");
    }
}
