package com.example.familybudget.view.Family_Manage.Family_Register;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.R;

public class Family_RegisterActivity extends AppCompatActivity implements Family_RegisterView{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regfamily);


        Family_RegisterPresenter presenter = new Family_RegisterPresenter(this);
        findViewById(R.id.btnCreateFam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonClick(getFamName(),getMemName(),getMemUsername(),getMemPassword());

            }
        });
    }


    public String getFamName(){
        return ((EditText)findViewById(R.id.edtFamilyName)).getText().toString().trim();
    }
    public String getMemName(){
        return ((EditText)findViewById(R.id.edtProtName)).getText().toString().trim();
    }
    public String getMemUsername(){
        return ((EditText)findViewById(R.id.edtProtUsername)).getText().toString().trim();
    }
    public String getMemPassword(){
        return ((EditText)findViewById(R.id.edtPassword)).getText().toString().trim();
    }
    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
