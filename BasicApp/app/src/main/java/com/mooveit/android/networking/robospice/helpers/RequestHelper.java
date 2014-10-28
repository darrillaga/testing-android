package com.mooveit.android.networking.robospice.helpers;


public class RequestHelper {

    public static RequestHelper instance;
    public static String domain;

    private RequestHelper(String domain){
        this.domain = domain;
    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            throw new RuntimeException("You must initialize it before call getInstance");
        }
        return instance;
    }

    public static void init(String domain){
        if (instance == null){
            instance = new RequestHelper(domain);
        }
    }

    public  String createUrlForRequest() {
        return createUrlForRequest(domain, null, null);
    }


    public String createUrlForRequest(String endPoint){
        return createUrlForRequest(domain, endPoint, null);
    }

    public String createUrlForRequest(String end_point, String params) {
        return createUrlForRequest(
                domain,
                end_point,
                params
        );
    }

    public String createUrlForRequest(String domain, String endPoint, String params){
        String url = "";

        if (domain != null){
            url += domain;
        }
        else{
            url += this.domain;
        }

        if (endPoint != null) {
            url += endPoint;
        }

        if (params != null){
            url += "?" + params;
        }

        return url;
    }


    public void setDomain(String domain){
        this.domain = domain;
    }





}
