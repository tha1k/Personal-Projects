package com.example.familybudget.view.Money_allocation.AddEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.familybudget.R;
import com.example.familybudget.view.PiggyBank.Details.DetailsPiggyBankActivity;

public class AddMoneyAllocationActivity extends AppCompatActivity implements AddMoneyAllocationsView{
/**On creation find be the Intent sent by the caller activity the id of the Piggy bank
 * which the allocation is referring to
 * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_allocation);
        AddMoneyAllocationPresenter presenter = new AddMoneyAllocationPresenter(this);
        if(savedInstanceState==null){
            Intent i = getIntent();


            if(i!= null && i.hasExtra("bank_id")){
                int bank_id = i.getIntExtra("bank_id", -1);
                presenter.setId(bank_id);

            }
        }
        findViewById(R.id.button_savenewAllocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt_amount = findViewById(R.id.edt_amount_new_alloc);
                Switch Negate = findViewById(R.id.switch_Negate_alloc);
                int amount = Integer.parseInt(edt_amount.getText().toString());
                boolean switchState = Negate.isChecked();
                presenter.onSave(amount, switchState);


            }
        });
    }

    public void Show_message(String msg ){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
/**Successful allocations destroy the screen returning the bank which the allocation was added to
 * */
    @Override
    public void suceessful_allocation(int id) {
        Intent i = new Intent(this, DetailsPiggyBankActivity.class);
        i.putExtra("piggy_bank_id", id);
        setResult(RESULT_OK);
        finish();


    }


}