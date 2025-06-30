package com.example.familybudget.view.Family_Member.EditMembersDetails;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.R;
import com.example.familybudget.view.Family.Login.LoginPresenter;

public class EditMembersDetailsActivity extends AppCompatActivity implements EditMembersDetailsView {

    public String getName(){
        return ((EditText)findViewById(R.id.editmember_editname)).getText().toString().trim();
    }

    public String getUsername(){
        return ((EditText)findViewById(R.id.editmember_editusername)).getText().toString().trim();
    }

    public String getPassword(){
        return ((EditText)findViewById(R.id.editmember_editpassword)).getText().toString().trim();
    }

    public void setName(String s){
        ((EditText)findViewById(R.id.editmember_editname)).setText(s);
    }

    public void setUsername(String s){
        ((EditText)findViewById(R.id.editmember_editusername)).setText(s);
    }

    public void setPassword(String s){
        ((EditText)findViewById(R.id.editmember_editpassword)).setText(s);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmember);

        final EditMembersDetailsPresenter presenter = new EditMembersDetailsPresenter(this);

        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onUpdateMember(getName(), getUsername(), getPassword());

            }
        });
    }
    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
