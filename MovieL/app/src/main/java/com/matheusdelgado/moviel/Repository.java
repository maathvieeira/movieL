package com.matheusdelgado.moviel;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";

    private static Repository repository;

    private MovieService service;

    private Repository(MovieService service) {
        this.service = service;
    }

    public static Repository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new Repository(retrofit.create(MovieService.class));
        }

        return repository;
    }

    public void getMovies(final MoviesCallb callback) {
        service.getPopularMovies("765bce083e22f8fd78ef131558b9a82d", LANGUAGE, 1)
                .enqueue(new Callback<Response>() {

                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            Response moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}
