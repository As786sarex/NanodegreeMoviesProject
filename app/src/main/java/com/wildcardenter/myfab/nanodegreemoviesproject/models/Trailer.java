package com.wildcardenter.myfab.nanodegreemoviesproject.models;

/*
                                #  #           #  #     
    Created by Asif Mondal on 05-05-2020 at 19:01
*/


public class Trailer {

    private String key;
    private String name;
    private String site;

    public Trailer(String key, String name, String site) {
        this.key = key;
        this.name = name;
        this.site = site;
    }

    public Trailer() {
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }
}
