package com.example.familybudget.view.Income.ShowIncomes;
import static com.example.familybudget.R.id;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.familybudget.Income;
import com.example.familybudget.R;
import com.example.familybudget.view.Income.AddEditIncome.AddEditIncomeActivity;
import com.example.familybudget.view.Income.Details.IncomeDetailsActivity;
import java.util.List;
public class IncomeActivity extends AppCompatActivity implements IncomeView, IncomeRVA.ItemSelectionListener {
    public static int ADDED= 9999;
    private IncomeRVA adapter;
    private List<Income> Show;
    private IncomesViewModel viewModel;
    private RecyclerView rcView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes);
        viewModel= new ViewModelProvider(this).get(IncomesViewModel.class);
        viewModel.getPresenter().setView(this);
        viewModel.getPresenter().initIncomes();
        rcView = findViewById(id.item_list_view);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        Show = viewModel.getPresenter().getIncomes();
        if(Show.isEmpty()){return ;}
        adapter = new IncomeRVA(Show, this);

        rcView.setAdapter(adapter);
        findViewById(id.button_add_list_income).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewModel.getPresenter().addIncome();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        rcView = findViewById(id.item_list_view);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        Show = viewModel.getPresenter().getIncomes();
        if (Show.isEmpty()) {
            return;
        }
        adapter = new IncomeRVA(Show, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4004 && resultCode == RESULT_OK) {
            int deletedPosition = data.getIntExtra("list_index", -1);
            if (deletedPosition != -1) {
                if (deletedPosition != -1 && deletedPosition < Show.size()) {
                    Show.remove(deletedPosition);
                    adapter.notifyItemRemoved(deletedPosition);
                } else {
                    showMessage("Invalid position for deletion " + String.valueOf(deletedPosition));
                }
            }
        }else if ((requestCode == ADDED && resultCode == RESULT_OK)){

            if (adapter != null) {
                viewModel.getPresenter().initIncomes();

                Show = viewModel.getPresenter().getIncomes();
                adapter = new IncomeRVA(Show, this);
                rcView.setAdapter(adapter);
            }
        }
    }
    /**
     * Callback when an income is selected from the list.
     *
     * @param income The selected income.
     * @param index  The position of the selected income in the list.
     */
    public void select_Income(Income income, int index) {
        Intent intent = new Intent(this, IncomeDetailsActivity.class);
        intent.putExtra("income_id", income.getId());
        intent.putExtra("list_index", index);
        startActivityForResult(intent,9999);
    }
    /**
     * Redirects to the activity responsible for adding a new income.
     */
    public void redirectToActivity(){
        Intent intent = new Intent(this, AddEditIncomeActivity.class);
        startActivityForResult(intent, ADDED);
    }
    /**
     * Displays a short toast message.
     *
     * @param msg The message to be displayed.
     */
    public void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}