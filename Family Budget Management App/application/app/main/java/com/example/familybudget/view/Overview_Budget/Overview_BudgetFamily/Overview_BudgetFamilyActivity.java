package com.example.familybudget.view.Overview_Budget.Overview_BudgetFamily;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.R;
import com.example.familybudget.view.Family.Login.LoginPresenter;

public class Overview_BudgetFamilyActivity extends AppCompatActivity implements Overview_BudgetFamilyView {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familybudget);

        Overview_BudgetFamilyPresenter presenter = new Overview_BudgetFamilyPresenter(this);
        presenter.onGo(LoginPresenter.FAMILY_ID);
    }


    public void setTxt(String s) {
        TextView txt = findViewById(R.id.txtFamilyBudgetStats);
        txt.setText(s);
    }
    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}