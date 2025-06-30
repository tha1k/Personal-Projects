package com.example.familybudget.view.Family_Manage.Family_MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.R;
import com.example.familybudget.view.Family_Manage.Family_Edit.Family_EditActivity;
import com.example.familybudget.view.Family_Manage.Family_Register.Family_RegisterActivity;

public class Family_MainPageActivity extends AppCompatActivity implements Family_MainPageView {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familymain);

        Family_MainPagePresenter presenter = new Family_MainPagePresenter(this);
        findViewById(R.id.btn_createfamily).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ongoCreate();
            }
        });

        findViewById(R.id.btn_editfamily).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ongoEdit();
            }
        });
    }
    public void onstartCreate(){
        Intent i = new Intent(this, Family_RegisterActivity.class);
        startActivity(i);
    }

    public void onstartEdit(){
        Intent i = new Intent(this, Family_EditActivity.class);

        startActivity(i);
    }
    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
