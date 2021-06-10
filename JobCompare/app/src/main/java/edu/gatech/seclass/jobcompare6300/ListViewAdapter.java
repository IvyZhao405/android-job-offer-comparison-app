package edu.gatech.seclass.jobcompare6300;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class ListViewAdapter extends ArrayAdapter<String> {

    private List<String> jobs;
    private Context context;
    public static ArrayList<Integer> selectedJobs = new ArrayList<>();

    public ListViewAdapter(List<String> jobs, Context context) {
        super(context, R.layout.item_layout, jobs);
        this.context = context;
        this.jobs = jobs;

    }

    @NonNull
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.item_layout, parent, false);
        TextView jobItem = row.findViewById(R.id.company_title);
        jobItem.setText(jobs.get(position));

        CheckBox checkBox = row.findViewById(R.id.checkbox);
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setTag(position);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();
                if (isChecked) {
                    selectedJobs.add(position);
                } else {
                    selectedJobs.remove(Integer.valueOf(position));
                }
                if (selectedJobs.size() ==2) {
                    JobRank.compareButton.setEnabled(true);
                } else {
                    JobRank.compareButton.setEnabled(false);
                    Toast.makeText(context, "Please select two jobs. ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return row;
    }
}
