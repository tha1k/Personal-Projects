package com.example.familybudget;
import static java.time.temporal.ChronoUnit.DAYS;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.Objects;

public class Date_range {
    private LocalDate startDate;
    private LocalDate endDate;


    public Date_range( LocalDate startDate,  LocalDate endDate) {
        if ((startDate==null || endDate==null) || startDate.isAfter(endDate) ){
            throw new IllegalArgumentException();
        }
        else{
        this.startDate = startDate;
        this.endDate = endDate;}
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int totalDaysUntil() {

        return (int) DAYS.between(this.getStartDate(), this.getEndDate());

    }

    @Override
    public String toString() {
        return "DateRange{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date_range dateRange = (Date_range) o;
        return Objects.equals(startDate, dateRange.startDate) && Objects.equals(endDate, dateRange.endDate);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
