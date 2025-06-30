package com.example.familybudget.View.MoneyAlloc.Manage;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Money;
import com.example.familybudget.Piggy_bank;
import com.example.familybudget.view.Family.Login.LoginPresenter;
import com.example.familybudget.view.Money_allocation.Manage.ManageMoneyAllocationsPresenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class ManageMoneyAllocPresenterTest {
    public static int BANK_NUM = 1;
    private ManageMoneyAllocationsPresenter presenter;
    private ManageMoneyAllocViewStub view;
    Piggy_Bank_DAO pdg;
    Money one_euro;
    @Before
    public  void setup(){
        view = new ManageMoneyAllocViewStub();
        presenter= new ManageMoneyAllocationsPresenter();
        presenter.setView(view);
        Initializer data= new Memory_Initializer();
        data.prepareDemoData();
        LoginPresenter.FAMILY_ID =1;
        LoginPresenter.FAMILYMEMBER_ID=1;
        presenter.setId(BANK_NUM);
        pdg = new Piggy_bank_DAOMemory();
        one_euro = new Money(new BigDecimal(1), Currency.getInstance("EUR"));
    }
    /**
     * Checks that a an allocation to bank is accepted by a guardian
     * family member #1
     * */
    @Test
    public void add_allocation_accepted(){
        presenter.add_alloc();
        Assert.assertEquals(view.bank,BANK_NUM);


    }
    /**
     * Checks that a an allocation to bank is not accepted by a member
     * family member #2
     * */
    @Test
    public void add_allocation_denied(){
        LoginPresenter.FAMILYMEMBER_ID=2;
        presenter.add_alloc();
        Assert.assertEquals(view.bank,-100);
        Assert.assertEquals(view.last,"Only guardian accounts can allocate");

    }
    /**
     * Checks to see if any allocation after 2023 (every time the test runs ) is filtered
     * */
    @Test
    public void filter_allocations_based_onDate(){
        Piggy_bank p = pdg.find(BANK_NUM);
        p.allocate_money(one_euro);
        presenter.filter_allocs("2003/01/02", "2023/12/31");
        Assert.assertEquals(p.get_allocations().size()-1,presenter.filter_allocs("2003/01/02", "2023/12/31").size() );

    }
    @After
    public void cleanup(){
        LoginPresenter.FAMILYMEMBER_ID=1;
    }
}
