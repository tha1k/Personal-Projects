package com.example.familybudget.view.Family_Manage.Family_Edit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.R;
import com.example.familybudget.view.Family.Login.LoginActivity;
import com.example.familybudget.view.Family.Login.LoginPresenter;

public class Family_EditActivity extends AppCompatActivity implements Family_EditView{
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfamily);

        Family_EditPresenter presenter = new Family_EditPresenter(this, new Family_DAOMemory());
        findViewById(R.id.btn_renamefam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String famname = getFamilyName();
                presenter.onRename(famname);

            }
        });
        findViewById(R.id.btn_deletefam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = LoginPresenter.FAMILY_ID;
                presenter.onDelete(id);

            }
        });
    }

    public void setFamilyName(String s){
        ((EditText)findViewById(R.id.txtFamilyName)).setText(s);
    }
    public String getFamilyName(){
        return ((EditText)findViewById(R.id.txtFamilyName)).getText().toString().trim();
    }

    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void logout(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
