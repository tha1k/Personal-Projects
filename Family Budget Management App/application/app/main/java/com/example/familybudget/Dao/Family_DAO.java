package com.example.familybudget.Dao;

import com.example.familybudget.Family;

import java.util.List;

public interface Family_DAO {
    /*we want a method to families members */
    void delete(Family entity);
    /*shows all family members */
    List<Family> findAll();
    /* add a new member */
    void save(Family entity);
    /*find allocation based on id */
    Family find(int MemberId);

    /*find next available id for member */
    int nextId();

    /*find a family by name*/
     Family findByName(String name );


}
