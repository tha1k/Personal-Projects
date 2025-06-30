package com.example.familybudget.view.Overview_Budget.Overview_BudgetMainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.R;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetFamily.Overview_BudgetFamilyActivity;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetSelf.Overview_BudgetSelfActivity;

public class Overview_BudgetMainPageActivity extends AppCompatActivity implements Overview_BudgetMainPageView {

    Overview_BudgetMainPagePresenter presenter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overviewbudgetmainpage);

        presenter = new Overview_BudgetMainPagePresenter(this);
        findViewById(R.id.btnSelfBudget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onstartSelf();
            }
        });

        findViewById(R.id.btnFamilyBudget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onstartFamily();
            }
        });
    }


    public void startSelf(){
        Intent i = new Intent(this, Overview_BudgetSelfActivity.class);
        startActivity(i);
    }
    public void startFamily(){
        Intent i = new Intent(this, Overview_BudgetFamilyActivity.class);
        startActivity(i);
    }
    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
