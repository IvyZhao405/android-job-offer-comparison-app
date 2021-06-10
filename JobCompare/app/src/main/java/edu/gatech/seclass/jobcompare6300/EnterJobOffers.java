package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterJobOffers extends AppCompatActivity {

    private Button saveButton;
    private Button cancelButton;
    private Button enterAnotherOfferButton;
    private Button compareWithCurrentButton;

    private EditText jobTitleEditText;
    private EditText companyEditText;
    private EditText cityEditText;
    private EditText stateEditText;
    private EditText costOfLivingEditText;
    private EditText yearlySalaryEditText;
    private EditText yearlyBonusEditText;
    private EditText signingBonusEditText;
    private EditText retirementBenefitsEditText;
    private EditText leaveTimeEditText;
    // Variable to hold the saved job offer. Used for comparison with current job.
    private Job savedOffer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_offers);

        saveButton = findViewById(R.id.joboffer_saveButtonID);
        cancelButton = findViewById(R.id.joboffer_cancelButtonID);
        enterAnotherOfferButton = findViewById(R.id.joboffer_enteranother_ButtonID);
        compareWithCurrentButton = findViewById(R.id.joboffer_compareButtonID);
        jobTitleEditText = findViewById(R.id.joboffer_titleID);
        companyEditText = findViewById(R.id.joboffer_companyID);
        cityEditText = findViewById(R.id.joboffer_cityID);
        stateEditText = findViewById(R.id.joboffer_stateID);
        costOfLivingEditText = findViewById(R.id.joboffer_costLivingIndexID);
        yearlySalaryEditText = findViewById(R.id.joboffer_yearlySalaryID);
        yearlyBonusEditText = findViewById(R.id.joboffer_yearlyBonusID);
        signingBonusEditText = findViewById(R.id.joboffer_signingBonusID);
        retirementBenefitsEditText = findViewById(R.id.joboffer_retirementBenefitID);
        leaveTimeEditText = findViewById(R.id.joboffer_leaveTimeID);

        // Disable Compare With Current Job button if current job does not exist.
        DBHelper dbHelper = new DBHelper(EnterJobOffers.this);
        final Job currentJob = dbHelper.getCurrentJob();
        if (currentJob == null) {
            compareWithCurrentButton.setEnabled(false);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelJobOfferIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cancelJobOfferIntent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job jobOffer;
                int costOfLivingIndex = 0;
                float yearlySalary = 0;
                float signingBonus = 0;
                float yearlyBonus = 0;
                float retirementBenefit = 0;
                int leaveTime = 0;
                String jobTitle = jobTitleEditText.getText().toString();
                String company = companyEditText.getText().toString();
                String city = cityEditText.getText().toString();
                String state = stateEditText.getText().toString();
                if (jobTitle.isEmpty()) {
                    jobTitleEditText.setError("Job Title required!");
                    Toast.makeText(EnterJobOffers.this, "Please enter Job Title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (company.isEmpty()) {
                    companyEditText.setError("Company required!");
                    Toast.makeText(EnterJobOffers.this, "Please enter Company", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!costOfLivingEditText.getText().toString().isEmpty()) {
                    costOfLivingIndex = Integer.parseInt(costOfLivingEditText.getText().toString());
                }
                if (!yearlySalaryEditText.getText().toString().isEmpty()) {
                    yearlySalary = Float.parseFloat(yearlySalaryEditText.getText().toString());
                }
                if (!signingBonusEditText.getText().toString().isEmpty()) {
                    signingBonus = Float.parseFloat(signingBonusEditText.getText().toString());
                }
                if (!yearlyBonusEditText.getText().toString().isEmpty()) {
                    yearlyBonus = Float.parseFloat(yearlyBonusEditText.getText().toString());
                }
                if (!retirementBenefitsEditText.getText().toString().isEmpty()) {
                    retirementBenefit = Float.parseFloat(retirementBenefitsEditText.getText().toString());
                }
                if (!leaveTimeEditText.getText().toString().isEmpty()) {
                    leaveTime = Integer.parseInt(leaveTimeEditText.getText().toString());
                }
                try {
                    jobOffer = new Job (1, jobTitle, company, city, state, costOfLivingIndex, yearlySalary, signingBonus,
                            yearlyBonus, retirementBenefit,  leaveTime,true);

                    DBHelper dbHelper = new DBHelper(EnterJobOffers.this);
                    boolean addedJobOffer = dbHelper.addJob(jobOffer);
                    if (addedJobOffer) {
                        disableFields();
                        savedOffer = jobOffer;
                        Toast.makeText(EnterJobOffers.this, "Job offer saved successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EnterJobOffers.this, "Error saving job offer", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(EnterJobOffers.this, "Error saving job offer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enterAnotherOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enterJobOffersIntent = new Intent(getApplicationContext(), EnterJobOffers.class);
                startActivity(enterJobOffersIntent);
            }
        });

        compareWithCurrentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedOffer == null) {
                    Toast.makeText(EnterJobOffers.this, "Please enter and save a job offer", Toast.LENGTH_SHORT).show();
                } else if (currentJob != null) {
                    Intent compareWithCurrentJobIntent = new Intent(getApplicationContext(), CompareWithCurrentJob.class);
                    compareWithCurrentJobIntent.putExtra("jobOffer", savedOffer);
                    compareWithCurrentJobIntent.putExtra("currentJob", currentJob);
                    startActivity(compareWithCurrentJobIntent);
                }
            }
        });

    }

    // Method to set the text fields and Save button to readonly once Save operation is successful.
    private void disableFields() {
        jobTitleEditText.setEnabled(false);
        companyEditText.setEnabled(false);
        cityEditText.setEnabled(false);
        stateEditText.setEnabled(false);
        costOfLivingEditText.setEnabled(false);
        yearlySalaryEditText.setEnabled(false);
        yearlyBonusEditText.setEnabled(false);
        signingBonusEditText.setEnabled(false);
        retirementBenefitsEditText.setEnabled(false);
        leaveTimeEditText.setEnabled(false);
        saveButton.setEnabled(false);
    }

}
