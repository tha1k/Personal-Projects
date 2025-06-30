package com.example.familybudget.view.Expense.Details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.R;
import com.example.familybudget.view.Expense.AddEditExpense.AddEditExpenseActivity;

/**
 * Activity for displaying details of a specific expense.
 */
public class ExpenseDetailsActivity extends AppCompatActivity implements ExpenseDetailsView {
    public static int EDIT_EXPENSE = 7777;
    private ExpenseDetailsPresenter presenter;
    private TextView description, regDate, endDate, owner, amount, isRecurring, effective;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);
        presenter = new ExpenseDetailsPresenter(this);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("expense_id")){
            presenter.setId(intent.getIntExtra("expense_id", -1));
        }
        if(intent != null && intent.hasExtra("expense_id")){
            presenter.setIndex(intent.getIntExtra("list_index", -1));
        }
        description = findViewById(R.id.details_exp_description_fill);
        regDate = findViewById(R.id.details_exp_regDate_fill);
        endDate  = findViewById(R.id.details_exp_endDate_fill);
        owner = findViewById(R.id.details_exp_owner_fill);
        amount = findViewById(R.id.details_exp_amount_fill);
        isRecurring = findViewById(R.id.details_exp_frequency_fill);
        effective = findViewById(R.id.details_exp_effective_fill);
        findViewById(R.id.exp_details_button_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editExpense();
            }
        });
        findViewById(R.id.exp_details_button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteExpense();
            }
        });
    }
    protected void  onResume(){
        super.onResume();
        description = findViewById(R.id.details_exp_description_fill);
        regDate = findViewById(R.id.details_exp_regDate_fill);
        endDate  = findViewById(R.id.details_exp_endDate_fill);
        owner = findViewById(R.id.details_exp_owner_fill);
        amount = findViewById(R.id.details_exp_amount_fill);
        isRecurring = findViewById(R.id.details_exp_frequency_fill);
        effective = findViewById(R.id.details_exp_effective_fill);
        description.setText(presenter.getDescription());
        regDate.setText(presenter.getRegDate());
        endDate.setText(presenter.getEndDate());
        owner.setText(presenter.getOwner());
        amount.setText(presenter.getAmount());
        isRecurring.setText(presenter.getFrequency());
        effective.setText(presenter.getDateRange());
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_EXPENSE && resultCode == RESULT_OK){
            description = findViewById(R.id.details_exp_description_fill);
            regDate = findViewById(R.id.details_exp_regDate_fill);
            endDate  = findViewById(R.id.details_exp_endDate_fill);
            owner = findViewById(R.id.details_exp_owner_fill);
            amount = findViewById(R.id.details_exp_amount_fill);
            description.setText(presenter.getDescription());
            regDate.setText(presenter.getRegDate());
            endDate.setText(presenter.getEndDate());
            owner.setText(presenter.getOwner());
            amount.setText(presenter.getAmount());
        }
    }
    /**
     * Display a short toast message.
     *
     * @param msg The message to be displayed.
     */
    public void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * Remove the expense at the specified index.
     *
     * @param index The index of the expense to be removed.
     */
    public void removeExpense(int index){
        Intent intent = new Intent();
        intent.putExtra("list_index", index);
        setResult(RESULT_OK, intent);
        finish();
    }
    /**
     * Edit the expense with the specified ID.
     *
     * @param id The ID of the expense to be edited.
     */
    public void editExpense(int id){
        Intent intent = new Intent(this, AddEditExpenseActivity.class);
        intent.putExtra("expense_id", id);
        startActivityForResult(intent, EDIT_EXPENSE);
        finish();
    }
}