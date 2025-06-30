package com.example.familybudget.view.Income.ShowIncomes;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILY_ID;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Income_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Income;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IncomePresenter {
    private IncomeView view ;
    private Income_DAO incomeDao;
    private Family_Member_DAO fmd;
    private Family_DAO fd;
    private List<Income> incomes;
    /**
     * Sets the view associated with this presenter.
     *
     * @param v The view to be associated with this presenter.
     */
    public void setView(IncomeView v){
        this.view = v;
        Initializer data= new Memory_Initializer();
        data.prepareDemoData();
        incomeDao = new Income_DAOMemory();
        fmd= new Family_Member_DAOMemory();
        fd= new Family_DAOMemory();
        incomes = new ArrayList<>();
        if(FAMILY_ID==0 || FAMILYMEMBER_ID==0){
            FAMILY_ID=1;
            FAMILYMEMBER_ID=1;
        }
    }

    /**
     * Retrieves family incomes and adds them to the list of incomes.
     */
    public void  getFamilyIncomes(){
        Family fam = fd.find(FAMILY_ID);
        Set<Family_Member> members= new HashSet<>();
        if(fam.getMembers()!=null){
            members= fam.getMembers();}

        List<Income> result = new ArrayList<>();
        for(Family_Member mem: members){
            for(Income income : incomeDao.findAll()){
                if(income.get_owner().equals(mem)){
                    incomes.add(income);
                }
            }
        }
        incomes.addAll(result);

    }
    /**
     * Retrieves member incomes and adds them to the list of incomes.
     */
    public void getMemberIncomes(){

        Family_Member member = fmd.find(FAMILYMEMBER_ID);
        List<Income> result = new ArrayList<>();
        for(Income income : incomeDao.findAll()){
            if(income.get_owner().equals(member)) {
                result.add(income);
            }
        }
        incomes.addAll(result);

    }
    /**
     * Initializes the list of incomes based on the user's role (Guardian or Ward).
     */
    public void initIncomes(){
        incomes = null;
        incomes = new ArrayList<>();
        Family_Member member = fmd.find(FAMILYMEMBER_ID);
        if(member.Is_protector() ){
            getFamilyIncomes();
            view.showMessage("Guardian account");
        }
        else{
            getMemberIncomes();
            view.showMessage("Ward account");
        }
    }
    /**
     * Gets the list of incomes.
     *
     * @return The list of incomes.
     */
    public List<Income> getIncomes() {
        List<Income> result = new ArrayList<>(this.incomes);
        return result;
    }
    /**
     * Redirects to the activity responsible for adding a new income.
     */
    public void addIncome(){
        view.redirectToActivity();
    }
}
