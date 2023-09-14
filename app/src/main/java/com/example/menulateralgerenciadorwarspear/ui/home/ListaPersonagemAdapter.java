package com.example.menulateralgerenciadorwarspear.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menulateralgerenciadorwarspear.R;
import com.example.menulateralgerenciadorwarspear.model.Personagem;

import java.util.List;

public class ListaPersonagemAdapter extends RecyclerView.Adapter<ListaPersonagemAdapter.PersonagemViewHolder> {

    private final List<Personagem> personagens;
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;

    public ListaPersonagemAdapter(List<Personagem> personagens, Context context) {
        this.personagens = personagens;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada= LayoutInflater.from((context))
                .inflate(R.layout.item_personagem,parent,false);
        return new PersonagemViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonagemViewHolder holder, int position) {
        Personagem personagem=personagens.get(position);
        holder.vincula(personagem);
    }

    @Override
    public int getItemCount() {
        return personagens.size();
    }

    public void remove(int position) {
        if (position < 0 || position >= personagens.size()) {
            return;
        }
        personagens.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, personagens.size());
        notifyDataSetChanged();
    }

    public class PersonagemViewHolder extends RecyclerView.ViewHolder{
        private final TextView nomePersonagem;
        private final CardView cardPersonagem;
        private Personagem personagem;

        public PersonagemViewHolder(@NonNull View itemView) {
            super(itemView);
            nomePersonagem=itemView.findViewById(R.id.itemNomePersonagem);
            cardPersonagem=itemView.findViewById(R.id.cardViewPersonagem);
        }

        public void vincula(Personagem personagem){
            this.personagem=personagem;
            preencheCampo(personagem);
        }

        private void preencheCampo(Personagem personagem) {
            nomePersonagem.setText(personagem.getNome());
            if (personagem.getEstado()){
                if (personagem.getUso()){
                    cardPersonagem.setCardBackgroundColor(ContextCompat.getColor(context,R.color.cor_texto_licenca_principiante));
                }else{
                    cardPersonagem.setCardBackgroundColor(ContextCompat.getColor(context,R.color.cor_background_escuro));
                }
            }else{
                cardPersonagem.setCardBackgroundColor(ContextCompat.getColor(context,R.color.cor_texto_licenca_iniciante));
            }
        }
    }
}