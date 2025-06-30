package com.example.familybudget.View.Statistics;

import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Income_DAO;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Expense_DAOMemory;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Income_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family_Member;
import com.example.familybudget.view.Statistics.CheckStatisticsPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class StatisticsPresenterTest {
    private StatisticsStub view;
    private CheckStatisticsPresenter presenter;
    private Expense_DAO expenseDao;
    private Income_DAO incomeDao;
    private Family_Member_DAO familyMemberDao;
    private Family_DAO familyDao;
    Initializer data;
    @Before
    public void setup(){
        data = new Memory_Initializer();
        data.prepareDemoData();
        view = new StatisticsStub();
        presenter = new CheckStatisticsPresenter(view);
        expenseDao = new Expense_DAOMemory();
        incomeDao = new Income_DAOMemory();
        familyDao = new Family_DAOMemory();
        familyMemberDao = new Family_Member_DAOMemory();
    }
    /**
     * Tests the case where there is no data within the specified date range and limits.
     */
    @Test
    public void testGetNothing() {
        LocalDate startDate = LocalDate.parse("2022-01-01");
        LocalDate endDate = LocalDate.parse("2022-12-31");
        Double upperLimit = 100.0;
        Double lowerLimit = 50.0;

        double resultExpense = presenter.getFamiliesStatistics("expense", startDate, endDate, upperLimit, lowerLimit);
        double resultIncome = presenter.getFamiliesStatistics("income", startDate, endDate, upperLimit, lowerLimit);
        Assert.assertEquals(0, resultExpense, 0.001);
        Assert.assertEquals(0, resultIncome, 0.001);
    }
    /**
     * Tests the case where there is data within the specified date range and limits.
     */
    @Test
    public  void testGetAll(){
        LocalDate startDate = LocalDate.parse("1900-01-01");
        LocalDate endDate = LocalDate.parse("2900-01-01");
        Double upperLimit = 9999999.0;
        Double lowerLimit= 1.0;
        double resultExpense = presenter.getFamiliesStatistics("expense", startDate, endDate, upperLimit, lowerLimit);
        double resultIncome = presenter.getFamiliesStatistics("income", startDate, endDate, upperLimit, lowerLimit);
        Assert.assertEquals(517.5, resultExpense, 0.001);
        Assert.assertEquals(845.0, resultIncome, 0.001);
        Assert.assertEquals(5, presenter.getMemberCount());
        Assert.assertEquals(2, presenter.getFamilyCount());
        Assert.assertEquals(3, presenter.getExpenseCount());
        Assert.assertEquals(4,presenter.getIncomeCount());
    }
    /**
     * Tests the case where there is some data within the specified date range and limits.
     */
    @Test
    public void testGetSome(){
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-02-02");
        double upperLimit = 500.0;
        double lowerLimit = 58.0;
        double resultExpense = presenter.getFamiliesStatistics("expense", startDate, endDate, upperLimit, lowerLimit);
        double resultIncome = presenter.getFamiliesStatistics("income", startDate, endDate, upperLimit, lowerLimit);
        Assert.assertEquals(0, resultExpense, 0.001);
        Assert.assertEquals(0, resultIncome, 0.001);
    }
}
