package com.example.familybudget.DAO;

import com.example.familybudget.Dao.Expense_DAO;
import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Income_DAO;
import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.Dao.Money_alloc_DAO;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Expense_DAOMemory;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Income_DAOMemory;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.DaoMemory.Money_alloc_DAOMemory;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Piggy_bank;

import org.junit.*;

public class DAOTest {
    private Expense_DAO expd;
    private Family_Member_DAO fmd ;
    private Family_DAO fd;
    private Income_DAO incd;
    private Money_alloc_DAO mallocd;
    private Piggy_Bank_DAO bankd;
    private static final int NO_FOR_NEW_Families = 2;
    private static final int NO_members = 5;
    private static final int NO_Incomes = 4;
    private static final int ITEM_NO_expenses= 3;
    private static final int NO_Piggy_banks = 4;
    private static final int NO_allocations = 4;
    Initializer dataHelper;
    @Before
    public void setUp() {
        dataHelper = new Memory_Initializer();
        dataHelper.prepareDemoData();

        expd= new Expense_DAOMemory();
        fmd = new Family_Member_DAOMemory();
        fd= new Family_DAOMemory();
        incd= new Income_DAOMemory();
        mallocd = new Money_alloc_DAOMemory();
        bankd= new Piggy_bank_DAOMemory();


    }
    /*
    * Ensures that erase data works as it should as memory daos have static lists of domain objects
    * */
    @Test
    public void Find_NON_ExistingFamilies(){
        Assert.assertNull(fd.find(3));
        Assert.assertNull(fmd.find(8));
        Assert.assertNull(incd.find(5));
        Assert.assertNull(expd.find(4));

        Assert.assertNull(bankd.find(6));
        Assert.assertNull(mallocd.find(6));
    }
    @Test
    public void NumberOfExistingItemsPerDao(){

        Assert.assertEquals(NO_FOR_NEW_Families, fd.findAll().size());
        Assert.assertEquals(NO_members, fmd.findAll().size());
        Assert.assertEquals(NO_Incomes, incd.findAll().size());
        Assert.assertEquals(ITEM_NO_expenses, expd.findAll().size());
        Assert.assertEquals(NO_Piggy_banks, bankd.findAll().size());
        Assert.assertEquals(NO_allocations, mallocd.findAll().size());

    }
    @Test
    public void FindExistingFamilies(){
        Assert.assertNotNull(fd.find(2));
        Assert.assertNotNull(fmd.find(2));
       Assert.assertNotNull(incd.find(2));
        Assert.assertNotNull(expd.find(2));
        Assert.assertNotNull(bankd.find(2));
        Assert.assertNotNull(mallocd.find(2));
    }


    @Test
    public void find_instances_from_Daos_ByName(){
        Assert.assertEquals("First", bankd.findbyName("First").getName());
        Assert.assertNull( bankd.findbyName("awsd"));
        Assert.assertNotNull( fd.findByName("Smiths"));
        Assert.assertNotNull(fmd.findByName("Liz"));

    }
    @Test
    public void member_count(){
        Assert.assertEquals(3, fd.find(1).getMembers().size());
        Assert.assertEquals(2, fd.find(2).getMembers().size());
    }
    @Test
    public void protectors_set(){
        Assert.assertEquals(fmd.find(1), fd.find(1).getProtector());
        Assert.assertEquals(fmd.find(4), fd.find(2).getProtector());


    }


}