package com.example.familybudget.view.Income.ShowIncomes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.familybudget.Income;
import com.example.familybudget.R;
import java.util.List;
/**
 * RecyclerView Adapter for displaying a list of income items.
 */
public class IncomeRVA extends RecyclerView.Adapter<IncomeRVA.ViewHolder> {
    private final List<Income> incomes;
    private final ItemSelectionListener listener;
    /**
     * Constructor for IncomeRVA.
     *
     * @param items     List of Income items to be displayed.
     * @param listener  ItemSelectionListener for handling item selection events.
     */
    public IncomeRVA(List<Income> items , ItemSelectionListener listener){
        this.incomes=items;
        this.listener=listener;
    }
    /**
     * Creates and returns a new ViewHolder for the RecyclerView.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given viewType.
     */
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));    }
    /**
     * Binds data to the ViewHolder at the specified position.
     *
     * @param holder   The ViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Income currentIncome = incomes.get(position);
        holder.txtItemname.setText(String.valueOf(currentIncome.getDescription()));
        holder.txtItemId.setText(String.valueOf(currentIncome.getId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.select_Income(currentIncome, position);
            }
        });
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        return incomes.size();
    }
    /**
     * ViewHolder class for holding views of individual income items.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtItemname;
        private TextView txtItemId ;
        /**
         * Constructor for ViewHolder.
         *
         * @param view The View for this ViewHolder.
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
     * Interface definition for a callback to be invoked when an income item is selected.
     */
    public interface ItemSelectionListener{
        /**
         * Called when an income item is selected.
         *
         * @param income The selected Income item.
         * @param index  The position of the selected item in the adapter.
         */
        void select_Income(Income income, int index );
    }
}