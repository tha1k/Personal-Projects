package com.example.familybudget.DaoMemory;

import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.Dao.Money_alloc_DAO;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.Expense;
import com.example.familybudget.Income;
import com.example.familybudget.Money_alloc;
import com.example.familybudget.Piggy_bank;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;

public class Memory_Initializer extends Initializer {
    /**
     * Διαγράφει τα αποθηκευμένα δεδομένα.
     */

    @Override
    protected void eraseData(){
        for(Expense expense : getExpenseDao().findAll()) {
            getExpenseDao().delete(expense);
        }

        for(Family fam : getFamilyDAO().findAll()) {
            getFamilyDAO().delete(fam);
        }

        for(Family_Member member : getFamilyMemberDAO().findAll()) {
            getFamilyMemberDAO().delete(member);
        }

        for(Income income : getIncomeDAO().findAll()) {
            getIncomeDAO().delete(income);
        }

        for(Piggy_bank bank : getPiggyBankDAO().findAll()) {
            getPiggyBankDAO().delete(bank);
        }

        for(Money_alloc alloc : getMoneyAlloDAO().findAll()) {
            getMoneyAlloDAO().delete(alloc);
        }


    }
    /**
     * Επιστρέφει όλα τα (inMemory) DAO objects.
     */

    @Override
    protected Expense_DAO getExpenseDao() {
        return new Expense_DAOMemory();
    }
    @Override
    protected  Piggy_Bank_DAO getPiggyBankDAO(){
        return new Piggy_bank_DAOMemory();
    }
    @Override
   protected   Money_alloc_DAO getMoneyAlloDAO(){
        return new Money_alloc_DAOMemory();
    }
    @Override
    protected  Income_DAO getIncomeDAO(){
        return new Income_DAOMemory();
    }
    @Override
   protected   Family_DAO getFamilyDAO(){
        return new Family_DAOMemory();
    }
    @Override
    protected  Family_Member_DAO getFamilyMemberDAO(){
        return new Family_Member_DAOMemory();
    }
}
