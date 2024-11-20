package com.example.ac2dispmoveis;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroAluno extends AppCompatActivity {

    private EditText raInput, nomeInput, cepInput, logradouroInput, complementoInput, bairroInput, cidadeInput, ufInput;
    private Button btnCadastrar;

    private APIAluno apiAluno;
    private ViaCepAPI viaCepApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        raInput = findViewById(R.id.ra);
        nomeInput = findViewById(R.id.name);
        cepInput = findViewById(R.id.cep);
        logradouroInput = findViewById(R.id.rua);
        complementoInput = findViewById(R.id.complemento);
        bairroInput = findViewById(R.id.bairro);
        cidadeInput = findViewById(R.id.cidade);
        ufInput = findViewById(R.id.uf);
        btnCadastrar = findViewById(R.id.buttonCadastrar);


        Retrofit retrofitAluno = new Retrofit.Builder()
                .baseUrl("https://673e39e70118dbfe860a92c7.mockapi.io/api/alunos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiAluno = retrofitAluno.create(APIAluno.class);

        Retrofit retrofitViaCep = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepApi = retrofitViaCep.create(ViaCepAPI.class);


        btnCadastrar.setOnClickListener(view -> registrarAluno());

        cepInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus && !cepInput.getText().toString().isEmpty()) {
                buscarEndereco(cepInput.getText().toString().replace("-", ""));
            }
        });
    }

    private void buscarEndereco(String cep) {
        viaCepApi.getAddress(cep).enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Endereco endereco = response.body();
                    logradouroInput.setText(endereco.getLogradouro());
                    complementoInput.setText(endereco.getComplemento());
                    bairroInput.setText(endereco.getBairro());
                    cidadeInput.setText(endereco.getLocalidade());
                    ufInput.setText(endereco.getUf());
                } else {
                    Toast.makeText(CadastroAluno.this, "CEP não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(CadastroAluno.this, "Erro ao buscar endereço.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarAluno() {

        String ra = raInput.getText().toString();
        String nome = nomeInput.getText().toString();
        String cep = cepInput.getText().toString();
        String logradouro = logradouroInput.getText().toString();
        String complemento = complementoInput.getText().toString();
        String bairro = bairroInput.getText().toString();
        String cidade = cidadeInput.getText().toString();
        String uf = ufInput.getText().toString();


        if (ra.isEmpty() || nome.isEmpty() || cep.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }


        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);


        apiAluno.createAluno(aluno).enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CadastroAluno.this, "Aluno cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroAluno.this, "Erro ao cadastrar aluno. Verifique os dados.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(CadastroAluno.this, "Erro ao conectar com o servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
