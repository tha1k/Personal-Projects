package com.example.familybudget.view.Statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybudget.R;

import org.w3c.dom.Text;

import java.time.LocalDate;

/**
 * Activity class for checking statistics based on user-input filters.
 */
public class CheckStatisticsActivity extends AppCompatActivity implements CheckStatisticsView {

    private CheckStatisticsPresenter presenter;
    /**
     * Called when the activity is first created. Initializes the UI components and sets up event listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        presenter = new CheckStatisticsPresenter(this);

        final EditText editStartDate = findViewById(R.id.editStartDate);
        final EditText editEndDate = findViewById(R.id.editEndDate);
        final EditText editUpperLimit = findViewById(R.id.editUpperLimit);
        final EditText editLowerLimit = findViewById(R.id.editLowerLimit);
        Button btnCalculateStatistics = findViewById(R.id.btnCalculateStatistics);
        final TextView tvIncomeStatistics = findViewById(R.id.tvIncomeStatistics);
        final TextView tvExpenseStatistics = findViewById(R.id.tvExpenseStatistics);
        final TextView tvFamilyCount = findViewById(R.id.tvIncomeStatisticsFamCount);
        final TextView tvMemberCount = findViewById(R.id.tvIncomeStatisticsMemCount);
        final TextView tvIncomeCount = findViewById(R.id.tvIncomeStatisticsIncomeCount);
        final TextView tvExpenseCount = findViewById(R.id.tvIncomeStatisticsExpenseCount);

        btnCalculateStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LocalDate startDate = LocalDate.parse(editStartDate.getText().toString());
                    LocalDate endDate = LocalDate.parse(editEndDate.getText().toString());
                    Double upperLimit = Double.parseDouble(editUpperLimit.getText().toString());
                    Double lowerLimit = Double.parseDouble(editLowerLimit.getText().toString());
                    if(startDate.isAfter(endDate)) {
                        showError("Wrong date inputs.");
                        return;
                    }

                    tvIncomeStatistics.setText(" Average family incomes: " + presenter.getFamiliesStatistics("income", startDate, endDate, upperLimit, lowerLimit));
                    tvExpenseStatistics.setText("Average family expenses: " + presenter.getFamiliesStatistics("expense", startDate, endDate, upperLimit,lowerLimit));
                    tvFamilyCount.setText("Number of families counted: " + presenter.getFamilyCount());
                    tvMemberCount.setText("Number of members counted: " + presenter.getMemberCount());
                    tvIncomeCount.setText("Number of incomes counted: " + presenter.getIncomeCount());
                    tvExpenseCount.setText("Number of expenses counted: " + presenter.getExpenseCount());
                } catch (Exception e) {
                    showError("Fill the form");
                }
            }
        });
    }
    /**
     * Displays an error message using a Toast.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
