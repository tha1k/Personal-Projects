package com.example.familybudget.view.Family.Login;

import static com.example.familybudget.R.id.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.familybudget.R;
import com.example.familybudget.view.Family.Homepage.HomepageActivity;
import com.example.familybudget.view.Family.Registration.RegisterFamilyActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

 /**
  * creates the screen allowing the user to input credentials or select to register his family
  * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final LoginPresenter presenter= new LoginPresenter(this);

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText edtfam_name = findViewById(R.id.login_editFamilyName);
                EditText edtusername = findViewById(R.id.login_editusername);
                EditText edtpass = findViewById(R.id.login_editpassword);
                String fam_name = edtfam_name.getText().toString();
                String user_name = edtusername.getText().toString();
                String password = edtpass.getText().toString();
                if (TextUtils.isEmpty(fam_name) || TextUtils.isEmpty(user_name) || TextUtils.isEmpty(password)) {
                    Show_Err("Please fill all the required fields");
                    return;
                }
                presenter.submitAuthenticationInfo(fam_name, user_name,password);
            }
        });

        findViewById(login_noaccount).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                presenter.RegisterFamily();
            }
        });
    }
    /**
     * Is activated only when a user has been authenticated successfully

     */
    public void submit_AuthenticationInfo(){
        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
        //intent.addExtra(usersname )
        startActivity(intent);

    }
    /**Is used when a user clicks the register family button and redirects him to a form
     * where he can register it and him/her self as the protector of that family.
     * */
    public void Redirect_to_registration_page(){
        Intent intent = new Intent(LoginActivity.this, RegisterFamilyActivity.class);
        startActivity(intent);
    }
    /**Displays the message given as the arguments as a pop up
     * @param msg the message that will be shown
     * */
    public void Show_Err(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}