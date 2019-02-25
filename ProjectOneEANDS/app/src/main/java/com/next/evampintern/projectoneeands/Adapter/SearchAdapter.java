package com.next.evampintern.projectoneeands.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.next.evampintern.projectoneeands.Model.SearchJobs;
import com.next.evampintern.projectoneeands.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheharyar on 2/20/2019.
 */

public class SearchAdapter extends ArrayAdapter<SearchJobs> {

    public SearchAdapter(Context context, ArrayList<SearchJobs> searchJobsList){
        super(context, 0, searchJobsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View listView = convertView;
       if (listView == null){
           listView = LayoutInflater.from(getContext()).inflate(R.layout.list_card_view, parent, false);

       }

        SearchJobs searchJobs = getItem(position);
        TextView mainTitle = (TextView)listView.findViewById(R.id.mainTitle);
        TextView companyName = (TextView)listView.findViewById(R.id.companyName);
        TextView date = (TextView)listView.findViewById(R.id.date);
        TextView location = (TextView)listView.findViewById(R.id.location);
        TextView jobType = (TextView)listView.findViewById(R.id.jobType);
        TextView jobDescription = (TextView)listView.findViewById(R.id.jobDescription);


        mainTitle.setText(searchJobs.getmJobTitle());
        companyName.setText(searchJobs.getmCompanyName());
        date.setText(searchJobs.getmCreatedDate());
        location.setText(searchJobs.getmLoaction());
        jobType.setText(searchJobs.getmJobType());
        jobDescription.setText(searchJobs.getmJobDescription());
        return listView;
    }


}
