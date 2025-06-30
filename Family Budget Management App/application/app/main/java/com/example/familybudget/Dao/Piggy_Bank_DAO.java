package com.example.familybudget.Dao;

import com.example.familybudget.Money_alloc;
import com.example.familybudget.Piggy_bank;

import java.time.LocalDate;
import java.util.List;

public interface Piggy_Bank_DAO {
    void delete(Piggy_bank entity);
    /*shows all allocations associated with a certain Piggy_bank*/
    List<Piggy_bank> findAll();
    /* add a new allocation made to that piggy bank*/
    void save(Piggy_bank entity);
    /*find allocation based on id */
    Piggy_bank find(int PiggyBankId);

    /*the next valid Money_alloc Id */
    int nextId();

    /*find a Piggy bank by name*/
    Piggy_bank findbyName(String PiggyBankName);
}
