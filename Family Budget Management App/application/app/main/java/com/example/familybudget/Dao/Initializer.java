package com.example.familybudget.Dao;


import com.example.familybudget.Expense;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Income;
import com.example.familybudget.Money;
import com.example.familybudget.Money_alloc;
import com.example.familybudget.Piggy_bank;
import com.example.familybudget.Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public abstract class Initializer {
    /**
     *Erases all stored data
     */
    protected abstract void eraseData();
    /**
    *abstract methods to get each domain class data access object
     * doesn't restrict our program to memory only daos
     */
    protected abstract Expense_DAO getExpenseDao();
    protected abstract Piggy_Bank_DAO getPiggyBankDAO();
    protected abstract Money_alloc_DAO getMoneyAlloDAO();
    protected abstract Income_DAO getIncomeDAO();
    protected abstract Family_DAO getFamilyDAO();
    protected abstract Family_Member_DAO getFamilyMemberDAO();

    public void prepareDemoData(){
        eraseData();
        /*misc */
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 2, 15);
        LocalDate date3 = LocalDate.of(2023, 4, 5);
        LocalDate date4 = LocalDate.of(2023, 6, 20);
        LocalDate date5 = LocalDate.of(2023, 8, 10);
        LocalDate date6 = LocalDate.of(2023, 9, 30);
        LocalDate date7 = LocalDate.of(2023, 11, 8);
        LocalDate date8 = LocalDate.of(2023, 12, 25);
        LocalDate date9 = LocalDate.of(2024, 3, 1);
        LocalDate date10 = LocalDate.of(2024, 5, 12);
        Money thousand = new Money(new BigDecimal(1000), Currency.getInstance("EUR"));
        Money hundread = new Money(new BigDecimal(100), Currency.getInstance("EUR"));
        Money fifty = new Money(new BigDecimal(50), Currency.getInstance("EUR"));
        //create 2 Families and add members

        Family_DAO fd= getFamilyDAO();
        Family_Member_DAO fmd= getFamilyMemberDAO();
        Family_Member Mr_smith= new Family_Member("tony", "tony1", "1234", true);
        Family family1 = new Family("Smiths", Mr_smith);
        family1.setProtector(Mr_smith);
        family1.addFamilyMember(Mr_smith);

        fd.save(family1);
        fmd.save(Mr_smith);
        Family_Member mrsSmith = new Family_Member("Mary", "mary1", "5679", false);
        Family_Member childSmith = new Family_Member("Sara", "sara1", "saraPass", false);
        family1.addFamilyMember(mrsSmith);
        family1.addFamilyMember(childSmith);
        fmd.save(mrsSmith);
        fmd.save(childSmith);
        /*family1 is a family of 3 */
        Family_Member Mr_McGil= new Family_Member("tony", "john1", "5678", true);
        Family family2= new Family("McGils",Mr_McGil);
        family2.setProtector(Mr_McGil);
        fd.save(family2);
        family2.addFamilyMember(Mr_McGil);
        /*family2 is a family of 2 */
        Family_Member MrsMcGil = new Family_Member("Liz", "liz", "5624", false);
        fmd.save(Mr_McGil);
        fmd.save(MrsMcGil);
        family2.addFamilyMember(MrsMcGil);

        // create expenses and incomes for some members (some recurring)

        Income_DAO id = getIncomeDAO();
        Expense_DAO exp_d = getExpenseDao();
        /*family 1 */
        Income transaction1 = new Income(Money.euros(BigDecimal.valueOf(545)), true, date1, date1.plusDays(5), "Pay");

        Expense expense1 = new Expense(Money.euros(BigDecimal.valueOf(345)), true, date2, date2.plusDays(5), "Rent");

        Mr_smith.addIncome(transaction1);
        Mr_smith.addExpense(expense1);

        Income transaction2 = new Income(Money.euros(BigDecimal.valueOf(545)), true, date1, date1.plusDays(5), "Pay ");

        mrsSmith.addIncome(transaction2);

        id.save(transaction1);

        exp_d.save(expense1);

        id.save(transaction2);

        Income transaction3 = new Income(Money.euros(BigDecimal.valueOf(55)), false, date2, date2.plusDays(5), "Lemonade stand ");

        childSmith.addIncome(transaction3);

        id.save(transaction3);
        /*family 2*/
        Income transaction4 = new Income(Money.euros(BigDecimal.valueOf(545)), true, date1, date1.plusDays(5), "Pay ");
        Expense expense2 = new Expense(Money.euros(BigDecimal.valueOf(345)), true, date2, date2.plusDays(5), "Rent");
        MrsMcGil.addIncome(transaction4);
        MrsMcGil.addExpense(expense2);

        id.save(transaction4);

        exp_d.save(expense2);
        Expense expense3 = new Expense(Money.euros(BigDecimal.valueOf(345)), false, date2, date2.plusDays(5), "Farmer's Market");
        MrsMcGil.addExpense(expense3);
        exp_d.save(expense3);
        //create a few piggy_banks
        Piggy_Bank_DAO pgd = getPiggyBankDAO();
        /* family 1 */

        Piggy_bank bank1= new Piggy_bank("First", "auto-down payment ", thousand);

        Piggy_bank bank2= new Piggy_bank("Second", "bike ", hundread);
        Piggy_bank bank3= new Piggy_bank("Third", "clothes", hundread);
        pgd.save(bank1);
        pgd.save(bank2);
        pgd.save(bank3);
        mrsSmith.addPiggy_bank(bank1);
        childSmith.addPiggy_bank(bank2);
        childSmith.addPiggy_bank(bank3);
        /* family 2 */
        Piggy_bank bank4= new Piggy_bank("Fourth", "clothes", hundread);
        pgd.save(bank4);
        MrsMcGil.addPiggy_bank(bank4);
        //add a few allocations to those piggy_banks
        Money_alloc_DAO mad= getMoneyAlloDAO();
        /* family 1 */
        Money_alloc m1 = new Money_alloc(fifty, date1);
        bank1.allocate_allocations(m1);
        Money_alloc m2 = new Money_alloc(fifty, date2);
        bank2.allocate_allocations(m2);
        Money_alloc m4 = new Money_alloc(hundread, date6);
        bank1.allocate_allocations(m4);

        mad.save(m1);
        mad.save(m2);
        mad.save(m4);
        /* family 2 */
        Money_alloc m3 = new Money_alloc(fifty, date5);
        bank2.allocate_allocations(m2);
        mad.save(m3);

    }
}
