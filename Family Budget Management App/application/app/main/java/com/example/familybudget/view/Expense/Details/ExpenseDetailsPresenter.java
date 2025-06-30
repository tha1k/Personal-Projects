package com.example.familybudget.view.Expense.Details;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.DaoMemory.Expense_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Expense;
import com.example.familybudget.Family_Member;
/**
 * Presenter for managing the details of an expense.
 */
public class ExpenseDetailsPresenter {
    private Expense_DAO expenseDao;
    private ExpenseDetailsView view;
    private int id;
    private Expense expense;
    Family_Member_DAO familyMemberDao;
    int index;
    /**
     * Constructor for the ExpenseDetailsPresenter.
     *
     * @param view The associated view for the presenter.
     */
    public ExpenseDetailsPresenter(ExpenseDetailsView view){
        this.view = view;
        expenseDao = new Expense_DAOMemory();
        familyMemberDao = new Family_Member_DAOMemory();
    }
    /**
     * Update the expense details.
     */
    public void updated(){
        expense = expenseDao.find(id);
    }
    /**
     * Set the ID of the expense.
     *
     * @param id The ID of the expense.
     */
    public void setId(int id){
        this.id = id;
        expense = expenseDao.find(this.id);
    }
    /**
     * Get the ID of the expense.
     *
     * @return The ID of the expense.
     */
    public int getId(){
        return this.id;
    }
    /**
     * Get the description of the expense.
     *
     * @return The description of the expense.
     */
    public String getDescription(){
        return expense.getDescription();
    }
    /**
     * Get the amount of the expense.
     *
     * @return The amount of the expense.
     */
    public String getAmount(){
        return expense.getMoney().toString();
    }
    /**
     * Get the effective date range of the expense.
     *
     * @return The effective date range of the expense.
     */
    public String getDateRange(){
        return expense.getEffectiveRange();
    }
    /**
     * Get the frequency of the expense.
     *
     * @return The frequency of the expense (Recurring or Urgent).
     */
    public String getFrequency(){
        if(expense.getIsRecurring()){
            return "Recurring";
        }else{
            return "Urgent";
        }
    }
    /**
     * Get the owner of the expense.
     *
     * @return The name of the owner of the expense.
     */
    public String getOwner() {
        return expense.get_owner().getName();
    }
    /**
     * Get the registration date of the expense.
     *
     * @return The registration date of the expense.
     */
    public String getRegDate(){
        return expense.getRegistrationDate().toString();
    }
    /**
     * Get the end date of the expense.
     *
     * @return The end date of the expense.
     */
    public String getEndDate(){
        return expense.getEndDate().toString();
    }
    /**
     * Delete the expense.
     */
    public void deleteExpense(){
        Family_Member owner = familyMemberDao.find(FAMILYMEMBER_ID);
        if(expense.get_owner().equals(owner)){
            try{
                expenseDao.delete(expenseDao.find(id));
                view.showMessage("Expense successfully deleted!");
                owner.addToDisposableIncome(expense.getMoney());
                view.removeExpense(index);

            }
            catch (Exception e){
                view.showMessage("Exception");
            }
            return;
        }
        view.showMessage("You are not the owner.");
    }
    /**
     * Set the index of the expense.
     *
     * @param index The index of the expense.
     */
    public void setIndex(int index){
        this.index = index;
    }
    /**
     * Edit the expense.
     */
    public void editExpense(){
        view.editExpense(id);
    }
    /**
     * Get the expense.
     *
     * @return The expense.
     */
    public Expense getExpense(){
        return this.expense;
    }
}