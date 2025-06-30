package com.example.familybudget.view.Expense.ShowExpenses;
import static com.example.familybudget.R.id;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.familybudget.Expense;
import com.example.familybudget.R;
import com.example.familybudget.view.Expense.AddEditExpense.AddEditExpenseActivity;
import com.example.familybudget.view.Expense.Details.ExpenseDetailsActivity;
import java.util.List;
/**
 * Activity for displaying a list of expenses.
 */
public class ExpenseActivity extends AppCompatActivity implements ExpenseView, ExpenseRVA.ItemSelectionListener{
    public static int ADDED= 7777;
    private ExpenseRVA adapter;
    private List<Expense> Show;
    private ExpensesViewModel viewModel;
    private RecyclerView rcView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        viewModel= new ViewModelProvider(this).get(ExpensesViewModel.class);
        viewModel.getPresenter().setView(this);
        viewModel.getPresenter().initExpenses();
        rcView = findViewById(id.item_list_view_expenses);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        Show = viewModel.getPresenter().getExpenses();
        if(Show.isEmpty()){return ;}
        adapter = new ExpenseRVA(Show, this);

        rcView.setAdapter(adapter);
        findViewById(id.button_add_list_expense).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewModel.getPresenter().addExpense();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        rcView = findViewById(id.item_list_view_expenses);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        Show = viewModel.getPresenter().getExpenses();
        if (Show.isEmpty()) {
            return;
        }
        adapter = new ExpenseRVA(Show, this);
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
                viewModel.getPresenter().initExpenses();

                Show = viewModel.getPresenter().getExpenses();
                adapter = new ExpenseRVA(Show, this);
                rcView.setAdapter(adapter);
            }
        }
    }
    /**
     * Handle the selection of an expense from the RecyclerView.
     *
     * @param expense The selected expense.
     * @param index   The index of the selected expense in the list.
     */
    public void selectExpense(Expense expense, int index) {
        Intent intent = new Intent(this, ExpenseDetailsActivity.class);
        intent.putExtra("expense_id", expense.getId());
        intent.putExtra("list_index", index);
        startActivityForResult(intent,7777);
    }
    /**
     * Redirect to the AddEditExpenseActivity to add a new expense.
     */
    public void redirectToActivity(){
        Intent intent = new Intent(this, AddEditExpenseActivity.class);
        startActivityForResult(intent, ADDED);
    }
    /**
     * Display a short toast message.
     *
     * @param msg The message to be displayed.
     */
    public void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}