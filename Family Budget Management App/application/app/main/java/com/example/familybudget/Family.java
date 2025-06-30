package com.example.familybudget;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Family {
    private Set<Family_Member> members = new HashSet<Family_Member>();
    private Family_Member protector;
    private String family_name;
    private LocalDateTime hmeromhnia_eggrafhs;
    private Money disposable_income;
    private Money total_income;
    private Money total_expenses;
    private Money total_savings;
    private int uid;
    public Family(String name, Family_Member protector) {
        this.family_name = name;
        setProtector(protector);
        this.members.add(protector);
        this.hmeromhnia_eggrafhs = LocalDateTime.now();
        this.disposable_income = new Money();
        this.total_income = new Money();
        this.total_expenses = new Money();
        this.total_savings = new Money();
    }

    public Family_Member getProtector() {
        return this.protector;
    }

    public void setProtector(Family_Member member) {
        if (member.Is_protector()) {
            this.protector = member;
        } else {
            throw new NotProtector();
        }
    }

    public Set getMembers(){
        return members;
    }

    public String getFamilyName() {
        return this.family_name;
    }

    public void setFamilyName(String name) {
        this.family_name = name;
    }

    public LocalDateTime getRegisterDate() {
        return this.hmeromhnia_eggrafhs;
    }

    public void setRegisterDate(LocalDateTime date) {
        this.hmeromhnia_eggrafhs = date;
    }

    public Money getDisposableIncome() {
        return this.disposable_income;
    }

    public void setDisposableIncome(Money money) {
        if (money.getAmount().compareTo(BigDecimal.ZERO) >= 0){
            this.disposable_income = money;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public Money getTotalIncome() {
        return this.total_income;
    }

    public void setTotalIncome(Money money) {
        this.total_income = money;
    }

    public Money getTotalExpenses() {
        return this.total_expenses;
    }

    public void setTotalExpenses(Money money) {
        this.total_expenses = money;
    }

    public Money getTotalSavings() {
        return this.total_savings;
    }

    public void setTotalSavings(Money money) {
        this.total_savings = money;
    }

    public void addFamilyMember(Family_Member member) {
        if (!(member instanceof Family_Member)) {
            throw new NotInstanceOfFamilyMember();
        } else {
            this.members.add(member);
        }
    }

    public void removeFamilyMember(Family_Member member) {
        if (!(member instanceof Family_Member)) {
            throw new NotInstanceOfFamilyMember();
        } else {
            if (members.contains(member)) {
                members.remove(member);
            } else {
                throw new NotAMemberOfThisFamily();
            }
        }
    }

    public void printFamilyMembers() {
        System.out.println();
        for (Family_Member i : members) {
            System.out.print(i.getName() + " ");
        }
        System.out.print("are the members of this family.");
    }

    public void printStats() {
        BigDecimal disposable_dia_totalincome = this.disposable_income.getAmount().divide(this.total_income.getAmount());
        BigDecimal pososto_disp_inc = disposable_dia_totalincome.multiply(BigDecimal.valueOf(100));
        BigDecimal expenses_dia_totalincome = this.total_expenses.getAmount().divide(this.total_income.getAmount());
        BigDecimal pososto_exp_inc = expenses_dia_totalincome.multiply(BigDecimal.valueOf(100));
        System.out.println("The disposable income percentage compared to the total income comes out to: " + pososto_disp_inc + ", while the total expenses percentage compared to the total income comes out to: " + pososto_exp_inc + ".");
    }
    public void setId(int a){this.uid= a;}
    public int getId(){return this.uid;}
}
