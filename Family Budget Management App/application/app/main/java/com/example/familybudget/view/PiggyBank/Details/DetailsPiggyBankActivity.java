package com.example.familybudget.view.PiggyBank.Details;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familybudget.Piggy_bank;
import com.example.familybudget.R;
import com.example.familybudget.view.Money_allocation.Manage.ManageMoneyAllocationsActivty;
import com.example.familybudget.view.PiggyBank.AddEdit.AddEditPiggyBankView;
import com.example.familybudget.view.PiggyBank.AddEdit.AddEditPiggyBanksActivity;
import com.example.familybudget.view.PiggyBank.Manage.ManagePiggyBanksActivity;
/**
 * Shows the attributes of a selected Piggy Bank instance
 */
public class DetailsPiggyBankActivity extends AppCompatActivity implements DetailsPiggyBankView {
    public static int EDIT_BANK= 3000;
    public static int CHANGE_ALLOCATEDAMOUNT=30005;
    private TextView name;
    private TextView goal;
    private TextView alloc_amount;
    private TextView piggy_bank_id;
    private TextView ownername;
    private TextView remaining_amount;
    private TextView description;
    private TextView title;
    private ProgressBar progressBar;
    private DetailsPiggyBankPresenter presenter;
    /**
     * Shows information about the piggy bank but allows user to enter
     * {@link com.example.familybudget.view.Money_allocation.Manage.ManageMoneyAllocationsActivty}
     * or
     * {@link com.example.familybudget.view.PiggyBank.AddEdit.AddEditPiggyBanksActivity}
     * or delete selected bank
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piggybank_details);

        presenter = new DetailsPiggyBankPresenter(this);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("piggy_bank_id") ) {  // Check if the key exists
            presenter.Setid(intent.getIntExtra("piggy_bank_id", -1));
        }
        if(intent != null && intent.hasExtra("list_index")) {
            presenter.setIndex(intent.getIntExtra("list_index", -1));
        }
        title = findViewById(R.id.txt_Details_piggyBankTitle);
        name = findViewById(R.id.text_piggybank_name);
        goal = findViewById(R.id.text_piggybank_goal);
        alloc_amount = findViewById(R.id.text_piggybank_allocAmount);
        piggy_bank_id = findViewById(R.id.text_piggybank_uid);
        ownername = findViewById(R.id.text_piggybank_owner);
        remaining_amount = findViewById(R.id.text_piggybank_remaining);
        description = findViewById(R.id.text_piggybank_description);
        //Show_message(String.valueOf(presenter.getId()));
        title.setText("Piggy bank #" + String.valueOf(presenter.getId()));
       name.setText(presenter.getName());
        goal.setText(presenter.getGoal());
        alloc_amount.setText(presenter.getAllocated());
        piggy_bank_id.setText(String.valueOf(presenter.getId()));
        ownername.setText(presenter.getOwnername());
        remaining_amount.setText(presenter.getRemainder());
        description.setText(presenter.getDescription());
        progressBar = (ProgressBar) findViewById(R.id.progressBar_saving);
        updateProgressBar(presenter.IntegerAllocated(), presenter.IntegerGoal());
        findViewById(R.id.button_piggy_bank_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteBank();
            }
        });
        findViewById(R.id.button_edit_piggybank_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editPiggyBank();
            }
        });
        findViewById(R.id.button_piggybank_allocate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.see_allocations();
            }
        });

    }
    /**updated piggy bank info cause the is a possibility that the info shown has been altered either
     * by an allocation or an Edit
     * */
    @Override
    protected void onResume(){
        super.onResume();
        title = findViewById(R.id.txt_Details_piggyBankTitle);
        name = findViewById(R.id.text_piggybank_name);
        goal = findViewById(R.id.text_piggybank_goal);
        alloc_amount = findViewById(R.id.text_piggybank_allocAmount);
        piggy_bank_id = findViewById(R.id.text_piggybank_uid);
        ownername = findViewById(R.id.text_piggybank_owner);
        remaining_amount = findViewById(R.id.text_piggybank_remaining);
        description = findViewById(R.id.text_piggybank_description);
        //Show_message(String.valueOf(presenter.getId()));
        title.setText("Piggy bank #" + String.valueOf(presenter.getId()));
        name.setText(presenter.getName());
        goal.setText(presenter.getGoal());
        alloc_amount.setText(presenter.getAllocated());
        piggy_bank_id.setText(String.valueOf(presenter.getId()));
        ownername.setText(presenter.getOwnername());
        remaining_amount.setText(presenter.getRemainder());
        description.setText(presenter.getDescription());
        progressBar = (ProgressBar) findViewById(R.id.progressBar_saving);
        updateProgressBar(presenter.IntegerAllocated(), presenter.IntegerGoal());

    }
    /**
     * EDIT_BANK
     * When a bank is edited you have to change the effected fields
     * CHANGE_ALLOCATEDAMOUNT
     * When an allocation is made to the specific piggy bank the remaining , allocated and progress bar shoulb be affected
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_BANK && resultCode == RESULT_OK) {
            int updated_id= data.getIntExtra("piggy_bank_id",-1);
            if(updated_id!=-1){
                title = findViewById(R.id.txt_Details_piggyBankTitle);
                name = findViewById(R.id.text_piggybank_name);
                goal = findViewById(R.id.text_piggybank_goal);
                alloc_amount = findViewById(R.id.text_piggybank_allocAmount);
                piggy_bank_id = findViewById(R.id.text_piggybank_uid);
                ownername = findViewById(R.id.text_piggybank_owner);
                remaining_amount = findViewById(R.id.text_piggybank_remaining);
                description = findViewById(R.id.text_piggybank_description);
                //Show_message(String.valueOf(presenter.getId()));
                title.setText("Piggy bank #" + String.valueOf(presenter.getId()));
                name.setText(presenter.getName());
                goal.setText(presenter.getGoal());
                alloc_amount.setText(presenter.getAllocated());
                piggy_bank_id.setText(String.valueOf(presenter.getId()));
                ownername.setText(presenter.getOwnername());
                remaining_amount.setText(presenter.getRemainder());
                description.setText(presenter.getDescription());
                progressBar = (ProgressBar) findViewById(R.id.progressBar_saving);
                updateProgressBar(presenter.IntegerAllocated(), presenter.IntegerGoal());
            }

        }else if(requestCode==CHANGE_ALLOCATEDAMOUNT && resultCode==RESULT_OK){
            int updated_id= data.getIntExtra("piggy_bank_id",-1);
            if(updated_id!=-1){
            alloc_amount = findViewById(R.id.text_piggybank_allocAmount);
            alloc_amount.setText(presenter.getAllocated());
            progressBar = (ProgressBar) findViewById(R.id.progressBar_saving);
            updateProgressBar(presenter.IntegerAllocated(), presenter.IntegerGoal());}


        }
    }

/**
 * Animates the progress bar to visually show how much of the goal has been covered so far
 * */
    private void updateProgressBar(int savedAmount, int goalAmount) {
        // Calculate the percentage of completion
        int progress = (int) (((float) savedAmount / goalAmount) * 100);

        // Create an ObjectAnimator to animate the progress change
        ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), progress).setDuration(5000).start();
    }
    public void Show_message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * When done the user is redirected to the ManagePiggyBanks screen where the item he delete is removed
     * that mean the recycler view has to be notified of where that item previously wass
     * @param index position of removed element
     * */
    public void removeBank(int index ){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("list_index", index);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    /**
     * calls edit bank activity
     * */
    public void editBank(int id){
        Intent editIntent= new Intent(this, AddEditPiggyBanksActivity.class);
        editIntent.putExtra("bank_id", id);
        startActivityForResult(editIntent, EDIT_BANK);
        finish();

    }
    /**
     * show allocations made to the selected bank referenced by its unique id
     * @param id
     */

    public void see_allocs(int id ){
        Intent allocations_intent= new Intent(this, ManageMoneyAllocationsActivty.class);
        allocations_intent.putExtra("bank_id", id );
        startActivity(allocations_intent);
        startActivityForResult(allocations_intent, CHANGE_ALLOCATEDAMOUNT);
    }
}