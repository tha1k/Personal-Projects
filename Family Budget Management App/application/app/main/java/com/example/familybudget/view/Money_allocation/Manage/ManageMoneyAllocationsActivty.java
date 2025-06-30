package com.example.familybudget.view.Money_allocation.Manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.familybudget.Money_alloc;
import com.example.familybudget.R;
import com.example.familybudget.view.Money_allocation.AddEdit.AddMoneyAllocationActivity;
import com.example.familybudget.view.Money_allocation.AddEdit.AddMoneyAllocationsView;

import java.util.ArrayList;
import java.util.List;

public class ManageMoneyAllocationsActivty extends AppCompatActivity implements ManageAllocationsView {
    /**
    Activity responsible for showing the lists of allocations for a certain piggy bank and also
    provides the ability of filtering withing a specific date-range*/
    private MoneyAllocRecyclerViewAdapter adapter;
    private List<Money_alloc> allocations;
    private ManageMoneyAllocationsViewModel viewModel;
    private RecyclerView rc;
    private static int ALLOCED=12293;
    private static int FILTERED=45445;

    EditText edtDateFrom;
    EditText edtDateTo;
    /** Creates recycler view list of allocations to the selected piggy bank.
     * Checks correct date format of the filter dates
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_allocs);
        viewModel= new ViewModelProvider(this).get(ManageMoneyAllocationsViewModel.class);
        viewModel.getPresenter().setView(this);
            Intent intent= getIntent();
            if(intent!=null && intent.hasExtra("bank_id")){
            int bank_id= intent.getIntExtra("bank_id", -1);
            viewModel.getPresenter().setId(bank_id);}


        RecyclerView recyclerView = findViewById(R.id.money_alloc_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allocations= viewModel.getPresenter().get_allocations_list();
        adapter= new MoneyAllocRecyclerViewAdapter(allocations);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btn_filter_allocs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDateFrom = findViewById(R.id.edt_Date_From_filter);
                edtDateTo = findViewById(R.id.edt_Date_To_filter);
                String startDate = edtDateFrom.getText().toString();
                String endDate = edtDateTo.getText().toString();
                if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)){
                    Show_message("Fill in both boxes");
                }
                boolean formated= viewModel.getPresenter().validate_format(startDate);
                if(formated==false){
                    Show_message("From date incorrect format");
                }
                formated= viewModel.getPresenter().validate_format(endDate);
                if(formated==false){
                    Show_message("To date incorrect format");
                }
                if(formated==true){
                    allocations = viewModel.getPresenter().filter_allocs(startDate, endDate);

                    // Update the existing adapter with the filtered allocations
                    adapter.updateData(allocations);
                }
                else{
                    return ;
                }
            }
        });

        findViewById(R.id.btn_allocate_money).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getPresenter().add_alloc();
            }
        });


    }
    /**When an allocation has been made update the recycler and add that element to the recycler view
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ALLOCED && resultCode==RESULT_OK){
            if(adapter!=null){
                viewModel.getPresenter().get_allocations_list();

                adapter.notifyItemInserted(allocations.size()-1);
            }
        }
    }

    //successfully finished allocation to re_draw the allocated amount
    public void Show_message(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
    /**See Javadoc for
     * {@link com.example.familybudget.view.Money_allocation.AddEdit.AddMoneyAllocationActivity}
     * */
    public void addMoneyAlloc(int id){
        Intent intent = new Intent(this, AddMoneyAllocationActivity.class);
        intent.putExtra("bank_id", id);
        startActivityForResult(intent,ALLOCED);

        finish();

    }


}