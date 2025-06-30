package com.example.familybudget.View.Expense.DetailsTest;

import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Expense_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Expense;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Money;
import com.example.familybudget.view.Expense.Details.ExpenseDetailsPresenter;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExpenseDetailsTest {
    ExpenseDetailsPresenter presenter;
    ExpenseDetailsStub view;
    Expense_DAO expenseDao;
    @Before
    public void setUp(){
        Initializer initializer = new Memory_Initializer();
        view = new ExpenseDetailsStub();
        presenter = new ExpenseDetailsPresenter(view);
        initializer.prepareDemoData();
        expenseDao = new Expense_DAOMemory();
        FAMILYMEMBER_ID = 1;
    }
    /**
     * Tests the method to show expense data.
     */
    @Test
    public void show_data(){
        presenter.setId(1);
        Assert.assertEquals("Rent", presenter.getExpense().getDescription());
    }
    /**
     * Tests the deletion and edit of an expense.
     */
    @Test
    public void delete_edit(){
        presenter.setId(1);
        int expenses = expenseDao.findAll().size();
        Expense expense2 = expenseDao.find(1);
        Family_Member member = expense2.get_owner();
        Money disposable = member.getDisposableIncome();
        Money expense = expense2.getMoney();
        presenter.deleteExpense();
        Assert.assertEquals("Expense successfully deleted!", view.last);
        Assert.assertEquals(expenses-1, expenseDao.findAll().size());
        Assert.assertEquals(member.getDisposableIncome(), disposable.plus(expense));
    }
    /**
     * Tests an unsuccessful attempt to delete an expense due to ownership.
     */
    @Test
    public void delete_unsuccessful(){
        presenter.setId(2);
        int expenses = expenseDao.findAll().size();
        presenter.deleteExpense();
        Assert.assertEquals("You are not the owner.", view.last);
        Assert.assertEquals(expenses, expenseDao.findAll().size());
    }
    /**
     * Tests the redirection to edit an expense.
     */
    @Test
    public void redirectEdit(){
        presenter.editExpense();
        Assert.assertTrue(view.redirected);
    }
}
