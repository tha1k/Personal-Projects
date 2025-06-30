package com.example.familybudget.view.Overview_Budget.Overview_BudgetSelf;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.R;
import com.example.familybudget.view.Family.Login.LoginPresenter;

public class Overview_BudgetSelfActivity extends AppCompatActivity implements Overview_BudgetSelfView{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfbudget);

        Overview_BudgetSelfPresenter presenter = new Overview_BudgetSelfPresenter(this);

        presenter.onGo(LoginPresenter.FAMILYMEMBER_ID);
    }

    public void setTxt(String s){
        TextView txt = findViewById(R.id.txtSelfBudgetStats);
        txt.setText(s);
    }
    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
