package com.next.evampintern.projectoneeands;

import android.text.TextUtils;
import android.util.Log;

import com.next.evampintern.projectoneeands.Model.JobsForAndroid;
import com.next.evampintern.projectoneeands.Model.JobsInSf;
import com.next.evampintern.projectoneeands.Model.SearchJobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sheharyar on 2/21/2019.
 */

public class QueryUtilsForAndroidJobs {

    private static final String QUERY_TAG_Android = QueryUtilsForAndroidJobs.class.getName();

    private QueryUtilsForAndroidJobs(){}

    /**
     * Query the SearchJobs dataset and return a list of {@link SearchJobs} objects.
     */

    public static List<JobsForAndroid> fetchSfJobsList(String requestUrl){
        Log.i(QUERY_TAG_Android, "Test: fetchSfJobsList is called");

        URL url = createUrl(requestUrl);
        // perform the https request ..
        String jsonResponse = null;
        try{

            jsonResponse = makeHttpsRequest(url);
        }catch (IOException e){

            Log.i(QUERY_TAG_Android,"Problem Taking the http request", e);
        }

        /**
         *  extract the relevant fields from the json Response and create a list a list of {@Link SearchJobs }
         *
         */
        List<JobsForAndroid> list = extractDataFromJson(jsonResponse);
        return list;
    }

    private static List<JobsForAndroid> extractDataFromJson(String jobsJson) {
        // if the json string is empty or null, then return early
        if (TextUtils.isEmpty(jobsJson)){
            return null;
        }
        // create an empty arrayList that we can start adding jobs to it ..

        List<JobsForAndroid> jobsInSf = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {

            JSONArray resultOfJsonArray = new JSONArray(jobsJson);
            for (int i = 0; i < resultOfJsonArray.length(); i++){
                JSONObject objJson = resultOfJsonArray.getJSONObject(i);
                String type = objJson.getString("type");
                String url = objJson.getString("url");
                String created_at = objJson.getString("created_at");
//                created_at = formatDate(created_at);
                String company = objJson.getString("company");
                String company_url = objJson.getString("company_url");
                String location = objJson.getString("location");
                String title = objJson.getString("title");
                String description = objJson.getString("description");
                String companyIcon = objJson.getString("company_logo");

                jobsInSf.add(new JobsForAndroid(type, url, created_at, company, company_url, location, title, description, companyIcon));
            }

        }catch (JSONException j){
            Log.i(QUERY_TAG_Android,"There is a problem while parsing the json", j);
        }

        return jobsInSf;
    }

    private static String makeHttpsRequest(URL url) throws IOException {

        String jsonResponse = "";

        // if the url is null than return early ..

        if (url == null){
            return jsonResponse;
        }

        HttpsURLConnection httpsURLConnection = null;
        InputStream inputStream = null;

        try{
            httpsURLConnection = (HttpsURLConnection)url.openConnection();
            httpsURLConnection.setReadTimeout(10000);
            httpsURLConnection.setConnectTimeout(15000);
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();

            // if the request was successful then response code will be 200
            // and then read the input stream

            if (httpsURLConnection.getResponseCode() == 200){
                inputStream = httpsURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(QUERY_TAG_Android, "Error response code: " + httpsURLConnection.getResponseCode());
            }
        }catch (IOException io){
            Log.i(QUERY_TAG_Android,"Erro while creating Url", io);{

            }
        } finally {
            if (httpsURLConnection != null){
                httpsURLConnection.disconnect();
            }

            if (inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
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
            Log.i(QUERY_TAG_Android, "Error in create Url: ", m);
        }

        return url;
    }
}
