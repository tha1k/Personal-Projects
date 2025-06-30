package com.example.familybudget.Dao;
import com.example.familybudget.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface Expense_DAO {
    void delete(Expense entity);
    /*shows all expense*/
    List<Expense> findAll();
    /* add a new expense*/
    void save(Expense entity);
    /*find expense based on id */
    Expense find(int ExpsenseId);

    /*the next valid expense Id */
    int nextId();
    /*returns a list of expenses whose end-dates have not "expired"*/


}
