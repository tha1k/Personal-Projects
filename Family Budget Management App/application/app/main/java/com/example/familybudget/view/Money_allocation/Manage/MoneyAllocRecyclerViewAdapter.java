package com.example.familybudget.view.Money_allocation.Manage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familybudget.Money_alloc;
import com.example.familybudget.R;

import java.util.List;

public class MoneyAllocRecyclerViewAdapter extends RecyclerView.Adapter<MoneyAllocRecyclerViewAdapter.ViewHolder> {

    private final List<Money_alloc> moneyAllocList;

    // Constructor to initialize the adapter with a list of Money_alloc objects
    public MoneyAllocRecyclerViewAdapter(List<Money_alloc> moneyAllocList) {
        this.moneyAllocList = moneyAllocList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_alloc_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the views in each item
        Money_alloc moneyAlloc = moneyAllocList.get(position);

        holder.textViewAmount.setText(moneyAlloc.getAmount().toString());
        holder.textViewDate.setText(moneyAlloc.getDate().toString());
    }

    @Override
    public int getItemCount() {
        // Return the size of the data set
        return moneyAllocList.size();
    }
/**updates recycler view list by removing elements that are not in teh provided
 * @param allocations list but are in the moneyAllocList
 * after that adds elements present in the
 * @param allocations list but not in the
 * */
    public void updateData(List<Money_alloc> allocations) {
        // Remove items from the current list
        for (int i = moneyAllocList.size() - 1; i >= 0; i--) {
            if (!allocations.contains(moneyAllocList.get(i))) {
                int index = moneyAllocList.indexOf(moneyAllocList.get(i));
                moneyAllocList.remove(index);
                notifyItemRemoved(index);
            }
        }

        // Add new items
        for (int i = 0; i < allocations.size(); i++) {
            if (!moneyAllocList.contains(allocations.get(i))) {
                int index = i; // You may need to adjust this based on your actual implementation
                moneyAllocList.add(index, allocations.get(i));
                notifyItemInserted(index);
            }
        }
    }


    // ViewHolder class to hold references to the views in each item
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAmount;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views (replace 'textViewAmount' and 'textViewDate' with your actual TextView IDs)
            textViewAmount = itemView.findViewById(R.id.txt_allocation_amount);
            textViewDate = itemView.findViewById(R.id.txt_allocation_date);
        }
    }
}
