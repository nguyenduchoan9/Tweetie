package com.codepath.apps.restclienttemplate.common;

/**
 * Created by Nguyen.D.Hoang on 10/27/2016.
 */

public class SearchRequest {
    private int page;

    public int getPage() {
        return page;
    }

    public SearchRequest() {
        this.page = 0;
    }

    public void resetPage(){
        this.page = 0;
    }
    public void nextPage(){
        page +=  1;
    }
}
