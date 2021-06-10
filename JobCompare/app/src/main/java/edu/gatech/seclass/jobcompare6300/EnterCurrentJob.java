package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterCurrentJob extends AppCompatActivity {
    private Button saveButton;
    private Button cancelButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_current_job);

        saveButton = findViewById(R.id.currentjob_saveButtonID);
        cancelButton = findViewById(R.id.currentjob_cancelButtonID);
        jobTitleEditText = findViewById(R.id.currentjob_titleID);
        companyEditText = findViewById(R.id.currentjob_companyID);
        cityEditText = findViewById(R.id.currentjob_cityID);
        stateEditText = findViewById(R.id.currentjob_stateID);
        costOfLivingEditText = findViewById(R.id.currentjob_costLivingIndexID);
        yearlySalaryEditText = findViewById(R.id.currentjob_yearlySalaryID);
        yearlyBonusEditText = findViewById(R.id.currentjob_yearlyBonusID);
        signingBonusEditText = findViewById(R.id.currentjob_signingBonusID);
        retirementBenefitsEditText = findViewById(R.id.currentjob_retirementBenefitID);
        leaveTimeEditText = findViewById(R.id.currentjob_leaveTimeID);

        // Populate fields with values if current job details exists.
        DBHelper dbHelper = new DBHelper(EnterCurrentJob.this);
        final Job currentJob = dbHelper.getCurrentJob();
        if (currentJob != null) {
            jobTitleEditText.setText(currentJob.getTitle());
            companyEditText.setText(currentJob.getCompany());
            cityEditText.setText(currentJob.getCity());
            stateEditText.setText(currentJob.getState());
            costOfLivingEditText.setText(String.valueOf(currentJob.getCOL()));
            yearlySalaryEditText.setText(String.valueOf(currentJob.getSalary()));
            yearlyBonusEditText.setText(String.valueOf(currentJob.getYearBonus()));
            signingBonusEditText.setText(String.valueOf(currentJob.getSignBonus()));
            retirementBenefitsEditText.setText(String.valueOf(currentJob.getBenefits()));
            leaveTimeEditText.setText(String.valueOf(currentJob.getLeaveTime()));
        }


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelCurrentJobIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cancelCurrentJobIntent);
            }
        });

        //EXAMPLE BUTTON AND METHOD CODED BY REGGIE TO SHOW DATABASE FUNCTIONALITY
        //ENTERS ONE CURRENT JOB INTO THE DATABASE, CURRENTLY USES FIXED INPUT:
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job job;
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
                    Toast.makeText(EnterCurrentJob.this, "Please enter Job Title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (company.isEmpty()) {
                    companyEditText.setError("Company required!");
                    Toast.makeText(EnterCurrentJob.this, "Please enter Company", Toast.LENGTH_SHORT).show();
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
                    job = new Job (1, jobTitle, company, city, state, costOfLivingIndex, yearlySalary, signingBonus,
                            yearlyBonus, retirementBenefit,  leaveTime,false);
                    //Create DBHelper object to add a job to the database:
                    DBHelper dbHelper = new DBHelper(EnterCurrentJob.this);
                    if (currentJob == null) {
                        boolean addedJob = dbHelper.addJob(job);
                        if (addedJob) {
                            Toast.makeText(EnterCurrentJob.this, "Current Job details saved successfully!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        job.setId(currentJob.getId());
                        boolean updatedJob = dbHelper.updateJob(job);
                        if (updatedJob) {
                            Toast.makeText(EnterCurrentJob.this, "Current Job details updated successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Intent saveCurrentJobIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(saveCurrentJobIntent);
                }
                catch (Exception e) {
                    Toast.makeText(EnterCurrentJob.this, "Error saving current job details", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
