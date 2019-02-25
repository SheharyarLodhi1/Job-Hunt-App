package com.next.evampintern.projectoneeands.Model;

/**
 * Created by sheharyar on 2/21/2019.
 */

public class JobsInSf {
    private String mJobType;
    private String mJobUrl;
    private String mCreatedDate;
    private String mCompanyUrl;
    private String mLoaction;
    private String mJobTitle;
    private String mJobDescription;
    private String mCompanyName;

    public JobsInSf(String type, String jobUrl, String createdDate, String companyName, String companyUrl, String location,
                    String jobTitle, String jobDescription){
        mJobType = type;
        mJobUrl = jobUrl;
        mCreatedDate = createdDate;
        mCompanyName = companyName;
        mCompanyUrl = companyUrl;
        mLoaction = location;
        mJobTitle = jobTitle;
        mJobDescription = jobDescription;

    }

    public String getmJobType() {
        return mJobType;
    }

    public void setmJobType(String mJobType) {
        this.mJobType = mJobType;
    }

    public String getmJobUrl() {
        return mJobUrl;
    }

    public void setmJobUrl(String mJobUrl) {
        this.mJobUrl = mJobUrl;
    }

    public String getmCreatedDate() {
        return mCreatedDate;
    }

    public void setmCreatedDate(String mCreatedDate) {
        this.mCreatedDate = mCreatedDate;
    }

    public String getmCompanyUrl() {
        return mCompanyUrl;
    }

    public void setmCompanyUrl(String mCompanyUrl) {
        this.mCompanyUrl = mCompanyUrl;
    }

    public String getmLoaction() {
        return mLoaction;
    }

    public void setmLoaction(String mLoaction) {
        this.mLoaction = mLoaction;
    }

    public String getmJobTitle() {
        return mJobTitle;
    }

    public void setmJobTitle(String mJobTitle) {
        this.mJobTitle = mJobTitle;
    }

    public String getmJobDescription() {
        return mJobDescription;
    }

    public void setmJobDescription(String mJobDescription) {
        this.mJobDescription = mJobDescription;
    }

    public String getmCompanyName() {
        return mCompanyName;
    }

    public void setmCompanyName(String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }
}
