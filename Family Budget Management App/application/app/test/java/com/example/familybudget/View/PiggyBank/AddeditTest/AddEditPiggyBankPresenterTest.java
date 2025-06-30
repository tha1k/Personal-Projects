package com.example.familybudget.View.PiggyBank.AddeditTest;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.view.PiggyBank.AddEdit.AddEditPiggyBankPresenter;
import com.example.familybudget.view.PiggyBank.AddEdit.AddEditPiggyBankView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddEditPiggyBankPresenterTest {
    private AddEditPiggyBankViewStub view ;
    private AddEditPiggyBankPresenter presenter;
    private Piggy_Bank_DAO pgd;
    private Family_Member_DAO fmd;
    Initializer datahelper;

    @Before
    public void setup(){
        datahelper= new Memory_Initializer();
        datahelper.prepareDemoData();
        FAMILYMEMBER_ID= 1; // member 1 TONY
        view = new AddEditPiggyBankViewStub();
        presenter = new AddEditPiggyBankPresenter(view);
        pgd= new Piggy_bank_DAOMemory();
        fmd= new Family_Member_DAOMemory();

    }
/**
 * Checks if a bank is successfully added
 * */
    @Test
    public void SuccessfulAddition(){
        Family_Member member = fmd.find(FAMILYMEMBER_ID);
        int banks = member.getPiggy_banks().size();
        presenter.SavePiggyBank(10, "phone", "touchscreen phone");
        Assert.assertEquals(banks+1, member.getPiggy_banks().size());
        Assert.assertEquals("Successfully created new piggy bank",view.last_msg);
        Assert.assertEquals(2, view.getMessage_counter());
        Assert.assertTrue(view.isSuccessful_piggy_bank_creation());

    }
    /**
     * Banks with 0$ initial goal are not accepted
     */
    @Test
    public void unsuccessfullAddition(){
        Family_Member member = fmd.find(FAMILYMEMBER_ID);
        int banks = member.getPiggy_banks().size();
        presenter.SavePiggyBank(0, "phone", "touchscreen phone");
        Assert.assertEquals(banks, member.getPiggy_banks().size());
        Assert.assertEquals(2, view.getMessage_counter());
        Assert.assertFalse(view.isSuccessful_piggy_bank_creation());
    }
    /**
     * editing a specific bank actually changes its fields
     * */
    @Test
    public void sucessfull_edit(){
        FAMILYMEMBER_ID= 3;
        presenter.setAttachedId(2);
        Family_Member member = fmd.find(FAMILYMEMBER_ID);
        int banks = member.getPiggy_banks().size();
        presenter.SavePiggyBank(1000, "newPhone", "touchscreen phone");
        Assert.assertEquals(banks, member.getPiggy_banks().size());
        Assert.assertEquals(1, view.getMessage_counter());
       Assert.assertEquals(view.id, 2);
        Assert.assertTrue(view.isSuccessful_piggy_bank_creation());
    }

    /**
     *
     * When editing if the new Goal is set to something less than the allocated amount it should not be accepted
     * */
    @Test
    public void unsucessfull_edit(){
        FAMILYMEMBER_ID= 3;
        presenter.setAttachedId(2);
        Family_Member member = fmd.find(FAMILYMEMBER_ID);
        int banks = member.getPiggy_banks().size();
        presenter.SavePiggyBank(5, "newPhone", "touchscreen phone");
        Assert.assertEquals(banks, member.getPiggy_banks().size());
        Assert.assertEquals(view.id, -1);

        Assert.assertEquals(1, view.getMessage_counter());
        //Assert.assertFalse(view.isSuccessful_piggy_bank_creation());
        Assert.assertEquals(view.last_msg, "Goal must be greater than the allocated amount ");
    }

    @After
    public void cleanup(){
        FAMILYMEMBER_ID=1;
    }
}
