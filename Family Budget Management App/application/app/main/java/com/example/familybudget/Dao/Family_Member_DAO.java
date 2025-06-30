package com.example.familybudget.Dao;

import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;


import java.time.LocalDate;
import java.util.List;

public interface Family_Member_DAO {

    void delete(Family_Member entity);
    /*shows all family members */
    List<Family_Member> findAll();
    /* add a new member */
    void save(Family_Member entity);
    /*find member based on id */
    Family_Member find(int MemberId);

 /*find next available id for member */
    int nextId();
    /*find a member by name */
    Family_Member findByName(String name );



}
