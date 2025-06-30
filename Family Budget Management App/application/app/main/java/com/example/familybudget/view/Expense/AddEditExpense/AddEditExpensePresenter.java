package com.example.familybudget.view.Expense.AddEditExpense;

import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;

import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import com.example.familybudget.DaoMemory.Expense_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.Expense;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Money;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Presenter for the Add/Edit Expense feature.
 */
public class AddEditExpensePresenter {
    AddEditExpenseView view;
    Expense_DAO expenseDao;
    Family_Member_DAO familyMemberDao;
    int attachedId;
    /**
     * Constructor for the AddEditExpensePresenter.
     *
     * @param v The associated view for the presenter.
     */
    public AddEditExpensePresenter(AddEditExpenseView v){
        this.view = v;
        familyMemberDao = new Family_Member_DAOMemory();
        expenseDao = new Expense_DAOMemory();
        attachedId = -1;
    }
    /**
     * Save the expense with the provided details.
     *
     * @param money       The amount of money for the expense.
     * @param isRecurring Flag indicating whether the expense is recurring.
     * @param regDate     The registration date of the expense.
     * @param endDate     The end date of the expense.
     * @param description The description of the expense.
     */
    public void saveExpense(double money, boolean isRecurring, LocalDate regDate, LocalDate endDate, String description){
        Money amount = new Money((new BigDecimal(money)), Currency.getInstance("EUR"));
        Family_Member member = familyMemberDao.find(FAMILYMEMBER_ID);
        if(regDate.isAfter(endDate)) {
            view.showMessage("Wrong date inputs.");
            return;
        }
        if(attachedId == -1){
            Expense expense = new Expense(amount, isRecurring, regDate, endDate, description);
            member.addExpense(expense);
            expenseDao.save(expense);
            view.successAdd("Successfully registered expense.");
        }else{
            Expense expense = expenseDao.find(attachedId);
            expense.setIsRecurring(isRecurring);
            expense.setRegistrationDate(regDate);
            expense.setEndDate(endDate, regDate);
            expense.setMoney(amount);
            expense.setDescription(description);
            view.successEdit("Updated expense " + attachedId, this.attachedId);
        }
    }
    /**
     * Set the attached expense ID.
     *
     * @param attachedId The ID of the attached expense.
     */
    public void setAttachedId(int attachedId){
        this.attachedId = attachedId;
    }
}
