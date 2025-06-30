package com.example.familybudget.View.MoneyAlloc.AddTest;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Money;
import com.example.familybudget.Piggy_bank;
import com.example.familybudget.view.Family.Login.LoginPresenter;
import com.example.familybudget.view.Money_allocation.AddEdit.AddMoneyAllocationPresenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class moneyallocAddPresentertest {
    AddMoneyAllocationPresenter presenter;
    AddMoneyAllocViewStub view;
    Piggy_Bank_DAO pdg;
    @Before
    public void setup(){
        view= new AddMoneyAllocViewStub();
        presenter= new AddMoneyAllocationPresenter(view);
        Initializer data= new Memory_Initializer();
        data.prepareDemoData();
        LoginPresenter.FAMILY_ID =1;
        LoginPresenter.FAMILYMEMBER_ID=1;
        presenter.setId(2);
        pdg = new Piggy_bank_DAOMemory();
    }
    /**
     * checks that an allocation that does not exceed the remaining amount is successful
     * */
    @Test
    public void successfull_allocations(){
        Piggy_bank pg = pdg.find(presenter.getId());
        Money start = pg.getAllocated_amount();
        int allocs = pg.get_allocations().size();
        presenter.onSave(10, true);
        presenter.onSave(10,false);
        Assert.assertEquals(start, pg.getAllocated_amount());
        Assert.assertEquals(allocs+2, pg.get_allocations().size());
        Assert.assertEquals(view.counter,0);


    }
    /**
     * checks that an allocation that does exceed the remaining amount is unsuccessful
     * */
    @Test
    public void unsucessful_alloc(){
        Piggy_bank pg = pdg.find(presenter.getId());
        Money start = pg.getAllocated_amount();
        int allocs = pg.get_allocations().size();
        presenter.onSave(100000000, false);
        Assert.assertEquals(allocs, pg.get_allocations().size());
        Assert.assertEquals(start, pg.getAllocated_amount());

        Assert.assertEquals(view.last, "Allocation cannot exceed Remaining amount");



    }




}
