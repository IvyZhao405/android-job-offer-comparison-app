package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CompareWithCurrentJob extends AppCompatActivity {

    private Button backButton;
    private Button cancelButton;

    private TextView offerJobTitleTextView;
    private TextView offerCompanyTextView;
    private TextView offerCityTextView;
    private TextView offerStateTextView;
    private TextView offerCostOfLivingTextView;
    private TextView offerYearlySalaryTextView;
    private TextView offerYearlyBonusTextView;
    private TextView offerSigningBonusTextView;
    private TextView offerRetirementBenefitsTextView;
    private TextView offerLeaveTimeTextView;

    private TextView currentJobTitleTextView;
    private TextView currentCompanyTextView;
    private TextView currentCityTextView;
    private TextView currentStateTextView;
    private TextView currentCostOfLivingTextView;
    private TextView currentYearlySalaryTextView;
    private TextView currentYearlyBonusTextView;
    private TextView currentSigningBonusTextView;
    private TextView currentRetirementBenefitsTextView;
    private TextView currentLeaveTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_with_current_job);

        backButton = findViewById(R.id.compare_backButtonID);
        cancelButton = findViewById(R.id.compare_cancelButtonID);

        offerJobTitleTextView = findViewById(R.id.compare_offerTitleID);
        offerCompanyTextView = findViewById(R.id.compare_offerCompanyID);
        offerCityTextView = findViewById(R.id.compare_offerCity);
        offerStateTextView = findViewById(R.id.compare_offerState);
        offerCostOfLivingTextView = findViewById(R.id.compare_offerCostOfLivingID);
        offerYearlySalaryTextView = findViewById(R.id.compare_offerYearlySalaryID);
        offerYearlyBonusTextView = findViewById(R.id.compare_offerYearlyBonusID);
        offerSigningBonusTextView = findViewById(R.id.compare_offerSigningBonusID);
        offerRetirementBenefitsTextView = findViewById(R.id.compare_offerRetirementBenefitID);
        offerLeaveTimeTextView = findViewById(R.id.compare_offerLeaveTimeID);

        currentJobTitleTextView = findViewById(R.id.compare_currentTitleID);
        currentCompanyTextView = findViewById(R.id.compare_currentCompanyID);
        currentCityTextView = findViewById(R.id.compare_currentCity);
        currentStateTextView = findViewById(R.id.compare_currentState);
        currentCostOfLivingTextView = findViewById(R.id.compare_currentCostOfLivingID);
        currentYearlySalaryTextView = findViewById(R.id.compare_currentYearlySalaryID);
        currentYearlyBonusTextView = findViewById(R.id.compare_currentYearlyBonusID);
        currentSigningBonusTextView = findViewById(R.id.compare_currentSigningBonusID);
        currentRetirementBenefitsTextView = findViewById(R.id.compare_currentRetirementBenefitID);
        currentLeaveTimeTextView = findViewById(R.id.compare_currentLeaveTimeID);
        // Fetch the job objects passed from the Job Offer screen.
        Job jobOffer = (Job) getIntent().getSerializableExtra("jobOffer");
        Job currentJob = (Job) getIntent().getSerializableExtra("currentJob");
        // Set the fetched values into the text fields.
        offerJobTitleTextView.setText(jobOffer.getTitle());
        offerCompanyTextView.setText(jobOffer.getCompany());
        offerCityTextView.setText(jobOffer.getCity());
        offerStateTextView.setText(jobOffer.getState());
        offerCostOfLivingTextView.setText(String.valueOf(jobOffer.getCOL()));
        offerYearlySalaryTextView.setText(String.valueOf(jobOffer.getSalary()));
        offerYearlyBonusTextView.setText(String.valueOf(jobOffer.getYearBonus()));
        offerSigningBonusTextView.setText(String.valueOf(jobOffer.getSignBonus()));
        offerRetirementBenefitsTextView.setText(String.valueOf(jobOffer.getBenefits()));
        offerLeaveTimeTextView.setText(String.valueOf(jobOffer.getLeaveTime()));

        currentJobTitleTextView.setText(currentJob.getTitle());
        currentCompanyTextView.setText(currentJob.getCompany());
        currentCityTextView.setText(currentJob.getCity());
        currentStateTextView.setText(currentJob.getState());
        currentCostOfLivingTextView.setText(String.valueOf(currentJob.getCOL()));
        currentYearlySalaryTextView.setText(String.valueOf(currentJob.getSalary()));
        currentYearlyBonusTextView.setText(String.valueOf(currentJob.getYearBonus()));
        currentSigningBonusTextView.setText(String.valueOf(currentJob.getSignBonus()));
        currentRetirementBenefitsTextView.setText(String.valueOf(currentJob.getBenefits()));
        currentLeaveTimeTextView.setText(String.valueOf(currentJob.getLeaveTime()));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToJobOfferIntent = new Intent(getApplicationContext(), EnterJobOffers.class);
                startActivity(backToJobOfferIntent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelJobOfferIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cancelJobOfferIntent);
            }
        });

    }
}
