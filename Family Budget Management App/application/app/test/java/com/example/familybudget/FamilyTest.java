package com.example.familybudget;

import java.time.LocalDateTime;
import static org.junit.Assert.*;

import androidx.annotation.TransitionRes;

import java.util.Currency;
import java.math.BigDecimal;
import org.junit.*;

import java.util.HashSet;
import java.util.Set;

import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;

public class FamilyTest{
    Family fam;
    Family_Member fam_protector;
    LocalDateTime date_now;
    Set<Family_Member> members = new HashSet<>();
    Money zero, hunnit;

    @Before
    public void setup(){
        fam_protector = new Family_Member("tony", "tony1", "1234", true);
        fam = new Family("Smiths", fam_protector);
        date_now = LocalDateTime.now();
        zero = new Money();
        hunnit = new Money(BigDecimal.valueOf(100), Currency.getInstance("EUR"));
    }

    @Test
    public void getprotector(){
        assertEquals(fam_protector, fam.getProtector());
    }

    @Test
    public void setprotector(){
        Family_Member dude = new Family_Member("john", "john1", "123", true);
        fam.setProtector(dude);
        assertEquals(dude, fam.getProtector());
    }

    @Test (expected = NotProtector.class)
    public void setprotectorExc(){
        Family_Member newMember = new Family_Member();
        newMember.setIs_protector(false);
        fam.setProtector(newMember);
    }

    @Test
    public void getname(){
        assertEquals("Smiths", fam.getFamilyName());
    }

    @Test
    public void setname(){
        fam.setFamilyName("Blacks");
        assertNotEquals("Smiths", fam.getProtector());
    }



    @Test
    public void setregdate(){
        fam.setRegisterDate(date_now);

    }

    @Test
    public void getdisincome(){
        assertEquals(zero, fam.getDisposableIncome());
    }

    @Test
    public void setdisincome(){
        fam.setDisposableIncome(hunnit);
        assertNotEquals(zero, fam.getDisposableIncome());
    }

    @Test
    public void gettotincome(){
        assertEquals(zero, fam.getTotalIncome());
    }

    @Test
    public void settotincome(){
        fam.setTotalIncome(hunnit);
        assertNotEquals(zero, fam.getTotalIncome());
    } 

    @Test
    public void gettotexp(){
        assertEquals(zero, fam.getTotalExpenses());
    }

    @Test
    public void settotexp(){
        fam.setTotalIncome(hunnit);
        assertNotEquals(0, fam.getTotalExpenses());
    }

    @Test
    public void gettotsavings(){
        assertEquals(zero, fam.getTotalSavings());
    }

    @Test
    public void settotsavings(){
        fam.setTotalSavings(hunnit);
        assertNotEquals(zero, fam.getTotalSavings());
    }

    @Test
    public void addfamilymember(){
        Family_Member newMember = new Family_Member();
        fam.addFamilyMember(newMember);
        assertTrue(fam.getMembers().contains(newMember));
    }

    @Test (expected = NotAMemberOfThisFamily.class)
    public void removefamilymemberExc(){
        Family_Member newMember = new Family_Member();
        fam.removeFamilyMember(newMember);
    }

    @Test
    public void removefamilymember(){
        Family_Member newMember = new Family_Member();
        fam.addFamilyMember(newMember);
        fam.removeFamilyMember(newMember);
        assertFalse(fam.getMembers().contains(newMember));
    }

    @Test
    public void settotalexpences(){
        fam.setTotalExpenses(new Money());
        assertEquals(BigDecimal.valueOf(0),  fam.getTotalExpenses().getAmount());
        assertEquals(Currency.getInstance("EUR"), fam.getTotalExpenses().getCurrency());
    }

    @Test (expected = IllegalArgumentException.class)
    public void setdispincomeExc(){
        fam.setDisposableIncome(new Money(BigDecimal.valueOf(-123), Currency.getInstance("EUR")));
    }

    @Test (expected = NotProtector.class)
    public void testconstruct(){
        Family_Member newMember = new Family_Member();
        newMember.setIs_protector(false);
        Family newFamily = new Family("Smiths", newMember);
    }

}