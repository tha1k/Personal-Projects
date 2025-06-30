package com.example.familybudget.view.PiggyBank.Details;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Piggy_bank;

public class DetailsPiggyBankPresenter{
    private Piggy_Bank_DAO pgd;
    private DetailsPiggyBankView view;
    private int id;
    private Piggy_bank bank ;
    Family_Member_DAO fmd;
    int index;
    public DetailsPiggyBankPresenter(DetailsPiggyBankView v){
     view= v ;
     pgd = new Piggy_bank_DAOMemory();
     fmd = new Family_Member_DAOMemory();

 }
 public void updated(){
        bank= pgd.find(id);
 }
 public void Setid(int id){
     this.id=(int) id;
     bank = pgd.find(this.id);

 }
 public int getId(){
     return this.id;
 }

 public String getName(){

     return bank.getName();

 }
 public String getGoal(){
     return bank.getInitial_goal().toString();
 }
 public String getAllocated(){

     return bank.getAllocated_amount().toString();
 }
 public String getOwnername(){
     return bank.get_owner().getName();
 }
 public String getRemainder(){
     return bank.get_remaining_amount().toString();
 }
 public String getDescription(){
     return bank.getDescription();
 }
public int IntegerAllocated(){
    int amount =  (bank.getAllocated_amount().getAmount()).intValue();
    return amount;
}
public int IntegerGoal(){
    int amount = (bank.getInitial_goal().getAmount()).intValue();
    return amount;
}
/**
 * Method deletes bank only if the signed in user is the owner of that Piggy Bank
 * */
public void deleteBank(){
     Family_Member owner = fmd.find(FAMILYMEMBER_ID);
     if(bank.get_owner().equals(owner) ) {
         //withdrawn money to the owner

         try{
         pgd.delete(pgd.find(id));
         view.Show_message("bank successfully deleted");
         owner.addToDisposableIncome(bank.getAllocated_amount());

         view.removeBank(index);

         }
         catch (Exception e){
             view.Show_message("Failed to delete , try again ");

         }
         return;
     }
     view.Show_message("Denied, you are not the owner");

 }


    public void setIndex(int listIndex) {
        this.index= listIndex;
    }
    public void editPiggyBank(){
        view.editBank(id);
    }

    public Piggy_bank getPiggyBank() {
        return this.bank;
    }
    public void see_allocations(){view.see_allocs(this.id);}
}
