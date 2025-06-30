package com.example.familybudget.DaoMemory;

import android.util.Log;

import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.Expense;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Expense_DAOMemory implements Expense_DAO {
    protected static List<Expense> entities = new ArrayList<Expense>();
    public void delete(Expense entity){
        entities.remove(entity);

    }
    public  List<Expense> findAll(){
        List<Expense> result = new ArrayList<>();
        result.addAll(this.entities);
        return result;
    }
    public void save(Expense entity){
        entity.setId(nextId());
        entities.add(entity);
    }
    public int nextId(){
        return (entities.size() > 0 ? entities.get(entities.size()-1).getId()+1 : 1);
    }
    public Expense find(int ExpenseID) {
        for (Expense e : entities) {
            if ((e).getId() == ExpenseID) {
                return e;

            }

        }
        return null;
    }

}
