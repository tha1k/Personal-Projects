package com.example.familybudget.View.PiggyBank.DetailsTest;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILY_ID;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Money;
import com.example.familybudget.Piggy_bank;
import com.example.familybudget.view.PiggyBank.Details.DetailsPiggyBankPresenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class DetailsActivityPresenterTest {
    DetailsPiggyBankPresenter presenter;
    DetailsPiggyBankViewStub viewStub;
    Piggy_Bank_DAO pgd;
    Money hundred;
    @Before
    public void setUp(){
        viewStub= new DetailsPiggyBankViewStub();
        presenter= new DetailsPiggyBankPresenter(viewStub);
        Initializer init = new Memory_Initializer();
        init.prepareDemoData();
        pgd= new Piggy_bank_DAOMemory();
        hundred = new Money(new BigDecimal(100), Currency.getInstance("EUR"));
        FAMILYMEMBER_ID= 3;

    }
    /**
     * Checks that setting the id actually gets the correct bank
     * */
    @Test
    public void show_correct_data(){
        presenter.Setid(3);
        Assert.assertEquals("Third" , presenter.getPiggyBank().getName());

    }
/**
 * checks that deleting a bank withdraws the money from that bank
 * */
    @Test
    public void delete_bank_change_disposable_income(){
        presenter.Setid(2);
        FAMILY_ID=1;
        int total_banks= pgd.findAll().size();
        Piggy_bank bank3 = pgd.find(2);
        Family_Member owner = bank3.get_owner();
        Money initialise_disposable_income= owner.getDisposableIncome();
        Money alloced= bank3.getAllocated_amount();
        presenter.deleteBank();
        Assert.assertEquals(viewStub.last,"bank successfully deleted");
        Assert.assertEquals(total_banks-1, pgd.findAll().size());
        Assert.assertEquals(owner.getDisposableIncome(), initialise_disposable_income.plus(alloced));



    }
    /**
     * If a member eg protector is not the owner of that bank he should not be allowed to delete it
     * */
    @Test
    public void delete_bank_unsuccessful(){
        presenter.Setid(4);
        int banks= pgd.findAll().size();
        presenter.deleteBank();
        Assert.assertEquals(viewStub.last, "Denied, you are not the owner");
        Assert.assertEquals(banks, pgd.findAll().size());


    }
    /**
     * Allocation list redirect button works
     * */
    @Test
    public void redirected_toAllocations(){
        presenter.see_allocations();
        Assert.assertTrue(viewStub.redirected);
    }
/**
 * Edit bank button works
 * */
    @Test
    public void edit_bank_redirected(){
        presenter.editPiggyBank();
        Assert.assertTrue(viewStub.redirected);
    }

    @After
    public void clean_up(){
        FAMILYMEMBER_ID= 1;
    }

}
