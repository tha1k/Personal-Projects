package com.example.familybudget.Dao;

import com.example.familybudget.Income;

import java.util.List;

public interface Income_DAO {
    void delete(Income entity);
    /*shows all income*/
    List<Income> findAll();
    /* add a new income*/
    void save(Income entity);
    /*find income based on id */
    Income find(int ExpsenseId);

    /*the next valid income Id */
    int nextId();
    /*returns a list of incomes whose end-dates have not "expired"*/

}
