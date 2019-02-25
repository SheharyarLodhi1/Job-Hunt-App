package com.next.evampintern.projectoneeands.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.next.evampintern.projectoneeands.Adapter.AndroidJobsAdapter;
import com.next.evampintern.projectoneeands.Adapter.JobsInSfAdapter;
import com.next.evampintern.projectoneeands.AndroidJobsLoader;
import com.next.evampintern.projectoneeands.Model.JobsForAndroid;
import com.next.evampintern.projectoneeands.Model.JobsInSf;
import com.next.evampintern.projectoneeands.R;
import com.next.evampintern.projectoneeands.SfJobsLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheharyar on 2/20/2019.
 */

public class AndroidJobs extends Fragment  implements LoaderManager.LoaderCallbacks<List<JobsForAndroid>> {

    private static final String LOG_TAG = JobSfFragment.class.getName();
    public AndroidJobs(){
        createStringUrl();
    }

    /**
     * @link createStringUrl that will resposible for creating the url ..
     */

    private static final String createStringUrl(){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .encodedAuthority("jobs.github.com")
                .appendPath("positions.json")
                .appendQueryParameter("description", "java")
                .appendQueryParameter("full_time","true")
                .appendQueryParameter("location", "sf");

        String url = builder.build().toString();
        return url;
    }

    /**
     * Constant value for the newsloader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */

    private static final int SEARCH_LOADER = 1;

    // Adapter for the listof seach jobs

    private AndroidJobsAdapter mSfAdapter;

    private TextView mEmptyTextView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_android_jobs, container, false);
        ListView listView = (ListView)root.findViewById(R.id.list);

        mEmptyTextView = (TextView)root.findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyTextView);
        mSfAdapter = new AndroidJobsAdapter(getContext(), new ArrayList<JobsForAndroid>());

        listView.setAdapter(mSfAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // fiest find the current Search
                JobsForAndroid jobsForAndroid = mSfAdapter.getItem(position);

                //convert the string Url into uri object (to pass into the intent constructor)

                Uri searchUri = Uri.parse(jobsForAndroid.getmJobUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, searchUri);
                startActivity(intent);
            }
        });

        // Get the refernce of ConnectivityManager to check the state of the Network
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on tthe cuurently active default  data network..
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // if there is a network connection, fetch data

        // if there is a network connection, fetch data

        if (networkInfo != null && networkInfo.isConnected()){

            // Get the refernece of rhe Loader manager to ionteract with the loader

            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            Log.i(LOG_TAG, "Test:calling initLoader() ...");
            loaderManager.initLoader(SEARCH_LOADER, null, this);

        } else {
            // other wise display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = root.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
        return root;
//        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public Loader<List<JobsForAndroid>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "Test: onCreateLoader() is called");
//        // Create a new loader for the given URL
//        return new JobsLoader(getContext(), createStringUrl());
        return new AndroidJobsLoader(getContext(), createStringUrl());
    }

    @Override
    public void onLoadFinished(Loader<List<JobsForAndroid>> loader, List<JobsForAndroid> jobsForAndroidList) {

        Log.i(LOG_TAG, "Test: onLoadFinished() called");
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);


        // set Empty State
        mEmptyTextView.setText(R.string.no_jobs_found);

        // clear the adapter of the previous earh quake data

        if (jobsForAndroidList != null && !jobsForAndroidList.isEmpty()){
            mSfAdapter.addAll(jobsForAndroidList);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<JobsForAndroid>> loader) {

        Log.i(LOG_TAG, "Test: onLoaderReset called");
        // Loader reset, so we can clear out our existing data.
        mSfAdapter.clear();
    }
}
