package com.example.ac2dispmoveis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIAluno {
    @GET("alunos")
    Call<List<Aluno>> getAlunos();

    @POST("alunos")
    Call<Aluno> createAluno(@Body Aluno aluno);
}