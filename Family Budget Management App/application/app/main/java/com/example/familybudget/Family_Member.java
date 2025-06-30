package com.example.familybudget;
import android.view.MotionEvent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
public class Family_Member {
    private String name;
    private String username;
    private String password;
    private Money disposable_income;
    public boolean is_protector;
    private Set<Piggy_bank> piggy_banks= new HashSet<Piggy_bank>();
    //private Set<Money_alloc> allocations= new HashSet<Money_alloc>();
    private Set<Income> member_income = new HashSet<>();
    private Set<Expense> member_expenses = new HashSet<>();
    private int uid ;
    public Family_Member(String name, String username, String password, boolean is_protector) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.is_protector = is_protector;
        this.disposable_income = new Money();
    }
    public Family_Member(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean Is_protector() {
        return is_protector;
    }

    public void setIs_protector(boolean is_protector) {
        this.is_protector = is_protector;
    }
    public Set<Piggy_bank> getPiggy_banks(){
        return piggy_banks;
    }
    public void addPiggy_bank(Piggy_bank piggy) {
        if (piggy!= null ) {
            this.piggy_banks.add(piggy);
            piggy.set_owner(this);


        }else {
            throw new NullPointerException();
        }
    }
    public void remove_bank(Piggy_bank piggy){
        if (piggy != null && piggy.get_owner().equals(this) )
        {
            this.piggy_banks.remove(piggy);
        }
        else{throw new Family_MemberException();}
    }
    public void allocate_money(Money amount , Piggy_bank pg){
        if(this.is_protector==false ){
            throw new Family_MemberException(); //maybe custom exception
        }
        if(pg==null){
            throw new RuntimeException();
        }
        pg.allocate_money(amount);
        this.disposable_income.minus(amount);
    }

    public void revert_allocate_money(Money amount , Piggy_bank pg){
        if(this.is_protector==false || pg==null){
            throw new Family_MemberException(); //maybe custom exception
        }
        pg.remove_money(amount);
        this.disposable_income = this.disposable_income.plus(amount);
    }
    public Money get_member_savings(){
        Money temp= new Money();
        for(Piggy_bank p: piggy_banks){
            temp= temp.plus(p.getAllocated_amount());
        }
        return temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Family_Member)) return false;
        Family_Member member = (Family_Member) o;
        return is_protector == member.is_protector && uid == member.uid && Objects.equals(name, member.name) && Objects.equals(username, member.username) && Objects.equals(password, member.password) && Objects.equals(disposable_income, member.disposable_income) && Objects.equals(piggy_banks, member.piggy_banks) && Objects.equals(member_income, member.member_income) && Objects.equals(member_expenses, member.member_expenses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, is_protector, piggy_banks, member_income, member_expenses);
    }

    public void addIncome(Income inc){
        inc.set_owner(this);
        this.member_income.add(inc);
        this.disposable_income = this.disposable_income.plus(inc.getMoney());
    }

    public void removeIncome(Income inc){
        if(!(member_income.contains(inc))){
            throw new NoSuchTransaction();
        }
        else{
            this.member_income.remove(inc);
        }
    }

    public Set<Income> getIncome(){
        //return new HashSet<Transactions>(this.member_income);
        return this.member_income;
    }

    public void addExpense(Expense exp){
        exp.set_owner(this);
        this.member_expenses.add(exp);
        this.disposable_income = this.disposable_income.minus(exp.getMoney());
    }

    public void removeExpense(Expense exp){
        if(!(member_expenses.contains(exp))){
            throw new NoSuchTransaction();
        }
        else{
            this.member_expenses.remove(exp);
            this.disposable_income = this.disposable_income.plus(exp.getMoney());
        }
    }
    
    public Set<Expense> getExpenses(){
        return new HashSet<Expense>(this.member_expenses);
    }

    public void withdraw_from_piggy_bank(Piggy_bank pb, Money amount){
        if(!(this.piggy_banks.contains(pb))){
            throw new NoSuchPiggyBank();
        }
        else{
            if(this.piggy_banks.contains(pb)){
                for(Piggy_bank piggy : this.piggy_banks){
                    if(pb.equals(piggy) && pb.get_owner() == this){
                        piggy.withdraw(amount);
                        this.disposable_income = this.disposable_income.plus(amount);
                    }
                }
            }
        }
    }

    public Money getDisposableIncome(){
        return this.disposable_income;
    }

    public void setDisposableIncome(Money m){
        this.disposable_income = m;
    }

    public Money getTotalIncome(){
        Money total = Money.euros(BigDecimal.ZERO);
        for(Income tr : this.member_income){
            total = total.plus(tr.getMoney());
        }
        return total;
    }

    public Money getTotalExpenses(){
        Money total = Money.euros(BigDecimal.ZERO);
        for(Expense tr : this.member_expenses){
            total = total.plus(tr.getMoney());
        }
        return total;
    }

    public void addToDisposableIncome(Money m){
        this.disposable_income = this.disposable_income.plus(m);
    }

    public void retractFromDisposableIncome(Money m){
        if(this.disposable_income.getAmount().compareTo(m.getAmount()) < 1){
            throw new RuntimeException();
        }
        this.disposable_income = this.disposable_income.minus(m);
    }

    public Set<Income> select_incomes(LocalDate start, LocalDate endDate) {
        Set<Income> temp= select_recurring_income(start, endDate);
        Set<Income> temp2= new HashSet<Income>();
        for (Income inc : member_income){

            LocalDate transactionDate= inc.getEndDate();
            LocalDate transactionDate_start= inc.effective.getStartDate();

            if (transactionDate.isEqual(start) || (transactionDate_start.isAfter(start) && (transactionDate.isBefore(endDate) || transactionDate.isEqual(endDate)))) {
                if(inc.getIsRecurring()==false){
                    temp2.add(inc);
                }
            }
        }
        temp.addAll(temp2);
        return temp;
    }
    public Set<Income> select_recurring_income(LocalDate start, LocalDate endDate){
        Set<Income> temp= new HashSet<Income>();
        for (Income inc : member_income){
            inc.getEffectiveRange();
            LocalDate transactionStartDate= inc.effective.getStartDate();
            LocalDate transactionEndDate= inc.effective.getEndDate();

            if (inc.getIsRecurring() &&
                    ((transactionStartDate.isEqual(start) || transactionStartDate.isAfter(start)) &&
                            (transactionEndDate.isBefore(endDate) || transactionEndDate.isEqual(endDate))))  {

                temp.add(inc);

            }
        }
        return temp;
    }
    public Set<Expense> select_expenses(LocalDate start, LocalDate endDate) {
        Set<Expense> temp= select_recurring_expenses(start, endDate);
        Set<Expense> temp2= new HashSet<Expense>();
        for (Expense exp : member_expenses){

            LocalDate transactionDate= exp.getEndDate();
            LocalDate transactionDate_start= exp.effective.getStartDate();

            if (transactionDate.isEqual(start) || (transactionDate_start.isAfter(start) && (transactionDate.isBefore(endDate) || transactionDate.isEqual(endDate)))) {
                if(exp.getIsRecurring()==false){
                    temp2.add(exp);
                }
            }
        }
        temp.addAll(temp2);
        return temp;
    }
    public Set<Expense> select_recurring_expenses(LocalDate start, LocalDate endDate){
        Set<Expense> temp= new HashSet<Expense>();
        for (Expense exp : member_expenses){
            exp.getEffectiveRange();
            LocalDate transactionStartDate= exp.effective.getStartDate();
            LocalDate transactionEndDate= exp.effective.getEndDate();

            if (exp.getIsRecurring() &&
                    ((transactionStartDate.isEqual(start) || transactionStartDate.isAfter(start)) &&
                            (transactionEndDate.isBefore(endDate) || transactionEndDate.isEqual(endDate))))  {

                    temp.add(exp);

            }
        }
        return temp;
    }
    public List<Money_alloc> get_members_allocs(LocalDate start, LocalDate end){
        List<Money_alloc> allAllocations = new ArrayList<>();

        for (Piggy_bank piggyBank : piggy_banks) {
            List<Money_alloc> allocations=piggyBank.get_allocation_for(start, end);
            allAllocations.addAll(allocations);
        }

        return allAllocations;

    }
    public void setID(int a){this.uid= a;}
    public int getID(){return this.uid;}
    public List<Income> findAll_currently_activeIncomes(){ //test
        LocalDate currentDate = LocalDate.now();
        List<Income> result = new ArrayList<>();
        for(Income e : member_income){
            if(currentDate!= null && e.getEndDate().isAfter(currentDate) && e.getIsRecurring()){
                result.add(e);

            }
        }
        return result;
    }
    public List<Expense> findAll_currently_activeExpense(){ //test
        LocalDate currentDate = LocalDate.now();
        List<Expense> result = new ArrayList<>();
        for(Expense e : member_expenses){
            if(currentDate!= null && e.getEndDate().isAfter(currentDate) && e.getIsRecurring()){
                result.add(e);

            }
        }
        return result;
    }
}