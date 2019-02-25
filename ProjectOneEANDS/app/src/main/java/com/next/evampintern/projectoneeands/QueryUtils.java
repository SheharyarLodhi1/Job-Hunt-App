package com.next.evampintern.projectoneeands;

/**
 * Created by sheharyar on 2/20/2019.
 */
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.next.evampintern.projectoneeands.Model.SearchJobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class QueryUtils {

    // tag for the log messages

    private static final String QUERY_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {

    }

    /**
     * Query the SearchJobs dataset and return a list of {@link SearchJobs} objects.
     */

    public static List<SearchJobs> fetchSearchJobsList(String requestUrl){
        Log.i(QUERY_TAG, "Test: fetchSearchJobsList is called");

        // create a url object

        URL url = createUrl(requestUrl);

        // perform the http request ..
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.i(QUERY_TAG,"Problem Taking the http request", e);
        }

        /**
         *  extract the relevant fields from the json Response and create a list a list of {@Link SearchJobs }
         *
          */
        List<SearchJobs> searchJobs = extractJobsFromJson(jsonResponse);

        return searchJobs;
    }

    private static List<SearchJobs> extractJobsFromJson(String jobsJson) {
        // if the json string is empty or null, then return early
        if (TextUtils.isEmpty(jobsJson)){
            return null;
        }

        // create an empty ArrayList that we can start adding Jobs to it..

        List<SearchJobs> searchJobs = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try{
            // Create a JSONObject from the JSON response string
//            JSONObject jsonResponse = new JSONObject(jobsJson);
//            JSONObject jsonResults = jsonResponse.getJSONArray("")
            JSONArray resultsArray = new JSONArray(jobsJson);

            for (int i = 0; i < resultsArray.length(); i++){
                JSONObject oneResult = resultsArray.getJSONObject(i);
                String type = oneResult.getString("type");
                String url = oneResult.getString("url");
                String created_at = oneResult.getString("created_at");
//                created_at = formatDate(created_at);
                String company = oneResult.getString("company");
                String company_url = oneResult.getString("company_url");
                String location = oneResult.getString("location");
                String title = oneResult.getString("title");
                String description = oneResult.getString("description");

                searchJobs.add(new SearchJobs(type, url, created_at, company, company_url, location, title, description));
            }
        }catch (JSONException e){
            Log.i(QUERY_TAG, "Problem parsing the Json", e);
        }

        return searchJobs;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // if the url is null then return early ..

        if (url == null){
            return jsonResponse;
        }

        HttpsURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try
        {
            httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            // if the request was successful then response code will be 200
            // and then read the input stream

            if (httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(QUERY_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }

        }catch (IOException e){
            Log.i(QUERY_TAG, "IO Exception Error : ", e);
        } finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    private static URL createUrl(String requestUrl) {

        URL url = null;

        try
        {
         url = new URL(requestUrl);
        }catch (MalformedURLException m){
            Log.i(QUERY_TAG, "Error in create Url: ", m);
        }

        return url;
    }

    private static String formatDate(String rawDate) {
        String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
        try {
            Date parsedJsonDate = jsonFormatter.parse(rawDate);
            String finalDatePattern = "MMM d, yyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
            return finalDateFormatter.format(parsedJsonDate);
        } catch (ParseException e) {
            Log.e(QUERY_TAG, "Error parsing JSON date: ", e);
            return "";
        }
    }
}
