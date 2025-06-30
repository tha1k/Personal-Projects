package com.example.familybudget.DaoMemory;

import com.example.familybudget.Dao.Money_alloc_DAO;
import com.example.familybudget.Money;
import com.example.familybudget.Money_alloc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Money_alloc_DAOMemory implements Money_alloc_DAO {
    protected static List<Money_alloc> entities = new ArrayList<>();
    /*if the signed in member is a protector he/she can remove an allocation*/
    public void delete(Money_alloc malloc){
        entities.remove(malloc);

    }
    /*shows all allocations associated with a certain Piggy_bank*/
    public List<Money_alloc> findAll(){
        ArrayList<Money_alloc> result = new ArrayList<Money_alloc>();
        result.addAll(entities);
        return result;
    }
    /* add a new allocation made to that piggy bank*/
    public void save(Money_alloc entity){
        entity.setID(nextId());
        entities.add(entity);
    }
    /*find allocation based on id */
    public Money_alloc find(int allocId ){
        for(Money_alloc moneyAlloc : entities)
            if((moneyAlloc).getID() == allocId)
                return moneyAlloc;

        return null;
    }
    /*the next valid Money_alloc Id */
    @Override

    public int nextId(){
        return (entities.size() > 0 ? entities.get(entities.size()-1).getID()+1 : 1);
    }

}
