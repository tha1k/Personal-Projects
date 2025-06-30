package com.example.familybudget.view.PiggyBank.AddEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.familybudget.R;
import com.example.familybudget.view.PiggyBank.Details.DetailsPiggyBankActivity;
import com.example.familybudget.view.PiggyBank.Manage.ManagePiggyBanksActivity;

public class AddEditPiggyBanksActivity extends AppCompatActivity implements AddEditPiggyBankView{
/**adds or edits banks specified by the caller Activity's Intent
 * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_piggy_bank);
        final AddEditPiggyBankPresenter presenter= new AddEditPiggyBankPresenter(this);
        Intent intent = getIntent();
        if(intent!= null && intent.hasExtra("bank_id")){
            presenter.setAttachedId(intent.getIntExtra("bank_id", -1));
        }
        findViewById(R.id.button_savenewAllocation).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                EditText editTextPiggyTitle= findViewById(R.id.editText_piggyBankName);
                EditText editTextPiggyDesc= findViewById(R.id.editText_PiggyBankDesc);
                EditText editTextPiggyAmount= findViewById(R.id.edt_amount_new_alloc);

                String title = editTextPiggyTitle.getText().toString();
                String Desc= editTextPiggyDesc.getText().toString();
                String goal= editTextPiggyAmount.getText().toString();
                if(TextUtils.isEmpty(Desc)){Desc="";}
                if (TextUtils.isEmpty(title) ||  TextUtils.isEmpty(goal)) {
                    Show_message("Please fill the required fields");
                    return;
                }
                int initial_goal= Integer.parseInt(goal);
                presenter.SavePiggyBank(initial_goal, title,Desc);

            }
        });

    }

    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**When Editing is finished redirect to the Details activity showing the updated information
     * */
    public void successfullyFinishActivityEdit(String message, int id)
    {
        //Show_message(message);
        Intent retData = new Intent(this, DetailsPiggyBankActivity.class);
        retData.putExtra("piggy_bank_id", id);
        setResult(RESULT_OK);

        startActivity(retData);
        finish();
    }
    /**After adding a bank the user is redirected to the list of Piggy banks
     * {@link com.example.familybudget.view.PiggyBank.Manage.ManagePiggyBanksActivity}
     * */
    public void SuccessfullyFinishAdd(String message){
        Intent retData = new Intent(this, ManagePiggyBanksActivity.class);

        setResult(RESULT_OK);

        finish();
    }

}