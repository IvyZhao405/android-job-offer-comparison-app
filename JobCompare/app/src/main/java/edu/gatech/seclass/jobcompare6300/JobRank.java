package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class JobRank extends AppCompatActivity {

    private ListView listView;
    public static Button compareButton;
    private Button cancelButton;
    private ArrayList<Job> jobs;
    private List<String> companyTitles = new ArrayList<>();
    private String companyTitle;
    private ListViewAdapter adapter;
    private ArrayList<Integer> selectedJobs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_rank);

        listView = findViewById(R.id.jobrank_rankListID);

        //get ranked jobs
        jobs = getSortedJobs();
        
        for (int i = 0; i < jobs.size(); i++) {
            if (!jobs.get(i).isOffer()) {
                companyTitle = jobs.get(i).getTitle() + ", " + jobs.get(i).getCompany() + " (Current Job)";
            } else {
                companyTitle = jobs.get(i).getTitle() + ", " + jobs.get(i).getCompany();
            }
            companyTitles.add(companyTitle);
        }

        //generate the job lists
        adapter = new ListViewAdapter(companyTitles, this);
        listView.setAdapter(adapter);


        compareButton = findViewById(R.id.jobrank_compareButtonID);
        cancelButton = findViewById(R.id.jobrank_cancelButtonID);

        compareButton.setEnabled(false);


        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compareJobIntent = new Intent(getApplicationContext(), JobComparison.class);

                if (ListViewAdapter.selectedJobs.size() == 2) {
                    selectedJobs = ListViewAdapter.selectedJobs;
                    compareJobIntent.putExtra("JobArrayList", jobs);
                    compareJobIntent.putExtra("SelectedJobs", selectedJobs);
                    startActivity(compareJobIntent);
                }
                ListViewAdapter.selectedJobs = new ArrayList<>();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelCompareJobIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cancelCompareJobIntent);
                ListViewAdapter.selectedJobs = new ArrayList<>();
            }
        });

    }

    /* Below method returns the list of job objects in descending order of its rank/score */

    public ArrayList<Job> getSortedJobs()
    {
        DBHelper dbHelper = new DBHelper(JobRank.this);
        List<Job> jobs = dbHelper.getAllJobs();
        Map<Integer, ArrayList<Job>> jobsMap = new HashMap<>();
        TreeMap<Integer, ArrayList<Job>> sortedJobsMap = new TreeMap<>();

        for(int i=0; i<jobs.size(); i++)
        {
            int tempRank = computeJobRank(jobs.get(i));
            if(jobsMap.containsKey(tempRank))
            {
                ArrayList<Job> tempList = jobsMap.get(tempRank);
                tempList.add(jobs.get(i));
                jobsMap.put(tempRank,tempList);
            }
            else {
                ArrayList<Job> tempList = new ArrayList<>();
                tempList.add(jobs.get(i));
                jobsMap.put(tempRank, tempList);
            }
        }

        sortedJobsMap.putAll(jobsMap);

        NavigableMap<Integer, ArrayList<Job>> reversedJobsMap = sortedJobsMap.descendingMap();
        Collection<ArrayList<Job>> jobsCollection = reversedJobsMap.values();
        ArrayList<ArrayList<Job>> sortedJobsListInterim = new ArrayList<>(jobsCollection);
        ArrayList<Job> sortedJobsList = new ArrayList<>();
        sortedJobsListInterim.forEach(sortedJobsList::addAll);
        return sortedJobsList;
    }

    /*this is a helper method used by getSortedJobs() method*/

    public int computeJobRank(Job job)
    {
        double adjustedYearlySalary = (job.getSalary()*100)/job.getCOL();
        double adjustedSigningBonus = (job.getSignBonus()*100)/job.getCOL();
        double adjustedYearlyBonus = (job.getYearBonus()*100)/job.getCOL();

        DBHelper dbHelper = new DBHelper(JobRank.this);
        List<Integer> weights = dbHelper.getWeights();

        /* order of weights in database
        1. salaryWeight
        2. signBonusWeight
        3. yearBonusWeight
        4. retireBenefitsWeight
        5. leaveTimeWeight
         */

        int weightYearlySalary = weights.get(0).intValue();
        int weightSignBonus = weights.get(1).intValue();
        int weightYearlyBonus = weights.get(2).intValue();
        int weightBenefits = weights.get(3).intValue();
        int weightLeaveTime = weights.get(4).intValue();

        float weightsSum = weightYearlySalary +
                weightSignBonus +
                weightYearlyBonus +
                weightBenefits +
                weightLeaveTime;

        adjustedYearlySalary = (weightYearlySalary/weightsSum) * adjustedYearlySalary;
        adjustedSigningBonus = (weightSignBonus/weightsSum) * adjustedSigningBonus;
        adjustedYearlyBonus = (weightYearlyBonus/weightsSum) * adjustedYearlyBonus;
        float benefitsPercentage = (weightBenefits/weightsSum) * job.getBenefits();
        float leaveTime = (weightLeaveTime/weightsSum) * job.getLeaveTime();

        double rank1 = (adjustedYearlySalary + adjustedSigningBonus + adjustedYearlyBonus);
        double rank2 = (benefitsPercentage * adjustedYearlySalary);
        double rank3 = (leaveTime * adjustedYearlySalary/260);


        int rank = Integer.valueOf((int) (rank1+rank2+rank3));

        return rank;


    }

}
