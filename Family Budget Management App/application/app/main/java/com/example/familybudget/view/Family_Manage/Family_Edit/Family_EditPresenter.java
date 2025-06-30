package com.example.familybudget.view.Family_Manage.Family_Edit;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.view.Family.Login.LoginPresenter;

public class Family_EditPresenter {
    Family_EditView view;
    Family_DAO dao;

    public Family_EditPresenter(Family_EditView view, Family_DAOMemory d){
        this.dao = d;
        this.view = view;
    }

    public void onRename(String famname){
        int id = LoginPresenter.FAMILY_ID;
        Family fam = dao.find(id);
        fam.setFamilyName(famname);
        view.Show_message("Family renamed to: " + famname);
    }

    public void onDelete(int famID){
        Family fam = dao.find(famID);
        String fam_name = fam.getFamilyName();
        dao.delete(fam);
        view.Show_message("Family" + fam_name + " Deleted");
        view.logout();
    }
}
