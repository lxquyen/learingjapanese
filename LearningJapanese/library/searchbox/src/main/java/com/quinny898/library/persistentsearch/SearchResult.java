package com.quinny898.library.persistentsearch;

import java.io.Serializable;

public class SearchResult implements Serializable {
    public String title;
    public int icon;

    /**
     * Create a search result with text and an icon
     *
     * @param title
     * @param icon
     */
    public SearchResult(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public int viewType = 0;

    public SearchResult(String title) {
        this.title = title;
    }

    public SearchResult(int viewType, String title) {
        this.viewType = viewType;
        this.title = title;
    }

    /**
     * Return the title of the result
     */
    @Override
    public String toString() {
        return title;
    }

}