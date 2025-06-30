package com.example.familybudget.view.Incomes_Expenses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.R;
import com.example.familybudget.view.Expense.ShowExpenses.ExpenseActivity;
import com.example.familybudget.view.Income.ShowIncomes.IncomeActivity;

public class IncomesExpensesActivity extends AppCompatActivity implements IncomesExpensesView {
    Initializer data = new Memory_Initializer();
    private static boolean initialized = false;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inc_exp);
        final IncomesExpensesPresenter presenter = new IncomesExpensesPresenter(this);
        findViewById(R.id.button_editincome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.on_edit_income();
            }
        });
        findViewById(R.id.button_editexpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.on_edit_expense();
            }
        });
        if(!initialized){
            data.prepareDemoData();
            initialized = true;
        }
    }
    public void edit_income(){
       Intent intent = new Intent(IncomesExpensesActivity.this, IncomeActivity.class);
       startActivity(intent);
    }
    public void edit_expense(){
        Intent intent = new Intent(IncomesExpensesActivity.this, ExpenseActivity.class);
        startActivity(intent);
    }
}