package com.example.familybudget.view.Income.Details;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Income_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Income;
public class IncomeDetailsPresenter {
    private Income_DAO incomeDao;
    private IncomeDetailsView view;
    private int id;
    private Income income;
    Family_Member_DAO familyMemberDao;
    int index;

    public IncomeDetailsPresenter(IncomeDetailsView view){
        this.view = view;
        incomeDao = new Income_DAOMemory();
        familyMemberDao = new Family_Member_DAOMemory();
    }
    /**
     * Fetches the latest data for the income.
     */
    public void updated(){
        income = incomeDao.find(id);
    }
    public void setId(int id){
        this.id = id;
        income = incomeDao.find(this.id);
    }
    public int getId(){
        return this.id;
    }
    public String getDescription(){
        return income.getDescription();
    }
    public String getAmount(){
        return income.getMoney().toString();
    }
    public String getDateRange(){
        return income.getEffectiveRange();
    }
    /**
     * Gets the frequency of the income.
     *
     * @return The frequency of the income (Recurring or Urgent).
     */
    public String getFrequency(){return income.getIsRecurring() ? "Recurring" : "Urgent";
    }
    public String getOwner(){
        return income.get_owner().getName();
    }
    public String getRegDate(){
        return income.getRegistrationDate().toString();
    }
    public String getEndDate(){
        return income.getEndDate().toString();
    }
    /**
     * Deletes the current income and notifies the view.
     */
    public void deleteIncome(){
        Family_Member owner = familyMemberDao.find(FAMILYMEMBER_ID);
        if(income.get_owner().equals(owner)){
            try{
                incomeDao.delete(incomeDao.find(id));
                view.showMessage("Income successfully deleted!");
                view.removeIncome(index);

            }
            catch (Exception e){
                view.showMessage("Exception");
            }
         }else{
            view.showMessage("You are not the owner!");
        }
    }
    public void setIndex(int index){
        this.index = index;
    }
    public void editIncome(){
        view.editIncome(id);
    }
    public Income getIncome(){
        return this.income;
    }
}