package com.example.familybudget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.Objects;

public class Piggy_bank{

    private String name;
    private String description;
    private Money Initial_goal;

    private Money allocated_amount;
    private int uid;

    private List<Money_alloc> allocations = new ArrayList<>();
    public Family_Member owner ;

    public void setUid(int a ){this.uid= a; }
    public int getId(){return this.uid;}
    public Piggy_bank(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String a){ this.description=a;}
    public String getDescription(){return this.description;}

    public void setInitial_goal(Money initial_goal) {
        BigDecimal dmoney= initial_goal.getAmount();
        if(dmoney.compareTo(BigDecimal.ZERO) >= 0) {
            Initial_goal = initial_goal;
        }else{throw new Piggy_bankException();}
    }
    public Money getInitial_goal() {
        return this.Initial_goal;
    }

    public Piggy_bank(String name){
        this.name= name;
        this.allocated_amount= new Money();

    }
    public Piggy_bank(String name, String description, Money initial_goal)
    {
        this.name=name;
        this.description= description;
        this.Initial_goal= initial_goal;
        this.allocated_amount= new Money();

    }
    public Piggy_bank(String name, String description, Money initial_goal, int uid)
    {
        this.name=name;
        this.description= description;
        this.Initial_goal= initial_goal;
        this.allocated_amount= new Money();
        this.uid= uid;
    }
    public Money get_remaining_amount(){
        return Initial_goal.minus(allocated_amount);
    }
    public Money getAllocated_amount(){return this.allocated_amount;}
    public void set_allocated_amount(Money m ){
        this.allocated_amount=m;
    }
    public void allocate_money(Money m){
        BigDecimal m1= m.getAmount();
        BigDecimal m2= this.get_remaining_amount().getAmount();
        /*m1<=m2 allocation less than remaining amount top reach the goal*/
        if(m1.compareTo(m2)<=0)
        {
            Money_alloc alloc = new Money_alloc(m);
            allocations.add(alloc);
            allocated_amount = allocated_amount.plus(m);
        }
        else{throw new Piggy_bankException();}
    }
    public void remove_allocations(Money_alloc malloc ){
        Money m = malloc.getAmount();
        BigDecimal m_amount= m.getAmount();
        BigDecimal aloc_amount= this.getAllocated_amount().getAmount();
        if(this.allocations.size()>0 && aloc_amount.compareTo(m_amount)>=0) {
            this.allocations.remove(malloc);
            this.allocated_amount= this.allocated_amount.minus(malloc.getAmount());
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    public void remove_money(Money m){

        BigDecimal m_amount= m.getAmount();
        BigDecimal aloc_amount= this.getAllocated_amount().getAmount();

        if(allocations.size()>0 && aloc_amount.compareTo(m_amount)>=0)
        {
            Money zero = new Money(new BigDecimal(0), Currency.getInstance("EUR"));
            Money negative_money = zero.minus(m);
            Money_alloc negative_alloc = new Money_alloc(negative_money);
            this.allocations.add(negative_alloc);
            this.allocated_amount= this.allocated_amount.plus(negative_money);

        }
        else{
            throw new Piggy_bankException();
        }

    }
    public Money withdraw_all(){
        if(this.get_remaining_amount().getAmount().compareTo(BigDecimal.ZERO)==0){
            Money m= this.getAllocated_amount();
            this.set_allocated_amount(new Money());
            return m;
        }
        return new Money();
    }
    public void withdraw(Money m){
        if(this.getAllocated_amount().getAmount().compareTo(m.getAmount())>=0){

            this.set_allocated_amount(this.getAllocated_amount().minus(m));
            return;
        }
        throw new Piggy_bankException();

    }
    public List<Money_alloc> get_allocations(){
        return new ArrayList<Money_alloc>(this.allocations);

    }
    public Family_Member get_owner(){
        return this.owner;
    }
    public void set_owner(Family_Member f){this.owner=f;}

    public void setAllocations(List<Money_alloc> allocations) {
        this.allocations = new ArrayList<Money_alloc>(allocations);
    }
    public List<Money_alloc> get_allocation_for(LocalDate start, LocalDate end){
        List<Money_alloc> allocations_selected= new ArrayList<>();
        for (Money_alloc money : this.allocations) {
            LocalDate transactionDate = money.getDate();
            if (start.isBefore(end)) {
                if (transactionDate.isEqual(start) || (transactionDate.isAfter(start) && transactionDate.isBefore(end))) {
                    allocations_selected.add(money);
                }
            }
        }
        return allocations_selected;
    }
    public void allocate_allocations(Money_alloc malloc){
        BigDecimal m1= malloc.getAmount().getAmount();
        BigDecimal m2= this.get_remaining_amount().getAmount();
        if(m1.compareTo(m2)<=0)
        {
            //Money_alloc alloc = new Money_alloc(m);
            allocations.add(malloc);
            allocated_amount = allocated_amount.plus(malloc.getAmount());
        }

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piggy_bank piggyBank = (Piggy_bank) o;
        return name.equals(piggyBank.name) && description.equals(piggyBank.description) && Initial_goal.equals(piggyBank.Initial_goal) && uid==piggyBank.uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, Initial_goal, allocations);
    }


}