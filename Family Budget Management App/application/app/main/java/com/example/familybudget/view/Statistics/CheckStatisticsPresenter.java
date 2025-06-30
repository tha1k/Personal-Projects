package com.example.familybudget.view.Statistics;

import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Expense_DAOMemory;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Income_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.Expense;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Income;
import com.example.familybudget.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Presenter class for checking statistics based on user-input filters.
 */
public class CheckStatisticsPresenter {
    private Family_DAO familyDao;
    private Family_Member_DAO familyMemberDao;
    private Income_DAO incomeDao;
    private Expense_DAO expenseDao;
    private List<Family> families;
    private List<Income> incomes;
    private List<Expense> expenses;
    private static int familyCount, memberCount, incomeCount, expenseCount = 0;

    private CheckStatisticsView view;
    /**
     * Constructor for the CheckStatisticsPresenter class.
     *
     * @param v The associated view for displaying statistics.
     */
    public CheckStatisticsPresenter(CheckStatisticsView v) {
        this.view = v;
        Initializer data = new Memory_Initializer();
        data.prepareDemoData();
        familyDao = new Family_DAOMemory();
        familyMemberDao = new Family_Member_DAOMemory();
        incomeDao = new Income_DAOMemory();
        expenseDao = new Expense_DAOMemory();
        incomes = new ArrayList<>();
        expenses = new ArrayList<>();
        families = new ArrayList<>();
    }

    /**
     * Calculates and retrieves statistics based on user-input filters.
     *
     * @param type     The type of statistics to calculate ("income" or "expense").
     * @param startDate The start date for the filter.
     * @param endDate   The end date for the filter.
     * @param upper     The upper limit for the filter.
     * @param lower     The lower limit for the filter.
     * @return The calculated average based on the provided filters.
     */
    public double getFamiliesStatistics(String type, LocalDate startDate, LocalDate endDate, Double upper, Double lower) {
        families = familyDao.findAll();
        familyCount = families.size();
        List<Income> filteredIncomes = new ArrayList<>();
        List<Expense> filteredExpenses = new ArrayList<>();
        if (upper == null) {
            upper = Double.MIN_VALUE;
        }
        if (lower == null) {
            lower = Double.MAX_VALUE;
        }
        Set<Family_Member> members = new HashSet<>();
        for (Family family : families) {
            if (family.getMembers() != null) {
                for (Object member : family.getMembers()) {
                    members.add((Family_Member) member);
                }
            }
        }
        memberCount = members.size();
        if(type.equals("income")) {
            incomes = new ArrayList<>();
            for (Family_Member member : members) {
                for (Income income : incomeDao.findAll()) {
                    if (income.get_owner().equals(member)) {
                        incomes.add(income);
                    }
                }
            }
            for (Income income : incomes) {
                if ((income.getRegistrationDate().isAfter(startDate) || income.getRegistrationDate().isEqual(startDate)) &&
                        (income.getRegistrationDate().isBefore(endDate) || income.getRegistrationDate().isEqual(endDate))) {
                    filteredIncomes.add(income);
                }
            }
            incomeCount=filteredIncomes.size();
            double result = 0;
            for (Income income : filteredIncomes) {
                if(income.getMoney().getAmount().compareTo(BigDecimal.valueOf(lower)) >= 0 &&
                income.getMoney().getAmount().compareTo(BigDecimal.valueOf(upper)) <=0) {
                    result += Double.parseDouble(String.valueOf(income.getMoney().getAmount()));
                }
            }
            return result / families.size();
        }
        else if(type.equals("expense")){
            expenses = new ArrayList<>();
            for (Family_Member member : members) {
                for (Expense expense : expenseDao.findAll()) {
                    if (member.equals(expense.get_owner())) {
                        expenses.add(expense);
                    }
                }
            }
            for (Expense expense : expenses) {
                if ((expense.getRegistrationDate().isAfter(startDate) || expense.getRegistrationDate().isEqual(startDate)) &&
                        (expense.getRegistrationDate().isBefore(endDate) || expense.getRegistrationDate().isEqual(endDate))) {

                    filteredExpenses.add(expense);
                }
            }
            double result = 0;
            for (Expense expense : filteredExpenses) {
                expenseCount++;
                System.out.println(expenseCount);
                BigDecimal amount = expense.getMoney().getAmount();
                if (amount.compareTo(BigDecimal.valueOf(lower)) >= 0 &&
                amount.compareTo(BigDecimal.valueOf(upper)) <=0) {
                    result += Double.parseDouble(String.valueOf(expense.getMoney().getAmount()));
                }
            }
            return result / families.size();
        }
        return 1.0;
    }
    public int getFamilyCount(){
        return familyCount;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public int getExpenseCount() {
        return expenseCount;
    }

    public int getIncomeCount() {
        return incomeCount;
    }
}