package com.matheusdelgado.moviel;

import java.util.List;

public interface MoviesCallb {

    void onSuccess(List<Movie> movies);

    void onError();
}
