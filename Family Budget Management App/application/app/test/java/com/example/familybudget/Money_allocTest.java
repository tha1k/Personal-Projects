package com.example.familybudget;

import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Money_allocTest {
    Money zero ;
    Money lt_zero;
    Money hundred;

    @Before
    public void setup(){
        zero = new Money();
        lt_zero = new Money(new BigDecimal("-100000"), Currency.getInstance("EUR"));
        hundred = new Money(new BigDecimal("100"), Currency.getInstance("EUR"));

    }
    @Test
    public void getdate(){
        Money_alloc m = new Money_alloc(hundred, LocalDate.now());
        Assert.assertEquals(m.getDate(), LocalDate.now());
    }
    @Test
    public void getamount(){
        Money_alloc m = new Money_alloc(hundred, LocalDate.now());
        Assert.assertEquals(m.getAmount(), hundred);
    }
    @Test
    public void setamount(){
        Money_alloc m = new Money_alloc(hundred, LocalDate.now());
        Assert.assertEquals(m.getDate(), LocalDate.now());
        m.setAmount(hundred.plus(hundred));
        Assert.assertNotEquals(m.getAmount(), hundred);
    }




    @Test
    public void check_equallity(){
        LocalDate d = LocalDate.parse("2023-11-20");
        Money_alloc m = new Money_alloc(hundred,LocalDate.now());
        Money_alloc m2 = new Money_alloc(hundred, d);

        Assert.assertTrue(m.equals(m2));
        m2.setDate(d);
        Assert.assertTrue(m.equals(m2));
    }
}
