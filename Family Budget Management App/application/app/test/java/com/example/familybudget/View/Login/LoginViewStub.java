package com.example.familybudget.View.Login;

import com.example.familybudget.view.Family.Login.LoginView;

public class LoginViewStub implements LoginView {
    boolean Authenticated=false;
    boolean redirected_successfull=false;
    int show_Err_counter;
    String last_msg;
    public void submit_AuthenticationInfo(){
        Authenticated= true;
    }
    public void Redirect_to_registration_page(){
        redirected_successfull=true;
    }
    public void Show_Err(String msg){
        show_Err_counter=show_Err_counter+1;
        last_msg= msg;
    }

    public boolean isAuthenticated() {
        return Authenticated;
    }

    public boolean isRedirected_successfull() {
        return redirected_successfull;
    }


}
