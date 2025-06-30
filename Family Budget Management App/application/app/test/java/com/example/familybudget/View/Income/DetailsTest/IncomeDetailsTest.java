package com.example.familybudget.View.Income.DetailsTest;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;

import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Income_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Income;
import com.example.familybudget.Money;
import com.example.familybudget.view.Income.Details.IncomeDetailsPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IncomeDetailsTest {
    IncomeDetailsPresenter presenter;
    IncomeDetailsStub view;
    Income_DAO incomeDao;
    @Before
    public void setUp(){
        Initializer initializer = new Memory_Initializer();
        view = new IncomeDetailsStub();
        presenter = new IncomeDetailsPresenter(view);
        initializer.prepareDemoData();
        incomeDao = new Income_DAOMemory();
        FAMILYMEMBER_ID = 1;
    }
    /**
     * Tests the method to show income data.
     */
    @Test
    public void show_data(){
        presenter.setId(1);
        Assert.assertEquals("Pay", presenter.getIncome().getDescription());
    }
    /**
     * Tests the deletion of income.
     */
    @Test
    public void delete_edit(){
        presenter.setId(1);
        int incomes = incomeDao.findAll().size();
        Income income2 = incomeDao.find(1);
        Family_Member member = income2.get_owner();
        Money disposable = member.getDisposableIncome();
        Money income = income2.getMoney();
        presenter.deleteIncome();
        Assert.assertEquals("Income successfully deleted!", view.last);
        Assert.assertEquals(incomes-1, incomeDao.findAll().size());
    }
    /**
     * Tests an unsuccessful attempt to delete income.
     */
    @Test
    public void delete_unsuccessful(){
        presenter.setId(2);
        int incomes = incomeDao.findAll().size();
        presenter.deleteIncome();
        Assert.assertEquals("You are not the owner!", view.last);
        Assert.assertEquals(incomes, incomeDao.findAll().size());
    }
    /**
     * Tests the redirection to edit income.
     */
    @Test
    public void redirectEdit(){
        presenter.editIncome();
        Assert.assertTrue(view.redirected);
    }
}
