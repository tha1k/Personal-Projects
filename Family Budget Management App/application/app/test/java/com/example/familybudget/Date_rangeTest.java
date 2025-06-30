package com.example.familybudget;

import org.junit.*;

import java.time.LocalDate;

public class Date_rangeTest {
    LocalDate date1;
    LocalDate date2;
    Date_range d ;
    @Before
    public void setup(){
        LocalDate date1 = LocalDate.of(2023, 12, 2); // December 2, 2023
        LocalDate date2 = LocalDate.of(2023, 12, 9);
        d= new Date_range(date1, date2);
    }
    @Test
    public void correct_getters(){
        LocalDate d1= d.getStartDate();
        LocalDate d2= d.getEndDate();
        Date_range daterangetest= new Date_range(d1, d2);
        Assert.assertEquals(d,daterangetest);
        //Assert.assertEquals(d2, date2);


    }
    @Test
    public void Correct_dayDiff_start_end(){
        Assert.assertEquals(7, d.totalDaysUntil(),0.1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void Incorrect_init(){
        Date_range d2= new Date_range(date2,null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void Incorrect_init_notNULL(){
        Date_range d2= new Date_range(date2,date1);

    }


}
