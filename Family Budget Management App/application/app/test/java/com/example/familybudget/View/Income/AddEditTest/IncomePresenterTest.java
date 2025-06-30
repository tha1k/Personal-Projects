package com.example.familybudget.View.Income.AddEditTest;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Income_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family_Member;
import com.example.familybudget.view.Income.AddEditIncome.AddEditIncomePresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class IncomePresenterTest {
    private IncomeViewStub view;
    private AddEditIncomePresenter presenter;
    private Income_DAO incomeDao;
    private Family_Member_DAO familymemberDao;
    Initializer data;

    @Before
    public void setup(){
        data = new Memory_Initializer();
        data.prepareDemoData();
        view = new IncomeViewStub();
        presenter = new AddEditIncomePresenter(view);
        incomeDao = new Income_DAOMemory();
        familymemberDao = new Family_Member_DAOMemory();
        FAMILYMEMBER_ID = 1;
    }
    /**
     * Tests the successful addition of income.
     */

    @Test
    public void successAdd(){
        Family_Member member = familymemberDao.find(FAMILYMEMBER_ID);
        int incomes = member.getIncome().size();
        presenter.saveIncome(500, true, LocalDate.now(), LocalDate.now().plusDays(5), "rent");
        Assert.assertEquals(incomes+1, member.getIncome().size());
        Assert.assertEquals("Successfully registered income.", view.last_msg);
        Assert.assertEquals(1, view.returnCounter());
        Assert.assertTrue(view.getSuccessAdd());
    }
    /**
     * Tests the successful editing of income.
     */
    @Test
    public void successEdit(){
        Family_Member member = familymemberDao.find(FAMILYMEMBER_ID);
        int incomes = member.getIncome().size();
        presenter.setAttachedId(1);
        presenter.saveIncome(100, false,LocalDate.now(), LocalDate.now().plusDays(5), "petrol");
        Assert.assertEquals(incomes, member.getIncome().size());
        Assert.assertEquals(1, view.returnCounter());
        Assert.assertEquals(1, view.id);
        Assert.assertTrue(view.success_income_edit);
    }
    /**
     * Tests an unsuccessful attempt to add or edit income.
     */
    @Test
    public void unsuccessful(){
        FAMILYMEMBER_ID = 1;
        presenter.setAttachedId(-1);
        Family_Member member = familymemberDao.find(FAMILYMEMBER_ID);
        int incomes = member.getIncome().size();
        presenter.saveIncome(100, true, LocalDate.now(), LocalDate.now().minusDays(5), "bill");
        Assert.assertEquals(incomes, member.getIncome().size());
        Assert.assertEquals(-1, view.id);
        Assert.assertEquals(1, view.returnCounter());
        Assert.assertEquals("Wrong date inputs.", view.last_msg);
    }
}