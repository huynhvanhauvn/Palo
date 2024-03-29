package com.hhub.palo.Activities.SearchActivity;

public interface SearchInterface {
    void getPopularTags();
    void updateKeyword(String keyword);
    void search(String keyword);
    void searchArtist(String keyword);
    void showBackground(String language);
}
