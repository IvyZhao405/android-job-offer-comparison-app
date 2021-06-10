package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button enterCurrentJobButton;
    private Button enterJobOffersButton;
    private Button adjustComparisonSettingsButton;
    private Button compareJobOffersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterCurrentJobButton = findViewById(R.id.enterCurrentJobButtonID);
        enterJobOffersButton = findViewById(R.id.enterJobOffersButtonID);
        adjustComparisonSettingsButton = findViewById(R.id.adjustComparisonSettingsButtonID);
        compareJobOffersButton = findViewById(R.id.compareJobOffersButtonID);

        compareJobOffersButton.setEnabled(false);
        // Disable Compare Job Offers button if user does not have atleast one job offer.
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        boolean hasCurrentJob = dbHelper.hasCurrentJob();
        int offerCount = dbHelper.getJobOfferCount();
        if (hasCurrentJob && offerCount >= 1) {
            compareJobOffersButton.setEnabled(true);
        } else if (offerCount >= 2) {
            compareJobOffersButton.setEnabled(true);
        }

        enterCurrentJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enterCurrentJobIntent = new Intent(getApplicationContext(), EnterCurrentJob.class);
                startActivity(enterCurrentJobIntent);
            }
        });

        enterJobOffersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enterJobOffersIntent = new Intent(getApplicationContext(), EnterJobOffers.class);
                startActivity(enterJobOffersIntent);
            }
        });

        adjustComparisonSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adjustComparisonIntent = new Intent(getApplicationContext(), AdjustComparisonSettings.class);
                startActivity(adjustComparisonIntent);
            }
        });

        compareJobOffersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobRankIntent = new Intent(getApplicationContext(), JobRank.class);
                startActivity(jobRankIntent);
            }
        });


    }
}
