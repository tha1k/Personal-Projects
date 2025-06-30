package com.example.familybudget.view.Income.AddEditIncome;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Income_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Income;
import com.example.familybudget.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class AddEditIncomePresenter {
    AddEditIncomeView view;
    Income_DAO incomeDao;
    Family_Member_DAO familyMemberDao;
    int attachedId;
    public AddEditIncomePresenter(AddEditIncomeView v){
        this.view = v;
        familyMemberDao = new Family_Member_DAOMemory();
        incomeDao = new Income_DAOMemory();
        attachedId = -1;
    }
    /**
     * Save or update an income entry based on the provided parameters.
     *
     * @param money       The amount of money for the income.
     * @param isRecurring Whether the income is recurring.
     * @param regDate     The registration date of the income.
     * @param endDate     The end date of the income (if recurring).
     * @param description The description of the income.
     */
    public void saveIncome(double money, boolean isRecurring, LocalDate regDate, LocalDate endDate, String description){
        Money amount = new Money((new BigDecimal(money)), Currency.getInstance("EUR"));
        Family_Member member = familyMemberDao.find(FAMILYMEMBER_ID);
        if(regDate.isAfter(endDate)) {
            view.showMessage("Wrong date inputs.");
            return;
        }
        if(attachedId == -1){
            Income income = new Income(amount, isRecurring, regDate, endDate, description);
            member.addIncome(income);
            incomeDao.save(income);
            view.successAdd("Successfully registered income.");
        }else{
            Income income = incomeDao.find(attachedId);
            income.setIsRecurring(isRecurring);
            income.setRegistrationDate(regDate);
            income.setEndDate(endDate, regDate);
            income.setMoney(amount);
            income.setDescription(description);
            view.successEdit("Updated income " + attachedId, this.attachedId);
        }
    }
    /**
     * Set the ID of the income entry that is being edited.
     *
     * @param attachedId The ID of the income entry.
     */
    public void setAttachedId(int attachedId){
        this.attachedId = attachedId;
    }
}
