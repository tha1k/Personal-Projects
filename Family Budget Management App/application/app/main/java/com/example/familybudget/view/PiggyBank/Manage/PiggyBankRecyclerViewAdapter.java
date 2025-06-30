package com.example.familybudget.view.PiggyBank.Manage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.familybudget.Piggy_bank;
import com.example.familybudget.R;

import java.util.List;

public class PiggyBankRecyclerViewAdapter extends RecyclerView.Adapter<PiggyBankRecyclerViewAdapter.ViewHolder> {
    private final List<Piggy_bank> piggy_banks ;
    private final ItemSelectionListener listener;

    public PiggyBankRecyclerViewAdapter(List<Piggy_bank> items , ItemSelectionListener listener){
        this.piggy_banks=items;
        this.listener=listener;
    }
/**
 * each item is clickable
 * */
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.piggy_bank_list_item, parent, false));    }

    public void onBindViewHolder(@NonNull PiggyBankRecyclerViewAdapter.ViewHolder holder, int position) {
        final Piggy_bank currentBank = piggy_banks.get(position);

        holder.txtItemname.setText(String.valueOf(currentBank.getName()));
        holder.txtItemId.setText(String.valueOf(currentBank.getId()));

//entire item is clickable
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the clicked Piggy_bank object to the listener
                listener.select_PiggyBank(currentBank, position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return piggy_banks.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtItemname;
        private TextView txtItemId ;


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
    public interface ItemSelectionListener{
        void select_PiggyBank(Piggy_bank pg, int index );
    }
}
