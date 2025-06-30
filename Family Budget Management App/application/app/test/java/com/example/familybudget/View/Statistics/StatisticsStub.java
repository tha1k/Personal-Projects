package com.example.familybudget.View.Statistics;

import com.example.familybudget.view.Statistics.CheckStatisticsView;

public class StatisticsStub implements CheckStatisticsView {
    int counter;
    String last;
    public void successSearch(String message){
        last = message;
        counter++;
    }
    public int returnCounter(){
        return counter;
    }
    public void showError(String message){
        counter ++;
        last = message;
    }

}