package com.example.familybudget.View.PiggyBank.ManageTest;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.view.Family.Login.LoginPresenter;
import com.example.familybudget.view.PiggyBank.Manage.ManagePiggyBanksPresenter;
import com.example.familybudget.view.PiggyBank.Manage.ManagePiggyBanksView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ManagePiggyBanksPresenterTest {
    private ManagePiggyBanksPresenter presenter;
    private ManagePiggyBanksViewStub view ;
    @Before
    public void setup(){
        Initializer initializer = new Memory_Initializer();
        initializer.prepareDemoData();
        presenter = new ManagePiggyBanksPresenter();
        view = new ManagePiggyBanksViewStub();
        presenter.setView(view);//initialises static variable of Family and Member id to 1,1

    }
    /**
     * this test shows that a Protector(guardian) of a family see every members' of that family too
     * member number 1 is a protector of family 1
     * */

    @Test
    public void Guardian_sees_every_Piggy_Members_Bank(){
        presenter.initBanks();
        Assert.assertEquals(3, presenter.getBanks().size());
        Assert.assertEquals("Guardian account ", view.getlast_message());
        Assert.assertEquals(1, view.message_counter);

    }
    /**
     * Checks to see that a member is only shown his/hers Piggy Banks
     * Member with id 3 is not a protector
     * */
    @Test
    public void Member_sees_its_own_banks(){
        LoginPresenter.FAMILYMEMBER_ID= 3;
        presenter.initBanks();
        Assert.assertEquals(2, presenter.getBanks().size());
        Assert.assertEquals("Ward account", view.getlast_message());
        Assert.assertEquals(1, view.message_counter);



    }
    /**
     * checks to see if the redirection is the Add piggy bank activity works
     * */
    @Test
    public void redirected_to_bank_creation(){
        presenter.AddBank();
        Assert.assertTrue(view.redirection);

    }
    @After
    public void cleanup(){
        LoginPresenter.FAMILYMEMBER_ID= 1;

    }

}
