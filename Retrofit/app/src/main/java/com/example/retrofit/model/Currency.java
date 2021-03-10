package com.example.retrofit.model;
/*
{
        "success":true,
        "terms":"https:\/\/currencylayer.com\/terms",
        "privacy":"https:\/\/currencylayer.com\/privacy",
        "timestamp":1615255987,
        "source":"USD",
        "quotes":{
                    "USDVND":23091.217786
                 }
}
*/

public class Currency {
    //Name variable need to be same key of json file
    private boolean success;
    private String terms;
    private String privacy;
    private int timestamp;
    private String source;
    private Quotes quotes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Quotes getQuotes() {
        return quotes;
    }

    public void setQuotes(Quotes quotes) {
        this.quotes = quotes;
    }
}
