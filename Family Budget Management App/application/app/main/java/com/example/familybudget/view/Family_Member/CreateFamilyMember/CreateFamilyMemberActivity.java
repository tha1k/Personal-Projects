package com.example.familybudget.view.Family_Member.CreateFamilyMember;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.R;
import com.example.familybudget.view.Family.Login.LoginPresenter;


public class CreateFamilyMemberActivity extends AppCompatActivity implements CreateFamilyMemberView {

    EditText editTxtName;
    EditText editTxtUsername;
    EditText editTxtPassword;
    CheckBox protectorCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regmember);


        editTxtName = findViewById(R.id.regmember_editname);

        editTxtUsername = findViewById(R.id.regmember_editusername);

        editTxtPassword = findViewById(R.id.regmember_editpassword);

        protectorCheckBox = findViewById(R.id.protectorCheckBox);

        final CreateFamilyMemberPresenter presenter = new CreateFamilyMemberPresenter(this);

        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSaveMember();

            }
        });
    }

    public String getName(){
        return editTxtName.getText().toString().trim();
    }

    public String getUsername(){
        return editTxtUsername.getText().toString().trim();
    }

    public String getPassword(){
        return editTxtPassword.getText().toString().trim();
    }

    public boolean getIsProtector(){
        return protectorCheckBox.isChecked();
    }

    public void setName(String s){
        editTxtName.setText(s);
    }

    public void setUsername(String s){
        editTxtUsername.setText(s);
    }

    public void setPassword(String s){
        editTxtPassword.setText(s);
    }

    public void setIsProtector(boolean b){
        protectorCheckBox.setChecked(b);
    }


    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
