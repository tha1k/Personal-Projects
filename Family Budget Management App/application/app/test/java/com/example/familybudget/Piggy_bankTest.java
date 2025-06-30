package com.example.familybudget;

import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public class Piggy_bankTest {
    Family_Member protector;
    LocalDate d= LocalDate.parse("2023-11-20");
    Money hundo;
    Money fifty;
    Money thousand;
    Money_alloc m1;
    Money_alloc m2;
    Piggy_bank p;
    Money zero;
    @Before
    public void setup() {
        protector = new Family_Member("Dad", "Smith", "pass", true);
        hundo = new Money(new BigDecimal(100), Currency.getInstance("EUR"));
        m1 = new Money_alloc(hundo, d);
        fifty = new Money(new BigDecimal(50), Currency.getInstance("EUR"));
        m2 = new Money_alloc(fifty, d);
        thousand = new Money(new BigDecimal(1000), Currency.getInstance("EUR"));
        p= new Piggy_bank("First", " ", thousand);
        zero = new Money(new BigDecimal(0), Currency.getInstance("EUR"));


    }
    @Test
    public void set_get_owner(){
        p.set_owner(protector);
        Assert.assertEquals(protector, p.get_owner());
        Family_Member person = p.get_owner();
        person= new Family_Member(" ", "Smith", "pass", false);
        Assert.assertEquals(protector, p.get_owner());
    }
    @Test
    public void get_allocated_amount(){
        Assert.assertEquals(new Money(), p.getAllocated_amount());
    }
    @Test
    public void get_remaining_amount(){
        Assert.assertEquals(thousand, p.get_remaining_amount());
        Assert.assertEquals(0, p.get_allocations().size());
    }
    @Test (expected = Piggy_bankException.class)
    public void remove_from_empty_piggyBank(){
        p.remove_money(fifty);
    }

    @Test
    public void add_Money(){
        p.allocate_money(fifty);
        Assert.assertEquals(1,p.get_allocations().size());
        Money nine_fifty= new Money(new BigDecimal(950), Currency.getInstance("EUR"));
        Assert.assertEquals(nine_fifty, p.get_remaining_amount());
    }
    @Test
    public void mistake_allocating_remove_75(){
        p.allocate_money(fifty);
        p.allocate_money(hundo);
        Money eightFifty = new Money(new BigDecimal(850), Currency.getInstance("EUR"));
        Assert.assertEquals(eightFifty, p.get_remaining_amount());
        Assert.assertEquals(fifty.plus(hundo), p.getAllocated_amount());
        Assert.assertEquals(2, p.get_allocations().size());
        Money seventyFive = new Money(new BigDecimal(75), Currency.getInstance("EUR"));
        p.remove_money(seventyFive);
        Assert.assertEquals(3,p.get_allocations().size());
        Assert.assertEquals(seventyFive, p.getAllocated_amount());

    }
    @Test //correct
    public void mistake_remove_greater_than_allocated(){
        p.allocate_money(fifty);
        p.allocate_money(hundo);
        Money eightFifty = new Money(new BigDecimal(850), Currency.getInstance("EUR"));
        Assert.assertEquals(eightFifty, p.get_remaining_amount());
        Assert.assertEquals(fifty.plus(hundo), p.getAllocated_amount());
        Assert.assertEquals(2, p.get_allocations().size());
        p.remove_money(fifty.plus(hundo));
        Assert.assertEquals(3, p.get_allocations().size());
        Assert.assertEquals(zero, p.getAllocated_amount());


    }
    @Test
    public void goal_reached_Withdraw(){
        Money eightFifty = new Money(new BigDecimal(850), Currency.getInstance("EUR"));
        p.allocate_money(eightFifty);
        p.allocate_money(fifty);
        p.allocate_money(hundo);
        Assert.assertEquals(p.withdraw_all(),thousand);
    }
    @Test(expected = Piggy_bankException.class)
    public void mistake_allocating_remove_MoreThanAnyAlloc(){
        p.allocate_money(fifty);
        p.allocate_money(hundo);
        Money eightFifty = new Money(new BigDecimal(850), Currency.getInstance("EUR"));
        Assert.assertEquals(eightFifty, p.get_remaining_amount());
        Assert.assertEquals(fifty.plus(hundo), p.getAllocated_amount());
        Assert.assertEquals(2, p.get_allocations().size());
        p.remove_money(fifty.plus(hundo));
        Assert.assertEquals(3, p.get_allocations().size());
        Assert.assertEquals(zero, p.getAllocated_amount());
        p.remove_money(fifty);

    }
    @Test
    public void withdraw_partial(){
        p.allocate_money(hundo);
        Money m1= new Money(new BigDecimal(5), Currency.getInstance("EUR"));
        p.withdraw(m1);
        Money forty_Five=new Money(new BigDecimal(95), Currency.getInstance("EUR"));
        Assert.assertEquals(forty_Five, p.getAllocated_amount());
    }
    @Test(expected = Piggy_bankException.class)
    public void withdraw_partial_exceeds_allocated(){
        p.withdraw(fifty);
    }
    @Test(expected = Piggy_bankException.class)
    public void allocation_exceeds_goal(){
        p.allocate_money(thousand.plus(hundo));
    }

    @Test
    public void name_getter_setter(){
        p.setName("Changed");
        Assert.assertEquals(p.getName(),"Changed");
    }
    @Test
    public void setInit_goal(){
        p.setInitial_goal(hundo);
        Assert.assertEquals(hundo, p.getInitial_goal());
    }
    @Test(expected = Piggy_bankException.class)
    public void Illegal_negative_initial_goal(){

        p.setInitial_goal(fifty.minus(hundo));
    }
    @Test
    public void get_allocations(){
        List<Money_alloc> m= p.get_allocations();
        m.add(m1);
        Assert.assertEquals(0, p.get_allocations().size());
    }
    @Test
    public void set_allocations(){
        List<Money_alloc> m= p.get_allocations();
        m.add(m1);
        m.add(m2);
        p.setAllocations(m);
        Assert.assertEquals(m.size(), p.get_allocations().size());
        m.add(new Money_alloc(hundo));
        Assert.assertNotEquals(m.size(), p.get_allocations().size());

    }
    @Test
    public void equals(){
        Assert.assertTrue(p.equals(p));
        Assert.assertFalse(p.equals(m1));
        Piggy_bank p1= new Piggy_bank("name","desc", hundo);
        Assert.assertFalse(p.equals(p1));
    }
    @Test
    public void desc_set_get(){
        p.setDescription("wow");
        Assert.assertEquals("wow", p.getDescription());

    }
    @Test
    public void get_filtered_allocations(){
        Assert.assertEquals(0, p.get_allocations().size());
        Money_alloc money1 = new Money_alloc(new Money(new BigDecimal("100"),Currency.getInstance("EUR")), LocalDate.of(2023, 1, 15));
        Money_alloc money2 = new Money_alloc(new Money(new BigDecimal("200"),Currency.getInstance("EUR")), LocalDate.of(2023, 3, 10));
        Money_alloc money3 = new Money_alloc(new Money(new BigDecimal("150"),Currency.getInstance("EUR")), LocalDate.of(2023, 6, 5));
        Money_alloc money4 = new Money_alloc(new Money(new BigDecimal("300"),Currency.getInstance("EUR")), LocalDate.of(2023, 8, 20));
        p.allocate_allocations(money1);

        p.allocate_allocations(money2);

        p.allocate_allocations(money3);

        p.allocate_allocations(money4);

        Assert.assertEquals(4, p.get_allocations().size());
        LocalDate startDate = LocalDate.of(2023, 3, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 31);
        //only 2 money_allocations_between those dates
        Assert.assertEquals(2, p.get_allocation_for(startDate,endDate).size());
    }
    @Test
    public void remove_specific_allocation(){
        Money_alloc money4 = new Money_alloc(new Money(new BigDecimal("300"),Currency.getInstance("EUR")), LocalDate.of(2023, 8, 20));

        p.allocate_allocations(money4);
        p.remove_allocations( new Money_alloc(new Money(new BigDecimal("300"),Currency.getInstance("EUR"))));
        Assert.assertEquals(zero, p.getAllocated_amount());
        Assert.assertEquals(0, p.get_allocations().size());


    }
    @Test(expected = IndexOutOfBoundsException.class)

    public void remove_allacation_greater_than_existing(){
        Money_alloc money4 = new Money_alloc(new Money(new BigDecimal("300"),Currency.getInstance("EUR")), LocalDate.of(2023, 8, 20));

        p.allocate_allocations(money4);
        p.remove_allocations( new Money_alloc(new Money(new BigDecimal("310"),Currency.getInstance("EUR"))));



    }
}
