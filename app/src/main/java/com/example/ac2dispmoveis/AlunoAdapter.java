package com.example.ac2dispmoveis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private List<Aluno> alunoList;

    public AlunoAdapter(List<Aluno> alunoList) {
        this.alunoList = alunoList;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aluno_item_view, parent, false); // Assuming 'item_aluno' is your layout file
        return new AlunoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunoList.get(position);
        holder.textViewRA.setText(aluno.getRa());
        holder.textViewNome.setText(aluno.getNome());
        holder.textViewCep.setText(aluno.getCep());
        holder.textViewRua.setText(aluno.getLogradouro());
        holder.textViewComplemento.setText(aluno.getBairro());
        holder.textViewBairro.setText(aluno.getBairro());
        holder.textViewCidade.setText(aluno.getCidade());
        holder.textViewUf.setText(aluno.getUf());
    }

    @Override
    public int getItemCount() {
        return alunoList.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRA;
        public TextView textViewNome;
        public TextView textViewCep;
        public TextView textViewRua;
        public TextView textViewComplemento;
        public TextView textViewBairro;
        public TextView textViewCidade;
        public TextView textViewUf;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRA = itemView.findViewById(R.id.textViewRa);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewCep = itemView.findViewById(R.id.textViewCEP);
            textViewRua = itemView.findViewById(R.id.textViewRua);
            textViewComplemento = itemView.findViewById(R.id.textViewComplemento);
            textViewBairro = itemView.findViewById(R.id.textViewBairro);
            textViewCidade = itemView.findViewById(R.id.textViewCidade);
            textViewUf = itemView.findViewById(R.id.textViewUf);
        }
    }
}