package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class JobComparison extends AppCompatActivity {

    private Button anotherComparisonButton;
    private Button cancelButton;

    private EditText firstTitle;
    private EditText secondTitle;
    private EditText firstCompany;
    private EditText secondCompany;
    private EditText firstLocation;
    private EditText secondLocation;
    private EditText firstYearlySalary;
    private EditText secondYearlySalary;
    private EditText firstSigningBonus;
    private EditText secondSigningBonus;
    private EditText firstYearlyBonus;
    private EditText secondYearlyBonus;
    private EditText firstRetirementBenefit;
    private EditText secondRetirementBenefit;
    private EditText firstLeaveTime;
    private EditText secondLeaveTime;
    private ArrayList<Job> jobs;
    private ArrayList<Integer> selectedJobs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_comparison);


        Intent intent = getIntent();

        jobs = (ArrayList<Job>) intent.getSerializableExtra("JobArrayList");


        System.out.println(jobs.size());

        selectedJobs = (ArrayList<Integer>) intent.getSerializableExtra("SelectedJobs");


        System.out.println(jobs.size());
        System.out.println(selectedJobs.size());

        firstTitle = findViewById(R.id.jobcomp_firstTitleID);
        secondTitle = findViewById(R.id.jobcomp_secondTitleID);
        firstCompany = findViewById(R.id.jobcomp_firstCompanyID);
        secondCompany = findViewById(R.id.jobcomp_secondCompanyID);
        firstLocation = findViewById(R.id.jobcomp_firstLocationID);
        secondLocation = findViewById(R.id.jobcomp_secondLocationID);
        firstYearlySalary = findViewById(R.id.jobcomp_firstYearlySalaryID);
        secondYearlySalary = findViewById(R.id.jobcomp_secondYearlySalaryID);
        firstSigningBonus = findViewById(R.id.jobcomp_firstSigningBonusID);
        secondSigningBonus = findViewById(R.id.jobcomp_secondSigningBonusID);
        firstYearlyBonus = findViewById(R.id.jobcomp_firstYearlyBonusID);
        secondYearlyBonus = findViewById(R.id.jobcomp_secondYearlyBonusID);
        firstRetirementBenefit = findViewById(R.id.jobcomp_firstRetirementBenefitID);
        secondRetirementBenefit = findViewById(R.id.jobcomp_secondRetirementBenefitID);
        firstLeaveTime = findViewById(R.id.jobcomp_firstLeaveTimeID);
        secondLeaveTime = findViewById(R.id.jobcomp_secondLeaveTimeID);

        Job firstJob = jobs.get(selectedJobs.get(0));
        Job secondJob = jobs.get(selectedJobs.get(1));

        firstTitle.setText(firstJob.getTitle());
        secondTitle.setText(secondJob.getTitle());
        firstCompany.setText(firstJob.getCompany());
        secondCompany.setText(secondJob.getCompany());
        firstLocation.setText(firstJob.getCity() + ", " + firstJob.getState());
        secondLocation.setText(secondJob.getCity() + ", " + secondJob.getState());
        firstYearlySalary.setText(String.valueOf(firstJob.getSalary()));
        secondYearlySalary.setText(String.valueOf(secondJob.getSalary()));
        firstSigningBonus.setText(String.valueOf(firstJob.getSignBonus()));
        secondSigningBonus.setText(String.valueOf(secondJob.getSignBonus()));
        firstYearlyBonus.setText(String.valueOf(firstJob.getYearBonus()));
        secondYearlyBonus.setText(String.valueOf(secondJob.getYearBonus()));
        firstRetirementBenefit.setText(String.valueOf(firstJob.getBenefits()));
        secondRetirementBenefit.setText(String.valueOf(secondJob.getBenefits()));
        firstLeaveTime.setText(String.valueOf(firstJob.getLeaveTime()));
        secondLeaveTime.setText(String.valueOf(secondJob.getLeaveTime()));





        anotherComparisonButton = findViewById(R.id.jobcomp_anotherComparisonButtonID);
        cancelButton = findViewById(R.id.jobcomp_cancelButtonID);


        anotherComparisonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compareAnotherJobIntent = new Intent(getApplicationContext(), JobRank.class);
                startActivity(compareAnotherJobIntent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelCompareJobIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cancelCompareJobIntent);
            }
        });

    }




}

