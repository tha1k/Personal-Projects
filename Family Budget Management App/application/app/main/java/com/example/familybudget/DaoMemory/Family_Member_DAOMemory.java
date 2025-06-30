package com.example.familybudget.DaoMemory;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;

import java.util.ArrayList;
import java.util.List;

public class Family_Member_DAOMemory implements Family_Member_DAO {
    protected static List<Family_Member> entities = new ArrayList<Family_Member>();
    public void delete(Family_Member entity){
        entities.remove(entity);
    }
    public void save(Family_Member entity){
        entity.setID(nextId());
        entities.add(entity);
    }


    public int nextId(){
        return (entities.size() > 0 ? entities.get(entities.size()-1).getID()+1 : 1);
    }

    public Family_Member find(int MemberId ){
        for(Family_Member f_m : entities){
            if(f_m.getID()==MemberId){
                return f_m;
            }
        }
        return null;
    }
    public List<Family_Member> findAll(){
        List<Family_Member> result = new ArrayList<>();
        result.addAll(entities);
        return  result ;
    }
    public Family_Member findByName(String name ){
        {
            for(Family_Member f_m : entities){
                if(f_m.getName()==name){
                    return f_m;
                }
            }
            return null;
        }
    }

}
