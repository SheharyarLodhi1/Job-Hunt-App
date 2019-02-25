package com.next.evampintern.projectoneeands.Fragments;

import android.support.v4.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.next.evampintern.projectoneeands.Adapter.SearchAdapter;
import com.next.evampintern.projectoneeands.JobsLoader;
import com.next.evampintern.projectoneeands.Model.SearchJobs;
import com.next.evampintern.projectoneeands.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sheharyar on 2/20/2019.
 */

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<SearchJobs>> {

    private static final String LOG_TAG = SearchFragment.class.getName();
    EditText edtSearchTxt;
    private static ArrayList<SearchJobs> searchList = new ArrayList<>();
    private ArrayList<SearchJobs> list = new ArrayList<>();


    public SearchFragment(){

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
                .appendQueryParameter("description", "python")
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

    private SearchAdapter mSearchAdapter;

    private TextView mEmptyTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);


        ListView listView = (ListView)rootView.findViewById(R.id.list);
        edtSearchTxt = (EditText)rootView.findViewById(R.id.searchBar);
        edtSearchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SearchFragment.this.mSearchAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = edtSearchTxt.getText().toString();

                list = search(text);

            }
        });
        mEmptyTextView = (TextView)rootView.findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyTextView);
        mSearchAdapter = new SearchAdapter(getContext(), new ArrayList<SearchJobs>());
        listView.setAdapter(mSearchAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // fiest find the current Search
                SearchJobs searchJobs = mSearchAdapter.getItem(position);

                //convert the string Url into uri object (to pass into the intent constructor)

                Uri searchUri = Uri.parse(searchJobs.getmJobUrl());
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
            View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
        return rootView;
//        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    private ArrayList<SearchJobs> search(String text) {
         list = new ArrayList<>();

        for (SearchJobs searchJobs :  searchList){

            if (searchJobs.getmJobTitle().contains(text)){
                list.add(searchJobs);

                mSearchAdapter = new SearchAdapter(getContext(), list);
                ListView listView = (ListView)getActivity().findViewById(R.id.list);
                listView.setAdapter(mSearchAdapter);
                mSearchAdapter.notifyDataSetChanged();
            }

//           searchJobs.getmJobTitle().contains(text);

//            list.add(searchJobs);
        }
        return list;
    }

//    @Override
//    public Loader<List<SearchJobs>> onCreateLoader(int i, Bundle bundle) {
//        Log.i(LOG_TAG, "Test: onCreateLoader() is called");
//        // Create a new loader for the given URL
//        return new JobsLoader(getContext(), createStringUrl());
//    }


    @Override
    public android.support.v4.content.Loader<List<SearchJobs>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "Test: onCreateLoader() is called");
//        // Create a new loader for the given URL
//        return new JobsLoader(getContext(), createStringUrl());
        return new JobsLoader(getContext(), createStringUrl());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<SearchJobs>> loader, List<SearchJobs> searchJobsList) {
        Log.i(LOG_TAG, "Test: onLoadFinished() called");
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);


        // set Empty State
        mEmptyTextView.setText(R.string.no_jobs_found);

        // clear the adapter of the previous earh quake data

        if (searchJobsList != null && !searchJobsList.isEmpty()){
            mSearchAdapter.addAll(searchJobsList);
            searchList = (ArrayList<SearchJobs>) searchJobsList;
//            ((ArrayList<SearchJobs>) searchJobsList).addAll(searchList);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<SearchJobs>> loader) {

        Log.i(LOG_TAG, "Test: onLoaderReset called");
        // Loader reset, so we can clear out our existing data.
        mSearchAdapter.clear();
    }

//    @Override
//    public void onLoadFinished(Loader<List<SearchJobs>> loader, List<SearchJobs> searchJobsList) {
//
//        Log.i(LOG_TAG, "Test: onLoadFinished() called");
//        // Hide loading indicator because the data has been loaded
//        View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
//        loadingIndicator.setVisibility(View.GONE);
//
//
//        // set Empty State
//        mEmptyTextView.setText(R.string.no_jobs_found);
//
//        // clear the adapter of the previous earh quake data
//
//        if (searchJobsList != null && !searchJobsList.isEmpty()){
//            mSearchAdapter.addAll(searchJobsList);
//        }
//    }

//    @Override
//    public void onLoaderReset(Loader<List<SearchJobs>> loader) {
//
//        Log.i(LOG_TAG, "Test: onLoaderReset called");
//        // Loader reset, so we can clear out our existing data.
//        mSearchAdapter.clear();
//    }
}
