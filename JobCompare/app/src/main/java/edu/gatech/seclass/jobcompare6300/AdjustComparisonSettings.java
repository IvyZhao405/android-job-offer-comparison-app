package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AdjustComparisonSettings extends AppCompatActivity {

    private Button saveButton;
    private Button cancelButton;

    private EditText yearlySalaryWeightEditText;
    private EditText signingBonusWeightEditText;
    private EditText yearlyBonusWeightEditText;
    private EditText retirementBenefitsWeightEditText;
    private EditText leaveTimeWeightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjustcomparisonsettings);

        saveButton = findViewById(R.id.compsetting_saveButtonID);
        cancelButton = findViewById(R.id.compsetting_cancelButtonID);

        yearlySalaryWeightEditText = findViewById(R.id.compsetting_yearlySalaryID);
        signingBonusWeightEditText = findViewById(R.id.compsetting_signingBonusID);
        yearlyBonusWeightEditText = findViewById(R.id.compsetting_yearlyBonusID);
        retirementBenefitsWeightEditText = findViewById(R.id.compsetting_retirementBenefitID);
        leaveTimeWeightEditText = findViewById(R.id.compsetting_leaveTimeID);

        DBHelper dbHelper = new DBHelper(AdjustComparisonSettings.this);

        List<Integer> currentWeightsList = dbHelper.getWeights();

        yearlySalaryWeightEditText.setText(String.valueOf(currentWeightsList.get(0)));
        signingBonusWeightEditText.setText(String.valueOf(currentWeightsList.get(1)));
        yearlyBonusWeightEditText.setText(String.valueOf(currentWeightsList.get(2)));
        retirementBenefitsWeightEditText.setText(String.valueOf(currentWeightsList.get(3)));
        leaveTimeWeightEditText.setText(String.valueOf(currentWeightsList.get(4)));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelComparisonSettingIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cancelComparisonSettingIntent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String salaryWeightString = yearlySalaryWeightEditText.getText().toString();
                int salWeight;
                String signBonusWeightString = signingBonusWeightEditText.getText().toString();
                int signBonusWeight;
                String yearBonusWeightString = yearlyBonusWeightEditText.getText().toString();
                int yearBonusWeight;
                String retireBenefitsWeightString = retirementBenefitsWeightEditText.getText().toString();
                int retireBenefitsWeight;
                String leaveTimeWeightString = leaveTimeWeightEditText.getText().toString();
                int leaveTimeWeight;

                if (salaryWeightString.isEmpty()) {
                    yearlySalaryWeightEditText.setError("Empty Salary Weight!");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a Salary weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (Integer.parseInt(salaryWeightString) == 0) {
                    yearlySalaryWeightEditText.setError("Please enter a nonzero value.");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a nonzero value", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                   salWeight = Integer.parseInt(salaryWeightString);
                }
                if (signBonusWeightString.isEmpty()) {
                    signingBonusWeightEditText.setError("Empty Signing Bonus Weight!");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a Signing Bonus weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (Integer.parseInt(signBonusWeightString) == 0) {
                    signingBonusWeightEditText.setError("Please enter a nonzero value.");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a nonzero value", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    signBonusWeight = Integer.parseInt(signBonusWeightString);
                }
                if (yearBonusWeightString.isEmpty()) {
                    yearlyBonusWeightEditText.setError("Empty Yearly Bonus Weight!");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a Yearly Bonus weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (Integer.parseInt(yearBonusWeightString) == 0) {
                    yearlyBonusWeightEditText.setError("Please enter a nonzero value.");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a nonzero value", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    yearBonusWeight = Integer.parseInt(yearBonusWeightString);
                }
                if (retireBenefitsWeightString.isEmpty()) {
                    retirementBenefitsWeightEditText.setError("Empty Retirement Benefits Weight!");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a Retire Benefits weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (Integer.parseInt(retireBenefitsWeightString) == 0) {
                    retirementBenefitsWeightEditText.setError("Please enter a nonzero value.");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a nonzero value", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    retireBenefitsWeight = Integer.parseInt(retireBenefitsWeightString);
                }
                if (leaveTimeWeightString.isEmpty()) {
                    leaveTimeWeightEditText.setError("Empty Leave Time Weight!");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a Leave Time weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (Integer.parseInt(leaveTimeWeightString) == 0) {
                    leaveTimeWeightEditText.setError("Please enter a nonzero value.");
                    Toast.makeText(AdjustComparisonSettings.this, "Please enter a nonzero value", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    leaveTimeWeight = Integer.parseInt(leaveTimeWeightString);
                }

                boolean successUpdate = dbHelper.updateWeights(salWeight, signBonusWeight, yearBonusWeight,
                        retireBenefitsWeight, leaveTimeWeight);
                if (successUpdate) {
                    Toast.makeText(AdjustComparisonSettings.this, "Weights saved successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AdjustComparisonSettings.this, "Failed to save weights!", Toast.LENGTH_SHORT).show();
                }
                Intent saveWeightsIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(saveWeightsIntent);
            }
        });
    }
}
