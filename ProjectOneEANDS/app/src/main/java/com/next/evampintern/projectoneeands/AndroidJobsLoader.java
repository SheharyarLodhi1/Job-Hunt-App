package com.next.evampintern.projectoneeands;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.next.evampintern.projectoneeands.Adapter.AndroidJobsAdapter;
import com.next.evampintern.projectoneeands.Model.JobsForAndroid;
import com.next.evampintern.projectoneeands.Model.SearchJobs;

import java.util.List;

/**
 * Created by sheharyar on 2/21/2019.
 */

public class AndroidJobsLoader extends AsyncTaskLoader<List<JobsForAndroid>> {

    /** Tag for log messages */
    private static final String LOG_TAG = AndroidJobsAdapter.class.getName();
    // Query Url
    private String mUrl;

    /**
     * Constructs a new {@link JobsLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */

    public AndroidJobsLoader(Context context, String url) {

        super(context);
        mUrl = url;
        Log.i(LOG_TAG, "Errors: ");
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "Test: onStartLoading is called");
        forceLoad();
    }

    @Override
    public List<JobsForAndroid> loadInBackground() {
        Log.i(LOG_TAG,"loadInBackground is called");
        if (mUrl == null){
            return null;
        }

        // Perform the network request, parse the response and extract a list of jobs
        List<JobsForAndroid> searchJobs = QueryUtilsForAndroidJobs.fetchSfJobsList(mUrl);
        return searchJobs;
    }
}
