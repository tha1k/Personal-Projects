package com.example.familybudget.view.Family.Homepage;
/**
 * Presenter class for managing interactions between the HomepageView and the underlying data.
 */
public class HomepagePresenter {
    private HomepageView view;
    /**
     * Constructor for HomepagePresenter.
     *
     * @param view The associated HomepageView.
     */
    public HomepagePresenter(HomepageView view){
        this.view = view;
    }
    /**
     * Handle the "Incomes and Expenses" button click.
     */
    void on_incomes_expenses(){
        view.incomes_expenses();
    }
    /**
     * Handle the "Piggy Bank" button click.
     */
    void on_piggy_bank(){
        view.piggy_bank();
    }
    /**
     * Placeholder method for handling the "Register Family" button click.
     */
    void on_register_family(){
        view.register_family();
    }
    /**
     * Handle the "Register Member" button click.
     */
    void on_register_member(){
        view.register_member();
    }
    /**
     * Placeholder method for handling the "Check Budget" button click.
     */
    void on_check_budget(){
        view.check_budget();
    }
    /**
     * Handle the "Check Statistics" button click.
     */
    void on_check_statistics(){
        view.check_statistics();
    }
    void on_family_options(){view.famdetails();}
}