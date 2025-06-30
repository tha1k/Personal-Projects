package com.example.familybudget.view.Money_allocation.AddEdit;


import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;

import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Money_alloc_DAO;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Money_alloc_DAOMemory;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Money;
import com.example.familybudget.Money_alloc;
import com.example.familybudget.Piggy_bank;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Currency;
/**Responsible for handling the addition of a specific allocation to the previously selected Piggy bank
 * */
public class AddMoneyAllocationPresenter {
    private AddMoneyAllocationsView view;
    private Piggy_Bank_DAO pgd;
    private Money_alloc_DAO md;
    private Family_Member_DAO fmd;
    private int id ;
    public AddMoneyAllocationPresenter(AddMoneyAllocationsView v ){
        view= v;
        pgd = new Piggy_bank_DAOMemory();
        md = new Money_alloc_DAOMemory();
        fmd = new Family_Member_DAOMemory();

    }
    public void setId(int bankid ){
        id = bankid;
    }
    public int getId(){return this.id;}
    /**onSave method allocates the amount under the condition that it doesn't exceed
     * the remaining amount needed for that specific bank.
     * If so it saves it to memory and to assign it to the object instance of the selected Piggy bank
     * @param amount is the amount of money represented as an Integer
     * @param negative dictated wether or no the amount is negated
     * */
    public void onSave(int amount, boolean negative ){
        if(negative==true){ amount =  amount * -1;
        }
        Money m1 = new Money(new BigDecimal(amount), Currency.getInstance("EUR"));
        Piggy_bank pg = pgd.find(id);
        try{
            Family_Member member= fmd.find(FAMILYMEMBER_ID);
            member.allocate_money(m1, pg);
            Money_alloc money_alloc = new Money_alloc(m1, LocalDate.now());
            md.save(money_alloc);
            view.suceessful_allocation(id);

        }
        catch(Exception e){
            view.Show_message("Allocation cannot exceed Remaining amount");
        }

    }



}
