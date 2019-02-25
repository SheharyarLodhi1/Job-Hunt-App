package com.next.evampintern.projectoneeands.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.next.evampintern.projectoneeands.Model.JobsInSf;
import com.next.evampintern.projectoneeands.Model.SearchJobs;
import com.next.evampintern.projectoneeands.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheharyar on 2/21/2019.
 */

public class JobsInSfAdapter extends ArrayAdapter<JobsInSf> {

    public JobsInSfAdapter(Context context, ArrayList<JobsInSf> jobsInSfArrayList){
        super(context, 0, jobsInSfArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listofItems = convertView;
        if (listofItems == null){
            listofItems = LayoutInflater.from(getContext()).inflate(R.layout.list_card_sf_jobs, parent, false);
        }

       JobsInSf jobsInSf = getItem(position);
        TextView mainTitleForSf = (TextView)listofItems.findViewById(R.id.mainTitle);
        TextView mainCompanyNameForSf = (TextView)listofItems.findViewById(R.id.companyName);
        TextView mainDate = (TextView)listofItems.findViewById(R.id.date);
        TextView mainLocation = (TextView)listofItems.findViewById(R.id.location);
        TextView mainJobType = (TextView)listofItems.findViewById(R.id.jobType);
        TextView mainJobDescription = (TextView)listofItems.findViewById(R.id.jobDescription);


        mainTitleForSf.setText(jobsInSf.getmJobTitle());
        mainCompanyNameForSf.setText(jobsInSf.getmCompanyName());
        mainDate.setText(jobsInSf.getmCreatedDate());
        mainLocation.setText(jobsInSf.getmLoaction());
        mainJobType.setText(jobsInSf.getmJobType());
        mainJobDescription.setText(jobsInSf.getmJobDescription());
        return listofItems;
    }
}
