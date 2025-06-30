package com.example.familybudget.DaoMemory;

import com.example.familybudget.Dao.Piggy_Bank_DAO;

import com.example.familybudget.Piggy_bank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Piggy_bank_DAOMemory implements Piggy_Bank_DAO {
    protected static List<Piggy_bank> entities= new ArrayList<Piggy_bank>();
    public void delete(Piggy_bank entity){
        entities.remove(entity);
    }
    public List<Piggy_bank> findAll(){
        List<Piggy_bank> result = new ArrayList<Piggy_bank>();
        result.addAll(this.entities);
        return result;
    }
    public  void save(Piggy_bank entity){
        entity.setUid(nextId());
        entities.add(entity);
    }
    public Piggy_bank find(int PiggyBankId){
        for(Piggy_bank piggy : entities)
            if((piggy).getId() == PiggyBankId)
                return piggy;

        return null;
    }


    public Piggy_bank findbyName(String PiggyBankName){
        for(Piggy_bank piggy : entities) {
            if ((piggy).getName() == PiggyBankName){
                return piggy;}
        }
        return null;
    }
    public int nextId(){
        return (entities.size() > 0 ? entities.get(entities.size()-1).getId()+1 : 1);
    }
}
