package com.example.familybudget.view.Family_Member.FamilyMemberOptions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.R;
import com.example.familybudget.view.Family_Member.CreateFamilyMember.CreateFamilyMemberActivity;
import com.example.familybudget.view.Family_Member.EditMembersDetails.EditMembersDetailsActivity;


public class FamilyMemberOptionsActivity extends AppCompatActivity implements FamilyMemberOptionsView {

    FamilyMemberOptionsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberoptions);

        presenter = new FamilyMemberOptionsPresenter(this);

        findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStartCreateMember();
            }
        });

        findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStartEditMember();
            }
        });
    }

    public void startEdit(){
        Intent i = new Intent(this, EditMembersDetailsActivity.class);
        startActivity(i);
    }

    public void startCreate(){
        Intent i = new Intent(this, CreateFamilyMemberActivity.class);
        startActivity(i);
    }
    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
