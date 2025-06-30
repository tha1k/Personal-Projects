package com.example.familybudget.view.Family.Homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.Dao.Initializer;
import com.example.familybudget.DaoMemory.Memory_Initializer;
import com.example.familybudget.R;
import com.example.familybudget.view.Family_Manage.Family_MainPage.Family_MainPageActivity;
import com.example.familybudget.view.Family_Member.FamilyMemberOptions.FamilyMemberOptionsActivity;
import com.example.familybudget.view.Incomes_Expenses.IncomesExpensesActivity;
import com.example.familybudget.view.Overview_Budget.Overview_BudgetMainPage.Overview_BudgetMainPageActivity;
import com.example.familybudget.view.PiggyBank.Manage.ManagePiggyBanksActivity;
import com.example.familybudget.view.Statistics.CheckStatisticsActivity;
/**
 * Activity representing the homepage of the family budget application.
 */
public class HomepageActivity extends AppCompatActivity implements HomepageView{
    Initializer data = new Memory_Initializer();
    private static boolean initialized = false;/**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this Bundle contains
     *                           the data it most recently supplied in
     *                           onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final HomepagePresenter presenter = new HomepagePresenter(this);
        findViewById(R.id.button_incomes_expenses).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                presenter.on_incomes_expenses();
            }
        });
        findViewById(R.id.button_piggybanks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.on_piggy_bank();
            }
        });
        findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.on_register_member();
            }
        });
        findViewById(R.id.button_budget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.on_check_budget();
            }
        });
        findViewById(R.id.button_statistics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.on_check_statistics();
            }
        });
        findViewById(R.id.btnFamDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.on_family_options();
            }
        });
        if(!initialized){
            data.prepareDemoData();
            initialized = true;
        }
    }
    /**
     * Navigate to the Incomes and Expenses activity.
     */
    public void famdetails(){
        Intent i = new Intent(this, Family_MainPageActivity.class);
        startActivity(i);
    }
    public void incomes_expenses(){
        Intent intent = new Intent(HomepageActivity.this, IncomesExpensesActivity.class);
        startActivity(intent);
    }
    /**
     * Navigate to the Piggy Bank management activity.
     */

    public void piggy_bank(){
        Intent intent = new Intent(HomepageActivity.this, ManagePiggyBanksActivity.class);
        startActivity(intent);
    }
    /**
     * Navigate to the Family registration activity.
     */
    public void register_family(){
        //Intent intent = new Intent(HomepageActivity.this, ) fill!
        //startActivity(intent)
    }
    /**
     * Navigate to the Member registration activity.
     */
    public void register_member(){
        Intent intent = new Intent(HomepageActivity.this, FamilyMemberOptionsActivity.class);
        startActivity(intent);
    }
    /**
     * Navigate to the Budget activity.
     */
    public void check_budget(){
        Intent intent = new Intent(HomepageActivity.this, Overview_BudgetMainPageActivity.class);
        startActivity(intent);
    }
    /**
     * Navigate to the Statistics activity.
     */
    public void check_statistics(){
        Intent intent = new Intent(HomepageActivity.this, CheckStatisticsActivity.class);
        startActivity(intent);
    }
}