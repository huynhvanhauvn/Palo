package com.sbro.palo.Fragments.HomeFragment;

import com.sbro.palo.Models.Movie;

import dagger.Component;

@Component
public interface HomeComponent {
    Movie initMovie();
}
