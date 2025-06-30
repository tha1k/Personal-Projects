package com.example.familybudget.DaoMemory;

import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.Income;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


public class Income_DAOMemory implements Income_DAO {
    protected static List<Income> entities = new ArrayList<Income>();


    public void delete(Income entity){
        entities.remove(entity);
    }
    public  List<Income> findAll(){
        List<Income> result = new ArrayList<>();
        result.addAll(this.entities);
        return result;
    }
    public void save(Income entity){
        entity.setId(nextId());
        entities.add(entity);
    }
    public int nextId(){
        return (entities.size() > 0 ? entities.get(entities.size()-1).getId()+1 : 1);
    }
    public Income find(int IncomeID) {
        for (Income e : entities) {
            if ((e).getId() == IncomeID) {
                return e;

            }

        }
        return null;
    }

}
