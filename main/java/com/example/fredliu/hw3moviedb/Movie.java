package com.example.fredliu.hw3moviedb;

/**
 * Created by fredliu on 10/13/17.
 */

public class Movie {


    private String title;
    private String date;
    private String rating;
    private String popularity;
    private String overview;
    private String poster;
    private String likeButton;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setLikeButton(String likeButton) {
        this.likeButton = likeButton;
    }

    public String getLikeButton() {

        return likeButton;
    }

    public String getPoster() {

        return poster;
    }

    public String getOverview() {

        return overview;
    }

    public String getPopularity() {

        return popularity;
    }

    public String getTitle() {

        return title;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }
}