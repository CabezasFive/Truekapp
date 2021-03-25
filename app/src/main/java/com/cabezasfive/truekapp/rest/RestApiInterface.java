package com.cabezasfive.truekapp.rest;

import com.cabezasfive.truekapp.domain.PublicacionDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApiInterface {

    @GET("/publicaciones")
    Call<List<PublicacionDto>> getPublicaciones();

    @GET("/publicaciones/id")
    Call<PublicacionDto> getPublicacion(@Path("id") int id);

    @POST("/publicaciones")
    Call<ResponseBody> addPublicacion(@Query("titulo") String titulo, @Query("descripcion") String descripcion);

    @PUT("/publicaciones/{id}")
    Call<ResponseBody> updatePublicacion(@Path("id") int id, @Query("titulo") String titulo, @Query("descripcion") String descripcion);

    @DELETE("/publicaciones/{id}")
    Call<ResponseBody> deletePublicacion(@Path("id") int id);
}
