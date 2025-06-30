package com.example.familybudget.view.PiggyBank.AddEdit;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Money;
import com.example.familybudget.Piggy_bank;

import java.math.BigDecimal;
import java.util.Currency;

public class  AddEditPiggyBankPresenter {
    AddEditPiggyBankView view;

    Family_Member_DAO fmd ;
    Piggy_Bank_DAO piggy_bank_dao;
    /**refers to the id of the selected Piggy bank instance
     * or to a new instance of Piggy bank if initialized = -1
     * */
    int attachedId;
    public AddEditPiggyBankPresenter(AddEditPiggyBankView v){
        this.view= v;
        fmd = new Family_Member_DAOMemory();
        piggy_bank_dao= new Piggy_bank_DAOMemory();
        attachedId=-1;

    }
    /**Either saves new piggy bank if it one has not been selected by the caller activity
     * @see int attachedId
     * or edits a selected
     * */
    public void SavePiggyBank(int Initial_goal, String name, String Desc){

        Money goal = new Money(new BigDecimal(Initial_goal), Currency.getInstance("EUR"));
        Family_Member user = fmd.find(FAMILYMEMBER_ID);
        if(attachedId == -1) {
            Piggy_bank pg = new Piggy_bank(name, Desc ,goal);

            String person = user.getName();
            view.Show_message("new piggy bank created for: " + person);
            if (Initial_goal > 0) {
                user.addPiggy_bank(pg);
                piggy_bank_dao.save(pg);
                view.SuccessfullyFinishAdd("Successfully created new piggy bank");
                return;
            }
            view.Show_message("Set initial goal, unsuccessful!!!");
        }else{ //edit mode
            Piggy_bank pg = piggy_bank_dao.find(attachedId);
            pg.setName(name);
            pg.setDescription(Desc);
            int comparison= (pg.getAllocated_amount().getAmount()).compareTo(BigDecimal.valueOf(Initial_goal));
            if(comparison<0 ) {
                pg.setInitial_goal(goal);
                view.successfullyFinishActivityEdit("Updated bank: "+ String.valueOf(attachedId),this.attachedId );
                return;
            }
            view.Show_message("Goal must be greater than the allocated amount ");


        }
    }

    public void setAttachedId(int bankId) {
        this.attachedId= bankId;
    }
}
