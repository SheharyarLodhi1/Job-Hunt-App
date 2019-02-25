package com.next.evampintern.projectoneeands.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.next.evampintern.projectoneeands.Model.JobsForAndroid;
import com.next.evampintern.projectoneeands.Model.JobsInSf;
import com.next.evampintern.projectoneeands.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sheharyar on 2/21/2019.
 */

public class AndroidJobsAdapter extends ArrayAdapter<JobsForAndroid> {

    private static final String ADAPTER_TAG = AndroidJobsAdapter.class.getName();
    public AndroidJobsAdapter(Context context, ArrayList<JobsForAndroid> jobsForAndroids){
        super(context, 0, jobsForAndroids);
        Log.i(ADAPTER_TAG, "Adapter is called");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listofItems = convertView;
        Log.i(ADAPTER_TAG,"getView method is called");
        if (listofItems == null) {
            listofItems = LayoutInflater.from(getContext()).inflate(R.layout.list_cardview_android_jobs, parent, false);
        }

            JobsForAndroid jobsForAndroid = getItem(position);
            TextView mainTitleForSf = (TextView) listofItems.findViewById(R.id.mainTitle);
            TextView mainCompanyNameForSf = (TextView) listofItems.findViewById(R.id.companyName);
            TextView mainDate = (TextView) listofItems.findViewById(R.id.date);
            TextView mainLocation = (TextView) listofItems.findViewById(R.id.location);
            TextView mainJobType = (TextView) listofItems.findViewById(R.id.jobType);
            TextView mainJobDescription = (TextView) listofItems.findViewById(R.id.jobDescription);
            ImageView companyIcon = (ImageView)listofItems.findViewById(R.id.image_view);

            mainTitleForSf.setText(jobsForAndroid.getmJobTitle());
            mainCompanyNameForSf.setText(jobsForAndroid.getmCompanyName());
            mainDate.setText(jobsForAndroid.getmCreatedDate());
            mainLocation.setText(jobsForAndroid.getmLoaction());
            mainJobType.setText(jobsForAndroid.getmJobType());
            mainJobDescription.setText(jobsForAndroid.getmJobDescription());
            String imageUrl = jobsForAndroid.getmCompanyLogo();
            Picasso.with(getContext()).load(imageUrl).fit().centerCrop().into(companyIcon);
            return listofItems;
        }
    }
