package com.example.familybudget.view.Expense.AddEditExpense;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.familybudget.R;
import com.example.familybudget.view.Expense.Details.ExpenseDetailsActivity;
import com.example.familybudget.view.Expense.ShowExpenses.ExpenseActivity;

import java.time.LocalDate;
/**
 * Activity for adding or editing expenses.
 */

public class AddEditExpenseActivity extends AppCompatActivity implements AddEditExpenseView {
    private EditText startDateDayEdit, startDateMonthEdit, startDateYearEdit,
            endDateDayEdit, endDateMonthEdit, endDateYearEdit, amountEdit, descriptionEdit;
    private RadioButton recurringButton, urgentButton;
    boolean isRecurring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        final AddEditExpensePresenter presenter = new AddEditExpensePresenter(this);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("expense_id")){
            presenter.setAttachedId(intent.getIntExtra("expense_id", -1));
        }
        findViewById(R.id.expense_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateDayEdit = findViewById(R.id.edit_exp_start_date_day);
                startDateMonthEdit = findViewById(R.id.edit_exp_start_date_month);
                startDateYearEdit = findViewById(R.id.edit_exp_start_date_year);
                endDateDayEdit = findViewById(R.id.edit_exp_end_date_day);
                endDateMonthEdit = findViewById(R.id.edit_exp_end_date_month);
                endDateYearEdit = findViewById(R.id.edit_exp_end_date_year);
                amountEdit = findViewById(R.id.edit_exp_amount);
                descriptionEdit = findViewById(R.id.edit_exp_description);
                recurringButton = findViewById(R.id.button_exp_recurring);
                urgentButton = findViewById(R.id.button_exp_urgent);

                if (recurringButton.isChecked()) {
                    isRecurring = true;
                } else if (urgentButton.isChecked()) {
                    isRecurring = false;
                } else if (!recurringButton.isChecked() && !urgentButton.isChecked()) {
                    showMessage("Select frequency");
                    return;
                }
                LocalDate regDate = null;
                LocalDate endDate = null;
                String amountS = amountEdit.getText().toString();
                try {
                    regDate = LocalDate.of(Integer.parseInt(startDateYearEdit.getText().toString()),
                            Integer.parseInt(startDateMonthEdit.getText().toString()),
                            Integer.parseInt(startDateDayEdit.getText().toString()));
                    endDate = LocalDate.of(Integer.parseInt(endDateYearEdit.getText().toString()),
                            Integer.parseInt(endDateMonthEdit.getText().toString()),
                            Integer.parseInt(endDateDayEdit.getText().toString()));

                } catch (NumberFormatException e) {
                    showMessage("Fill dates.");
                    return;
                }
                if(amountS.isEmpty()){
                    showMessage("Fill amount.");
                    return;
                }
                String description = descriptionEdit.getText().toString();
                if(description.isEmpty()){
                    showMessage("Fill description.");
                    return;
                }
                double amount = Double.parseDouble(amountS);
                presenter.saveExpense(amount, isRecurring, regDate, endDate, description);
            }
        });
    }
    /**
     * Display a short toast message.
     *
     * @param msg The message to be displayed.
     */
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * Handle the success of editing an expense.
     *
     * @param msg The success message.
     * @param id  The ID of the edited expense.
     */
    public void successEdit(String msg, int id){
        Intent retData = new Intent(this, ExpenseDetailsActivity.class);
        retData.putExtra("expense_id", id);
        setResult(RESULT_OK);
        startActivity(retData);
        finish();
    }
    /**
     * Handle the success of adding a new expense.
     *
     * @param msg The success message.
     */
    public void successAdd(String msg){
        Intent retData = new Intent(this, ExpenseActivity.class);
        setResult(RESULT_OK);
        finish();
    }
}
