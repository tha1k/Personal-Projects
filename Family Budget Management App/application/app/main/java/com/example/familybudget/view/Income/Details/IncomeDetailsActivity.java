package com.example.familybudget.view.Income.Details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.R;
import com.example.familybudget.view.Income.AddEditIncome.AddEditIncomeActivity;

public class IncomeDetailsActivity extends AppCompatActivity implements IncomeDetailsView {
    public static int EDIT_INCOME = 9999;
    private IncomeDetailsPresenter presenter;
    private TextView description, regDate, endDate, owner, amount, isRecurring, effective;
    /**
     * Called when the activity is starting. Initializes the UI, presenter, and handles incoming intent data.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise, it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_details);
        presenter = new IncomeDetailsPresenter(this);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("income_id")){
            presenter.setId(intent.getIntExtra("income_id", -1));
        }
        if(intent != null && intent.hasExtra("income_id")){
            presenter.setIndex(intent.getIntExtra("list_index", -1));
        }
        description = findViewById(R.id.details_inc_description_fill);
        regDate = findViewById(R.id.details_inc_regDate_fill);
        endDate  = findViewById(R.id.details_inc_endDate_fill);
        owner = findViewById(R.id.details_inc_owner_fill);
        amount = findViewById(R.id.details_inc_amount_fill);
        isRecurring = findViewById(R.id.details_inc_frequency_fill);
        effective = findViewById(R.id.details_inc_effective_fill);
        findViewById(R.id.inc_details_button_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editIncome();
            }
        });
        findViewById(R.id.inc_details_button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteIncome();
            }
        });
    }
    /**
     * Called when the activity will start interacting with the user. Updates the displayed income details
     * when the activity resumes.
     */
    protected void  onResume(){
        super.onResume();
        description = findViewById(R.id.details_inc_description_fill);
        regDate = findViewById(R.id.details_inc_regDate_fill);
        endDate  = findViewById(R.id.details_inc_endDate_fill);
        owner = findViewById(R.id.details_inc_owner_fill);
        amount = findViewById(R.id.details_inc_amount_fill);
        isRecurring = findViewById(R.id.details_inc_frequency_fill);
        effective = findViewById(R.id.details_inc_effective_fill);
        description.setText(presenter.getDescription());
        regDate.setText(presenter.getRegDate());
        endDate.setText(presenter.getEndDate());
        owner.setText(presenter.getOwner());
        amount.setText(presenter.getAmount());
        isRecurring.setText(presenter.getFrequency());
        effective.setText(presenter.getDateRange());
    }
    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it. Updates income details after editing.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_INCOME && resultCode == RESULT_OK){
            description = findViewById(R.id.details_inc_description_fill);
            regDate = findViewById(R.id.details_inc_regDate_fill);
            endDate  = findViewById(R.id.details_inc_endDate_fill);
            owner = findViewById(R.id.details_inc_owner_fill);
            amount = findViewById(R.id.details_inc_amount_fill);
            description.setText(presenter.getDescription());
            regDate.setText(presenter.getRegDate());
            endDate.setText(presenter.getEndDate());
            owner.setText(presenter.getOwner());
            amount.setText(presenter.getAmount());
        }
    }
    /**
     * Displays a short-duration message (toast) to the user.
     *
     * @param msg The message to be displayed.
     */
    public void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * Removes the income entry from the list and finishes the activity.
     *
     * @param index The index of the income entry to be removed.
     */
    public void removeIncome(int index){
        Intent intent = new Intent();
        intent.putExtra("list_index", index);
        setResult(RESULT_OK, intent);
        finish();
    }
    /**
     * Initiates the editing of the income entry.
     *
     * @param id The ID of the income entry to be edited.
     */
    public void editIncome(int id){
        Intent intent = new Intent(this, AddEditIncomeActivity.class);
        intent.putExtra("income_id", id);
        startActivityForResult(intent, EDIT_INCOME);
        finish();
    }
}