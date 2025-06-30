package com.example.familybudget.view.Expense.ShowExpenses;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILY_ID;

import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Expense_DAOMemory;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Expense;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Presenter for managing the display of expenses.
 */
public class ExpensePresenter {
    private ExpenseView view ;
    private Expense_DAO expenseDao;
    private Family_Member_DAO fmd;
    private Family_DAO fd;
    private List<Expense> expenses;
    /**
     * Set the associated view for the presenter.
     *
     * @param v The associated view.
     */
    public void setView(ExpenseView v){
        this.view = v;
        Initializer data= new Memory_Initializer();
        data.prepareDemoData();
        expenseDao = new Expense_DAOMemory();
        fmd= new Family_Member_DAOMemory();
        fd= new Family_DAOMemory();
        expenses = new ArrayList<>();
        if(FAMILY_ID==0 || FAMILYMEMBER_ID==0){
            FAMILY_ID=1;
            FAMILYMEMBER_ID=1;
        }
    }

    /**
     * Retrieve family expenses and add them to the list of expenses.
     */
    public void  getFamilyExpenses(){
        Family fam = fd.find(FAMILY_ID);
        Set<Family_Member> members= new HashSet<>();
        if(fam.getMembers()!=null){
            members= fam.getMembers();}

        List<Expense> result = new ArrayList<>();
        for(Family_Member mem: members){
            for(Expense expense : expenseDao.findAll()){
                if(expense.get_owner().equals(mem)){
                    expenses.add(expense);
                }
            }
        }
        expenses.addAll(result);

    }
    /**
     * Retrieve member expenses and add them to the list of expenses.
     */
    public void getMemberExpenses(){

        Family_Member member = fmd.find(FAMILYMEMBER_ID);
        List<Expense> result = new ArrayList<>();
        for(Expense expense : expenseDao.findAll()){
            if(expense.get_owner().equals(member)) {
                result.add(expense);
            }
        }
        expenses.addAll(result);

    }
    /**
     * Initialize the list of expenses based on the family or member role.
     */
    public void initExpenses(){
        expenses = null;
        expenses = new ArrayList<>();
        Family_Member member = fmd.find(FAMILYMEMBER_ID);
        getMemberExpenses();
        if(member.Is_protector() ){
            getFamilyExpenses();
            view.showMessage("Guardian account");
        }
        else{
            getMemberExpenses();
            view.showMessage("Ward account");
        }
    }

    /**
     * Get the list of expenses.
     *
     * @return The list of expenses.
     */
    public List<Expense> getExpenses() {
        List<Expense> result = new ArrayList<>(this.expenses);
        return result;
    }
    /**
     * Redirect to the AddEditExpenseActivity to add a new expense.
     */
    public void addExpense(){
        view.redirectToActivity();
    }
}