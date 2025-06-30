package com.example.familybudget.view.Income.AddEditIncome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.Income;
import com.example.familybudget.Money;
import com.example.familybudget.R;
import com.example.familybudget.view.Income.Details.IncomeDetailsActivity;
import com.example.familybudget.view.Income.ShowIncomes.IncomeActivity;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * Activity for adding or editing an income entry.
 */
public class AddEditIncomeActivity extends AppCompatActivity implements AddEditIncomeView {
    private EditText startDateDayEdit, startDateMonthEdit, startDateYearEdit,
            endDateDayEdit, endDateMonthEdit, endDateYearEdit, amountEdit, descriptionEdit;
    private RadioButton recurringButton, urgentButton;
    boolean isRecurring;
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this Bundle contains
     *                           the data it most recently supplied in
     *                           onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);
        final AddEditIncomePresenter presenter = new AddEditIncomePresenter(this);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("income_id")){
            presenter.setAttachedId(intent.getIntExtra("income_id", -1));
        }
        findViewById(R.id.edit_income_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateDayEdit = findViewById(R.id.edit_inc_start_day);
                startDateMonthEdit = findViewById(R.id.edit_inc_start_month);
                startDateYearEdit = findViewById(R.id.edit_inc_start_year);
                endDateDayEdit = findViewById(R.id.edit_inc_end_day);
                endDateMonthEdit = findViewById(R.id.edit_inc_end_month);
                endDateYearEdit = findViewById(R.id.edit_inc_end_year);
                amountEdit = findViewById(R.id.edit_inc_edit_amount);
                descriptionEdit = findViewById(R.id.edit_inc_edit_description);
                recurringButton = findViewById(R.id.button_edit_inc_recurring);
                urgentButton = findViewById(R.id.button_edit_inc_urgent);

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
                }catch (NumberFormatException e) {
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
                double amount = Double.parseDouble(amountEdit.getText().toString());
                presenter.saveIncome(amount, isRecurring, regDate, endDate, description);
            }
        });
    }
    /**
     * Display a toast message.
     *
     * @param msg The message to be displayed.
     */
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * Navigate to the details activity upon successful edit.
     *
     * @param msg The success message.
     * @param id  The income ID.
     */
    public void successEdit(String msg, int id){
        Intent retData = new Intent(this, IncomeDetailsActivity.class);
        retData.putExtra("income_id", id);
        setResult(RESULT_OK);
        startActivity(retData);
        finish();
    }
    /**
     * Navigate to the income activity upon successful addition.
     *
     * @param msg The success message.
     */
    public void successAdd(String msg){
        Intent retData = new Intent(this, IncomeActivity.class);
        setResult(RESULT_OK);
        finish();
    }
}
