package com.example.familybudget;
import android.os.Build;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Money_alloc {
    private int uid;
    private Money amount ;
    private LocalDate transaction_date;
    public Money_alloc(Money amount , LocalDate d){
        this.amount= amount;
        transaction_date= d;
    }
    public Money_alloc(Money amount , LocalDate d, int uid ){
        this.amount= amount;
        transaction_date= d;
        this.uid= uid;
    }
    public Money_alloc(Money amount) {
        this.amount = amount;

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.transaction_date = LocalDate.now();
        //}
    }
    public Money getAmount(){
        return this.amount;
    }
    public void setAmount(Money amount) {
        this.amount = amount;
    }
    public LocalDate getDate(){
        return this.transaction_date;
    }
    public void setDate(LocalDate d ){this.transaction_date=d;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money_alloc that = (Money_alloc) o;
        return amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }


    public int getID() {
        return uid;
    }
    public void setID(int a){
        this.uid=a;
    }
}