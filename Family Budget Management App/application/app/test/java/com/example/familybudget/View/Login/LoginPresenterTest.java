package com.example.familybudget.View.Login;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILY_ID;

import android.app.Activity;
import android.content.Context;

import com.example.familybudget.view.Family.Login.LoginPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginPresenterTest {
    private LoginPresenter presenter;
    private LoginViewStub view;

    @Before
    public void setup(){
        view= new LoginViewStub();
        presenter= new LoginPresenter(view);

    }
    /**
     * check that incorrect Family name does not redirect user to the homepage
     * and show a message each time
     * */
    @Test
    public void Incorrect_credentialsWrongFamilyName(){
        presenter.submitAuthenticationInfo("s", "tony1", "1234");
        presenter.submitAuthenticationInfo("s", "tony1", "1234");
        presenter.submitAuthenticationInfo("s", "tony1", "1234");
        Assert.assertEquals(false, view.isAuthenticated());
        Assert.assertEquals(3, view.show_Err_counter);

    }
    /**
     * Incorrect user name password does not redirect user to homepage
     * */
    @Test
    public void Incorrect_credentialsWrongUserName_password(){
       presenter.submitAuthenticationInfo("Smiths", "tony1", "1");
       presenter.submitAuthenticationInfo("Smiths", "tony5", "1234");
       Assert.assertEquals(false, view.isAuthenticated());

        Assert.assertEquals(2, view.show_Err_counter);

    }
    /**
     * correct credentials redirect user to homepage and display the pop up message
     * "Great!!!!" and also keep the correct information about that user
     * */
    @Test
    public void correct_credentials(){
        presenter.submitAuthenticationInfo("Smiths", "tony1", "1234");
        Assert.assertEquals(true, view.isAuthenticated());
        Assert.assertEquals(1, view.show_Err_counter);
        Assert.assertEquals("Great!!!", view.last_msg);
        Assert.assertEquals(FAMILY_ID, 1);
        Assert.assertEquals(FAMILYMEMBER_ID, 1);

    }
    /**
     * Checks if registration page redirection works
     * */
    @Test
    public void successfullRedirectedToRegistrationForm(){
        presenter.RegisterFamily();
        Assert.assertEquals(true, view.isRedirected_successfull());

    }

}
