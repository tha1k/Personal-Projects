package com.example.familybudget.view.Expense.ShowExpenses;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.familybudget.Expense;
import com.example.familybudget.R;
import java.util.List;
/**
 * RecyclerView Adapter for displaying a list of expenses.
 */
public class ExpenseRVA extends RecyclerView.Adapter<ExpenseRVA.ViewHolder>{
    private final List<Expense> expenses;
    private final ItemSelectionListener listener;
    /**
     * Constructor for ExpenseRVA.
     *
     * @param items    List of expenses to be displayed.
     * @param listener ItemSelectionListener for handling item clicks.
     */
    public ExpenseRVA(List<Expense> items, ItemSelectionListener listener){
        this.expenses = items;
        this.listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));}
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        final Expense currentExpense = expenses.get(position);
        holder.txtItemname.setText(String.valueOf(currentExpense.getDescription()));
        holder.txtItemId.setText(String.valueOf(currentExpense.getId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selectExpense(currentExpense, position);
            }
        });
    }
    @Override
    public int getItemCount(){
        return expenses.size();
    }
    /**
     * ViewHolder class for holding views of each item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtItemname;
        private TextView txtItemId ;
        /**
         * Constructor for ViewHolder.
         *
         * @param view The view containing the item layout.
         */
        public ViewHolder(View view){
            super(view);
            txtItemname = view.findViewById(R.id.txt_allocation_amount);
            txtItemId=view.findViewById(R.id.txt_allocation_date);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + txtItemname.getText() + "'";
        }
    }
    /**
     * Interface for handling item selection in the RecyclerView.
     */
    public interface ItemSelectionListener{
        /**
         * Called when an expense item is selected.
         *
         * @param expense The selected expense.
         * @param index   The index of the selected expense in the list.
         */
        void selectExpense(Expense expense, int index );
    }
}