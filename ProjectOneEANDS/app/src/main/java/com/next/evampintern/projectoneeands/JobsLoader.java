package com.next.evampintern.projectoneeands;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.next.evampintern.projectoneeands.Model.SearchJobs;

import java.util.List;

/**
 * Created by sheharyar on 2/20/2019.
 */

public class JobsLoader extends AsyncTaskLoader<List<SearchJobs>> {

    /** Tag for log messages */
    private static final String LOG_TAG = JobsLoader.class.getName();
    // Query Url
    private String mUrl;

    /**
     * Constructs a new {@link JobsLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */

    public JobsLoader(Context context, String url) {
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
    public List<SearchJobs> loadInBackground() {
        Log.i(LOG_TAG, "Test: loadInBackground is called");
        if (mUrl == null){
            return null;
        }

        // Perform the network request, parse the response and extract a list of jobs
        List<SearchJobs> searchJobs = QueryUtils.fetchSearchJobsList(mUrl);
        return searchJobs;
    }
}
