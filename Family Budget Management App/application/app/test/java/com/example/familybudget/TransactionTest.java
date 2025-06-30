package com.example.familybudget;

import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class TransactionTest {

    @Test (expected = IllegalArgumentException.class)
    public void testConstructor() {
        Transactions transaction = new Transactions(Money.euros(BigDecimal.valueOf(545)), false, LocalDate.now(), LocalDate.now().minusDays(5), "deposit 1");
    }
    @Test (expected = IllegalArgumentException.class)
    public void testSetMoney() {
        Transactions transaction = new Transactions();
        transaction.setMoney(Money.euros(BigDecimal.valueOf(545)));
        Assert.assertEquals(BigDecimal.valueOf(545), transaction.getMoney().getAmount());
        transaction.setMoney(Money.euros(BigDecimal.valueOf(-545)));
        Assert.assertNotEquals(BigDecimal.valueOf(545), transaction.getMoney().getAmount());
    }

    @Test
    public void testSetIsRecurring() {
        Transactions transaction = new Transactions();
        transaction.setIsRecurring(true);
        Assert.assertTrue(transaction.getIsRecurring());
        transaction.setIsRecurring(false);
        Assert.assertFalse(transaction.getIsRecurring());
    }

    @Test
    public void testSetDescription() {
        Transactions transaction = new Transactions();
        transaction.setDescription("withdrawal 1");
        Assert.assertEquals("withdrawal 1", transaction.getDescription());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetEndDate() {
        Transactions transaction = new Transactions();
        transaction.setRegistrationDate(LocalDate.now());
        transaction.setEndDate(LocalDate.now().minusDays(5), transaction.getRegistrationDate());
    }

    @Test
    public void testSetRegistrationDate() {
        Transactions transaction = new Transactions();
        LocalDate customDate = LocalDate.of(2023, 12, 3);
        transaction.setRegistrationDate(customDate);
        Assert.assertNotEquals(LocalDate.now(), transaction.getRegistrationDate());
    }
    @Test
    public void testGetEffectiveRange() {
        Transactions transaction = new Transactions();
        LocalDate registrationDate = LocalDate.now();
        transaction.setRegistrationDate(registrationDate);
        LocalDate endDate = registrationDate.plusDays(5);
        transaction.setEndDate(endDate, registrationDate);
        Assert.assertEquals("5", transaction.getEffectiveRange());

    }
    @Test
    public void testEquals() {
        Transactions transaction1 = new Transactions(Money.euros(BigDecimal.valueOf(545)), true, LocalDate.now(), LocalDate.now().plusDays(5), "withdrawal 1");
        Transactions transaction2 = new Transactions(Money.euros(BigDecimal.valueOf(545)), true, LocalDate.now(), LocalDate.now().plusDays(5), "withdrawal 1");
        Transactions transaction3 = new Transactions(Money.euros(BigDecimal.valueOf(544)), false, LocalDate.now(), LocalDate.now().plusDays(10), "deposit 1");
        Assert.assertTrue(transaction1.equals(transaction2));
        Assert.assertFalse(transaction1.equals(transaction3));
    }
}
