package com.example.familybudget;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Transactions {
    private Money money;
    private boolean isRecurring;
    private LocalDate registrationDate;
    private LocalDate endDate;
    Date_range effective;
    private String description;
    Family_Member owner;
    private int uid;
    public Transactions(){}
    public Transactions(Money money, boolean isRecurring, LocalDate registrationDate, LocalDate endDate, String description) {
        setMoney(money);
        this.isRecurring = isRecurring;
        this.registrationDate = registrationDate;
        setEndDate(endDate, registrationDate);
        this.description = description;
    }
    public void setMoney(Money money){
        if (money.getAmount().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException();
        }
        this.money = money;
    }
    public Family_Member get_owner(){return this.owner;}
    public void set_owner(Family_Member owner){ this.owner = owner;}
    public Money getMoney() {
        return money;
    }
    public void setIsRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }
    public boolean getIsRecurring() {
        return isRecurring;
    }
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
    public void setEndDate(LocalDate endDate, LocalDate registrationDate){
        if (endDate.isBefore(registrationDate)){
            throw new IllegalArgumentException();
        }
        this.endDate = endDate;
    }
    public LocalDate getEndDate(){
        return endDate;
    }
    public String getEffectiveRange(){
        effective = new Date_range(getRegistrationDate(), getEndDate());
        return String.valueOf(effective.totalDaysUntil());
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public String toString(){
        String recurring;
        if(getIsRecurring()){
            recurring = "The transaction is recurring";
        }else{
            recurring = "The transaction is not recurring";
        }
        return "Transaction: \n" +
                "Money: " + getMoney() + "\n" +
                "Registration date: " + getRegistrationDate() + "\n" +
                "End date: " + getEndDate() + "\n" +
                "Effective for: " + getEffectiveRange() + "days" + "\n" +
                recurring + "\n" +
                "Description: " + getDescription() + "\n";
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transactions transaction = (Transactions) o;
        return Objects.equals(money, transaction.money) &&
                isRecurring == transaction.isRecurring &&
                Objects.equals(registrationDate, transaction.registrationDate) &&
                Objects.equals(endDate, transaction.endDate) &&
                Objects.equals(description, transaction.description);
    }
    public void setId(int a){this.uid= a;}
    public int getId(){return this.uid;}
}