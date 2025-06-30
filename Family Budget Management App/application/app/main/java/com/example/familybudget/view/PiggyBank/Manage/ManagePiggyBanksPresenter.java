package com.example.familybudget.view.PiggyBank.Manage;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILY_ID;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Piggy_bank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagePiggyBanksPresenter {
    private ManagePiggyBanksView view ;
    private Piggy_Bank_DAO pgd;
    private Family_Member_DAO fmd;
    private Family_DAO fd;
    private List<Piggy_bank> banks;

    public void setView(ManagePiggyBanksView v){
        this.view = v;
        Initializer data= new Memory_Initializer();
        data.prepareDemoData();
        pgd= new Piggy_bank_DAOMemory();
        fmd= new Family_Member_DAOMemory();
        fd= new Family_DAOMemory();
        banks= new ArrayList<>();
        // only for debugging purposes (0,0) means not initialized
        if(FAMILY_ID==0 || FAMILYMEMBER_ID==0){
            FAMILY_ID=1;
            FAMILYMEMBER_ID=1;
        }


    }
/**
 * The protector should see all of the Piggy Banks his family has
 *
 * */

    public void  getFamilyBanks(){
        Family fam = fd.find(FAMILY_ID);
        Set<Family_Member> members= new HashSet<>();
        if(fam.getMembers()!=null){
            members= fam.getMembers();}

        List<Piggy_bank> result = new ArrayList<>();
        for(Family_Member mem: members){
            for(Piggy_bank pg : pgd.findAll()){
                if(pg.get_owner().equals(mem)){
                    banks.add(pg);
                }
            }
        }
        banks.addAll(result);

    }

    /**
     * A member is allowed to see only his/hers piggy_banks
     * */
    public void getmeberBanks(){

        Family_Member member = fmd.find(FAMILYMEMBER_ID);

        List<Piggy_bank> result = new ArrayList<>();
        for(Piggy_bank pg : pgd.findAll()){
            if(pg.get_owner().equals(member)){
                result.add(pg);
            }
            }
        banks.addAll(result);

    }
    /**
     * if the signed in member is a protector call the
     * getFamilyBanks()
     * else the
     * getmemberBanks() methods
     * */
    public void initBanks(){
        banks=null;
        banks= new ArrayList<>();
        Family_Member member = fmd.find(FAMILYMEMBER_ID);
        if(member.Is_protector() ){
            getFamilyBanks();
            view.Show_message("Guardian account ");
        }
        else{
            getmeberBanks();
            view.Show_message("Ward account");
        }
    }

    public List<Piggy_bank> getBanks() {
        List<Piggy_bank> result = new ArrayList<>(this.banks);
        return result;
    }
    public void AddBank(){
        view.redirect_to_AddeditActivity();

    }

}
