package com.example.familybudget.View.Expense.AddEditTest;

import com.example.familybudget.Dao.Expense_DAO;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Expense_DAOMemory;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Expense;
import com.example.familybudget.Family_Member;
import com.example.familybudget.view.Expense.AddEditExpense.AddEditExpensePresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class ExpensePresenterTest {
    private ExpenseViewStub view;
    private AddEditExpensePresenter presenter;
    private Expense_DAO expenseDao;
    private Family_Member_DAO familymemberDao;
    Initializer data;

    @Before
    public void setup(){
        data = new Memory_Initializer();
        data.prepareDemoData();
        view = new ExpenseViewStub();
        presenter = new AddEditExpensePresenter(view);
        expenseDao = new Expense_DAOMemory();
        familymemberDao = new Family_Member_DAOMemory();
        FAMILYMEMBER_ID = 1;
    }
    /**
     * Tests the successful addition of an expense.
     */
    @Test
    public void successAdd(){
        Family_Member member = familymemberDao.find(FAMILYMEMBER_ID);
        int expenses = member.getExpenses().size();
        presenter.saveExpense(500, true, LocalDate.now(), LocalDate.now().plusDays(5), "rent");
        Assert.assertEquals(expenses+1, member.getExpenses().size());
        Assert.assertEquals("Successfully registered expense.", view.last_msg);
        Assert.assertEquals(1, view.returnCounter());
        Assert.assertTrue(view.getSuccessAdd());
    }

    /**
     * Tests the successful edit of an expense.
     */
    @Test
    public void successEdit(){
        Family_Member member = familymemberDao.find(FAMILYMEMBER_ID);
        int expenses = member.getExpenses().size();
        presenter.setAttachedId(1);
        presenter.saveExpense(100, false,LocalDate.now(), LocalDate.now().plusDays(5), "petrol");
        Assert.assertEquals(expenses, member.getExpenses().size());
        Assert.assertEquals(1, view.returnCounter());
        Assert.assertEquals(1, view.id);
        Assert.assertTrue(view.success_expense_edit);
    }

    /**
     * Tests unsuccessful attempt due to date inputs.
     */
    @Test
    public void unsuccessful(){
        FAMILYMEMBER_ID = 1;
        presenter.setAttachedId(-1);
        Family_Member member = familymemberDao.find(FAMILYMEMBER_ID);
        int expenses = member.getExpenses().size();
        presenter.saveExpense(100, true, LocalDate.now(), LocalDate.now().minusDays(5), "bill");
        Assert.assertEquals(expenses, member.getExpenses().size());
        Assert.assertEquals(-1, view.id);
        Assert.assertEquals(1, view.returnCounter());
        Assert.assertEquals("Wrong date inputs.", view.last_msg);
    }
}