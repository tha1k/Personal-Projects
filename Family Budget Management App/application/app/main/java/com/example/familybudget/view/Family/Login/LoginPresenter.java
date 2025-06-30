package com.example.familybudget.view.Family.Login;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;

import java.util.Set;

/**Login Presenter handles the Business logic of the login screen
 * */
public class LoginPresenter {

    private LoginView view;
    Family_DAO familyDao;
    public static int FAMILYMEMBER_ID=0;
    public static int FAMILY_ID=0;

    public LoginPresenter(LoginView view){
        this.view= view;
        familyDao= new Family_DAOMemory();
        Initializer dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();
    }
    /**
     * after the conditions for authentication have been met
     * Redirect user to the HomePage if not show him a pop-up message to inform him it was
     * unsuccessful due to incorrect credentials
     */

    public void submitAuthenticationInfo(String Family_name, String User, String password ) {
        Family_name= Family_name.trim();
        User = User.trim();
        password = password.trim();

        Family fam= familyDao.findByName(Family_name);

        if(fam!= null) {
            Set<Family_Member> fam_members = fam.getMembers();
            for(Family_Member m : fam_members){
                if(m.getUsername().equals(User) && m.getPassword().equals(password)){
                        FAMILYMEMBER_ID=m.getID();
                        FAMILY_ID=fam.getId();
                        view.Show_Err("Great!!!");
                        //view.Show_Err(signedInUser.toString());
                        view.submit_AuthenticationInfo();
                        return;                    }



            }
            view.Show_Err("Invalid Credentials");

        } else {
            view.Show_Err("Family not found");
        }

    }
    public void RegisterFamily(){
        view.Redirect_to_registration_page();
    }

}
