package com.example.familybudget.DaoMemory;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Family;

import java.util.ArrayList;
import java.util.List;

public class Family_DAOMemory implements Family_DAO {
    protected static List<Family> entities = new ArrayList<Family>();
    public void delete(Family entity){
        entities.remove(entity);
    }
    public void save(Family entity){
        entity.setId(nextId());
        entities.add(entity);
    }


    public int nextId(){
        return (entities.size() > 0 ? entities.get(entities.size()-1).getId()+1 : 1);
    }

    public Family find(int FamilyId ){
        for(Family f_m : entities){
            if(f_m.getId()==FamilyId){
                return f_m;
            }
        }
        return null;
    }
    public List<Family> findAll(){
        List<Family> result = new ArrayList<>();
        result.addAll(entities);
        return  result ;
    }

    public Family findByName(String Name){
        for(Family f_m : entities){
            if(f_m.getFamilyName().equals(Name)){
                return f_m;
            }
        }
        return null;
    }

}
