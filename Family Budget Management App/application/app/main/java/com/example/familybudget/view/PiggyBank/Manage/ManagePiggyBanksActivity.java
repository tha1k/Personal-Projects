package com.example.familybudget.view.PiggyBank.Manage;

import static com.example.familybudget.R.*;
import static com.example.familybudget.R.id.button_add_list_bank;
import static com.example.familybudget.R.id.login_noaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.familybudget.Piggy_bank;
import com.example.familybudget.R;
import com.example.familybudget.view.PiggyBank.AddEdit.AddEditPiggyBanksActivity;
import com.example.familybudget.view.PiggyBank.Details.DetailsPiggyBankActivity;

import java.util.ArrayList;
import java.util.List;

public class ManagePiggyBanksActivity extends AppCompatActivity implements ManagePiggyBanksView, PiggyBankRecyclerViewAdapter.ItemSelectionListener {
    public static int ADDED= 10001;
    private PiggyBankRecyclerViewAdapter adapter;
    private List<Piggy_bank> Show;
    private ManagePiggybanksViewModel viewModel;
    private RecyclerView rcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_recycler_view);
        viewModel= new ViewModelProvider(this).get(ManagePiggybanksViewModel.class);
        viewModel.getPresenter().setView(this);

        viewModel.getPresenter().initBanks();

        //initialise recycler adapter set its list as the presenter list

         rcView = findViewById(id.listofpiggybanks);
       rcView.setLayoutManager(new LinearLayoutManager(this));
        Show= viewModel.getPresenter().getBanks();
        if(Show.isEmpty()==true){return ;}
        adapter = new PiggyBankRecyclerViewAdapter(Show, this);

        rcView.setAdapter(adapter);
        findViewById(R.id.button_add_list_bank).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewModel.getPresenter().AddBank();

            }
        });




    }
    @Override
    protected void onResume(){
        super.onResume();
        rcView = findViewById(id.listofpiggybanks);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        Show= viewModel.getPresenter().getBanks();
        if(Show.isEmpty()==true){return ;}
        adapter = new PiggyBankRecyclerViewAdapter(Show, this);
    }

/**Either remove an element
 * @param resultCode = 4004
 *                   or
 * @param requestCode = ADDED re-initialise the adapter list as an item has been added
 * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4004 && resultCode == RESULT_OK) {
            int deletedPosition = data.getIntExtra("list_index", -1);
            if (deletedPosition != -1) {
                if (deletedPosition != -1 && deletedPosition < Show.size()) {
                    // Remove the item from your data list and update the RecyclerView
                    Show.remove(deletedPosition);
                    adapter.notifyItemRemoved(deletedPosition);
                } else {
                    // Log or show an error message, as the deletedPosition is out of bounds
                    //add piggy bank
                    Show_message("Invalid position for deletion    "+   String.valueOf(deletedPosition));
                }
            }
        }else if ((requestCode == ADDED && resultCode == RESULT_OK)){

            if (adapter != null) {
                viewModel.getPresenter().initBanks();

                Show = viewModel.getPresenter().getBanks();
                adapter = new PiggyBankRecyclerViewAdapter(Show, this);
                rcView.setAdapter(adapter);
            }
        }
    }
    /**
     * When an item is clicked show details about selected element
     * @param SelectedPiggyBank instance of Piggy_bank class
     * and that items position in the list
     * @param index
     * */
    @Override
    public void select_PiggyBank(Piggy_bank SelectedPiggyBank, int index) {
       Intent intent = new Intent(this, DetailsPiggyBankActivity.class);
        intent.putExtra("piggy_bank_id", SelectedPiggyBank.getId());
        intent.putExtra("list_index", index);
        //initialize text view with piggy bank name in the details activity
        startActivityForResult(intent,4004 );
    }
    /**
     * AddEdit activity is called to add a new Piggy bank to the signed in user*/
    public void redirect_to_AddeditActivity(){
        Intent intent = new Intent(this, AddEditPiggyBanksActivity.class);
        startActivityForResult(intent, ADDED);
    }
    public void Show_message(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}