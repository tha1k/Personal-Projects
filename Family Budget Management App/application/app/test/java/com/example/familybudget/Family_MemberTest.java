package com.example.familybudget;
import android.graphics.ColorSpace;

import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;

public class Family_MemberTest {
    Currency c = Currency.getInstance("EUR");
    Family_Member protector;
    Family_Member child;
    Piggy_bank pg;
    Income income;
    Expense expense;
    BigDecimal bigDecimal100 = BigDecimal.valueOf(100);

    @Before
    public void setup() {
        protector = new Family_Member("Dad", "Smith", "pass", true);
        child = new Family_Member("John", "jay", "pass1", false);
    }

    @Test
    public void gets_correct_name() {
        Assert.assertEquals("John", child.getName());
    }

    @Test
    public void sets_correct_name() {
        child.setName("Johnathan");
        Assert.assertEquals("Johnathan", child.getName());
    }

    @Test
    public void addPiggybank_succesfully() {
        pg = new Piggy_bank();
        protector.addPiggy_bank(pg);
        Assert.assertEquals(1, protector.getPiggy_banks().size());
        Piggy_bank pg2 = new Piggy_bank();
        protector.addPiggy_bank(pg);
//same piggy bank cant be added twice
        Assert.assertEquals(1, protector.getPiggy_banks().size());
        Assert.assertEquals(protector, pg.get_owner());
    }

    @Test
    public void removePiggybank_succesfully() {
        pg = new Piggy_bank("a");
        protector.addPiggy_bank(pg);
        Assert.assertEquals(1, protector.getPiggy_banks().size());

        protector.remove_bank(pg);
        Assert.assertEquals(0, protector.getPiggy_banks().size());
    }

    @Test(expected = NullPointerException.class)
    public void addPiggybank_unsuccesfully() {
        pg = null;
        protector.addPiggy_bank(pg);

    }

    @Test(expected = Family_MemberException.class)
    public void removePiggybank_unsuccesfully() {
        pg = new Piggy_bank("a");
        protector.addPiggy_bank(pg);
        Assert.assertEquals(1, protector.getPiggy_banks().size());

        child.remove_bank(pg);

    }

    @Test(expected = Family_MemberException.class)
    public void NotProtectorPrivelegeMember_AttemptedAllocation() {
        child.allocate_money(new Money(bigDecimal100, c), pg);

    }

    @Test(expected = Family_MemberException.class)
    public void NotProtectorPrivelegeMember_AttemptedRevertAllocation() {
        child.revert_allocate_money(new Money(bigDecimal100, c), pg);

    }

    @Test
    public void getZeroSavings() {
        Assert.assertEquals(new Money(), child.get_member_savings());
    }

    @Test
    public void a() {
        LocalDate A = LocalDate.now();
    }

    @Test
    public void withdrawfrompbnull() {
        Piggy_bank newPB = new Piggy_bank("Toy", "Car", Money.euros(BigDecimal.valueOf(545)));
        newPB.allocate_money(Money.euros(BigDecimal.valueOf(530)));
        child.addPiggy_bank(newPB);
        child.withdraw_from_piggy_bank(newPB, Money.euros(BigDecimal.valueOf(520)));
        Assert.assertEquals(BigDecimal.valueOf(10), newPB.getAllocated_amount().getAmount());
    }

    @Test
    public void getpass() {
        child.setPassword("newpass123");
        Assert.assertEquals("newpass123", child.getPassword());
    }

    @Test
    public void getuname() {
        child.setUsername("Not a dad");
        Assert.assertEquals("Not a dad", child.getUsername());
    }
    @Test (expected = RuntimeException.class)
    public void addincome(){
        Income newIncome = new Income(Money.euros(BigDecimal.valueOf(0)), false, LocalDate.now(), LocalDate.now().plusDays(5), "payday");
        protector.addIncome(newIncome);
        Assert.assertTrue(protector.getIncome().contains(newIncome));
        Assert.assertEquals(newIncome.getMoney(), protector.getTotalIncome());
        Assert.assertEquals(newIncome.getMoney(), protector.getDisposableIncome());
        protector.removeIncome(newIncome);
        Assert.assertFalse(protector.getIncome().contains(newIncome));
        Assert.assertEquals(BigDecimal.ZERO, protector.getTotalIncome().getAmount());
        Assert.assertEquals(BigDecimal.ZERO, protector.getDisposableIncome().getAmount());
        Income income = new Income(Money.euros(BigDecimal.valueOf(200)), false, LocalDate.now(), LocalDate.now().plusDays(5), "Salary");
        protector.addIncome(income);
        protector.retractFromDisposableIncome(Money.euros(BigDecimal.valueOf(300)));
    }

    @Test
    public void addexpense(){
        Expense newExpense = new Expense(Money.euros(BigDecimal.valueOf(454)), false, LocalDate.now(), LocalDate.now().plusDays(5), "vacation");
        protector.addExpense(newExpense);
        Assert.assertTrue(protector.getExpenses().contains(newExpense));
        Assert.assertEquals(newExpense.getMoney(), protector.getTotalExpenses());
    }

    @Test
    public void setdisposable(){
        Family_Member newMember = new Family_Member();
        newMember.setDisposableIncome(Money.euros(BigDecimal.valueOf(1233)));
        Assert.assertEquals(BigDecimal.valueOf(1233), newMember.getDisposableIncome().getAmount());
        newMember.addToDisposableIncome(Money.euros(BigDecimal.valueOf(1233)));
        Assert.assertEquals(BigDecimal.valueOf(2466), newMember.getDisposableIncome().getAmount());
    }

    @Test
    public void retractFromDisposableIncome() {
        protector.setDisposableIncome(Money.euros(BigDecimal.valueOf(1000)));
        protector.retractFromDisposableIncome(Money.euros(BigDecimal.valueOf(200)));

        Assert.assertEquals(BigDecimal.valueOf(800), protector.getDisposableIncome().getAmount());
    }
    @Test
    public void equalsAndHashCode() {
        Family_Member member1 = new Family_Member("John", "john123", "pass123", false);
        Family_Member member2 = new Family_Member("John", "john123", "pass123", false);

        Assert.assertTrue(member1.equals(member2) && member2.equals(member1));
        Assert.assertEquals(member1.hashCode(), member2.hashCode());
    }
    @Test
    public void addAndRemoveMultipleExpenses() {
        Expense expense1 = new Expense(Money.euros(BigDecimal.valueOf(50)), false, LocalDate.now(), LocalDate.now().plusDays(5), "Groceries");
        Expense expense2 = new Expense(Money.euros(BigDecimal.valueOf(30)), false, LocalDate.now(), LocalDate.now().plusDays(5), "Utilities");
        protector.addExpense(expense1);
        protector.addExpense(expense2);
        Assert.assertEquals(2, protector.getExpenses().size());
        protector.removeExpense(expense1);
        Assert.assertEquals(1, protector.getExpenses().size());
    }
    @Test
    public void get_filtered_members_allocations(){
        Money thousand= new Money(new BigDecimal(1000), Currency.getInstance("EUR"));
        Piggy_bank p= new Piggy_bank("First", " ", thousand);
        Money_alloc money1 = new Money_alloc(new Money(new BigDecimal("100"),Currency.getInstance("EUR")), LocalDate.of(2023, 1, 15));
        Money_alloc money2 = new Money_alloc(new Money(new BigDecimal("200"),Currency.getInstance("EUR")), LocalDate.of(2023, 3, 10));
        Money_alloc money3 = new Money_alloc(new Money(new BigDecimal("150"),Currency.getInstance("EUR")), LocalDate.of(2023, 6, 5));
        Money_alloc money4 = new Money_alloc(new Money(new BigDecimal("300"),Currency.getInstance("EUR")), LocalDate.of(2023, 8, 20));
        p.allocate_allocations(money1);

        p.allocate_allocations(money2);

        p.allocate_allocations(money3);

        p.allocate_allocations(money4);
        Piggy_bank p2= new Piggy_bank("Second", " ", thousand);
        p2.allocate_allocations(money1);

        p2.allocate_allocations(money2);

        p2.allocate_allocations(money3);

        p2.allocate_allocations(money4);
        child.addPiggy_bank(p);
        child.addPiggy_bank(p2);
        LocalDate startDate = LocalDate.of(2023, 3, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 31);
        Assert.assertEquals(4, child.get_members_allocs(startDate,endDate).size());
    }
    @Test
    public void get_ALL_AllRecring_expenses(){
        LocalDate d = LocalDate.of(2023, 1, 15);
        LocalDate d1 = LocalDate.of(2023, 3, 10);
        LocalDate d2= LocalDate.of(2023, 6, 5);
        Expense expense0 = new Expense(Money.euros(BigDecimal.valueOf(0)), false, d, d.plusDays(5), "payday");
        Expense expense1 = new Expense(Money.euros(BigDecimal.valueOf(200)), true, d1, d1.plusDays(5), "Salary");
        Expense expense2 = new Expense(Money.euros(BigDecimal.valueOf(0)), false, d2, d2.plusDays(5), "payday");
        child.addExpense(expense0);
        child.addExpense(expense1);
        child.addExpense(expense2);
        Assert.assertEquals(1, child.select_recurring_expenses(d1,d1.plusDays(10)).size());
        Assert.assertEquals(0, child.select_recurring_expenses(d1.plusDays(24),d1.plusDays(30)).size());
        Assert.assertEquals(2, child.select_expenses(d1,d1.plusDays(365)).size());

    }
    @Test
    public void get_ALL_AllRecring_incomes(){
        LocalDate d = LocalDate.of(2023, 1, 15);
        LocalDate d1 = LocalDate.of(2023, 3, 10);
        LocalDate d2= LocalDate.of(2023, 6, 5);
        Income Income0 = new Income(Money.euros(BigDecimal.valueOf(0)), false, d, d.plusDays(5), "payday");
        Income Income1 = new Income(Money.euros(BigDecimal.valueOf(200)), true, d1, d1.plusDays(5), "Salary");
        Income Income2 = new Income(Money.euros(BigDecimal.valueOf(0)), false, d2, d2.plusDays(5), "payday");
        child.addIncome(Income0);
        child.addIncome(Income1);
        child.addIncome(Income2);
        Assert.assertEquals(1, child.select_recurring_income(d1,d1.plusDays(10)).size());
        Assert.assertEquals(0, child.select_recurring_income(d1.plusDays(24),d1.plusDays(30)).size());
        Assert.assertEquals(2, child.select_incomes(d1,d1.plusDays(365)).size());
    }
    @Test
    public void find_currently_activeIncomes(){
        Assert.assertEquals(0, protector.findAll_currently_activeIncomes().size());
        LocalDate d = LocalDate.of(2023, 1, 15);
        LocalDate d1 = LocalDate.of(2023, 3, 10);
        LocalDate d2= LocalDate.of(2023, 12, 25);
        Income expense0 = new Income(Money.euros(BigDecimal.valueOf(0)), false, d, d.plusDays(5), "payday");
        Income expense1 = new Income(Money.euros(BigDecimal.valueOf(200)), true, d1, d1.plusDays(5), "Salary");
        Income expense2 = new Income(Money.euros(BigDecimal.valueOf(0)), true, d2, d2.plusDays(100), "payday");
        protector.addIncome(expense0);
        protector.addIncome(expense1);
        protector.addIncome(expense2);
        Assert.assertEquals(1, protector.findAll_currently_activeIncomes().size());
    }
    @Test
    public void find_currently_activeEXpenses(){
        LocalDate d = LocalDate.of(2023, 1, 15);
        LocalDate d1 = LocalDate.of(2023, 3, 10);
        LocalDate d2= LocalDate.of(2023, 12, 25);
        Expense expense0 = new Expense(Money.euros(BigDecimal.valueOf(0)), false, d, d.plusDays(5), "payday");
        Expense expense1 = new Expense(Money.euros(BigDecimal.valueOf(200)), true, d1, d1.plusDays(5), "Salary");
        Expense expense2 = new Expense(Money.euros(BigDecimal.valueOf(0)), true, d2, d2.plusDays(100), "payday");
        protector.addExpense(expense0);
        protector.addExpense(expense1);
        protector.addExpense(expense2);
        Assert.assertEquals(1, protector.findAll_currently_activeExpense().size());

    }
}

