package com.example.ac2dispmoveis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaAlunosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAlunos;
    private AlunoAdapter alunoAdapter;
    private APIAluno APIAluno;
    private List<Aluno> alunoList = new ArrayList<>();
    private Button btnCadastro, btnAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        btnCadastro = findViewById(R.id.buttonCadastro);
        btnAtualizar = findViewById(R.id.buttonAtualizar);
        recyclerViewAlunos = findViewById(R.id.recyclerViewAlunos);

        recyclerViewAlunos.setLayoutManager(new LinearLayoutManager(this));
        alunoAdapter = new AlunoAdapter(alunoList); // Inicializa o adapter com uma lista vazia
        recyclerViewAlunos.setAdapter(alunoAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://673e39e70118dbfe860a92c7.mockapi.io/api/alunos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIAluno = retrofit.create(APIAluno.class);


        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(ListaAlunosActivity.this, CadastroAluno.class);
            startActivity(intent);
        });

        btnAtualizar.setOnClickListener(v -> fetchStudentData());

        fetchStudentData();
    }

    private void fetchStudentData() {
        Call<List<Aluno>> call = APIAluno.getAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alunoList.clear(); // Limpa a lista atual
                    alunoList.addAll(response.body()); // Adiciona os novos dados
                    alunoAdapter.notifyDataSetChanged(); // Atualiza o adapter
                } else {
                    Toast.makeText(ListaAlunosActivity.this, "Erro ao buscar alunos: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(ListaAlunosActivity.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Network Error", t.getMessage(), t);
            }
        });
    }
}
