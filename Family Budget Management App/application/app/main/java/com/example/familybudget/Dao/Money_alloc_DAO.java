package com.example.familybudget.Dao;
import com.example.familybudget.Money_alloc;

import java.util.List;
import java.util.Set;

public interface Money_alloc_DAO {
    /*if the signed in member is a protector he/she can remove an allocation*/
    void delete(Money_alloc malloc);
    /*shows all allocations associated with a certain Piggy_bank*/
    List<Money_alloc> findAll();
    /* add a new allocation made to that piggy bank*/
    void save(Money_alloc entity);
    /*find allocation based on id */
    Money_alloc find(int Money_allocId);
    /*the next valid Money_alloc Id */
    int nextId();


}
