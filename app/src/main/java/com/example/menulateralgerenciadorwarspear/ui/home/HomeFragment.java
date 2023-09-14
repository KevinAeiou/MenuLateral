package com.example.menulateralgerenciadorwarspear.ui.home;

import static com.example.menulateralgerenciadorwarspear.ui.WarspearActivityConstantes.CHAVE_PERSONAGEM;
import static com.example.menulateralgerenciadorwarspear.ui.WarspearActivityConstantes.CHAVE_USUARIOS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menulateralgerenciadorwarspear.R;
import com.example.menulateralgerenciadorwarspear.databinding.FragmentHomeBinding;
import com.example.menulateralgerenciadorwarspear.model.Personagem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListaPersonagemAdapter personagemAdapter;
    private List<Personagem> personagens;
    private FirebaseAuth minhaAutenticacao;
    private FirebaseDatabase database;
    private DatabaseReference minhaReferencia;
    private RecyclerView recyclerView;
    private String usuarioId;
    private String[] mensagens={"Carregando dados...","Erro de conexão..."};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        minhaAutenticacao=FirebaseAuth.getInstance();
        usuarioId= Objects.requireNonNull(minhaAutenticacao.getCurrentUser()).getUid();
        database=FirebaseDatabase.getInstance();
        minhaReferencia=database.getReference(CHAVE_USUARIOS);
        recyclerView= view.findViewById(R.id.listaPersonagensRecyclerView);

        pegaTodosPersonagens();
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        personagemAdapter=new ListaPersonagemAdapter(personagens,getActivity());
        recyclerView.setAdapter(personagemAdapter);
        //configuraRecyclerView(todosPersonagens);

        //ListaPersonagemAdapter homeViewModel =
        //        new ViewModelProvider(this).get(ListaPersonagemAdapter.class);


        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return view;
    }

    private void atualizaListaPersonagem() {
        pegaTodosPersonagens();
        configuraRecyclerView(personagens);
    }

    private void configuraRecyclerView(List<Personagem> todosPersonagens) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Collections.sort(todosPersonagens, (p1, p2) -> p1.getNome().compareToIgnoreCase(p2.getNome()));
        configuraAdapter(todosPersonagens,recyclerView);
    }

    private void configuraAdapter(List<Personagem> todosPersonagens, RecyclerView recyclerView) {
        personagemAdapter=new ListaPersonagemAdapter(todosPersonagens,getActivity());
        recyclerView.setAdapter(personagemAdapter);
    }

    private void pegaTodosPersonagens() {
        personagens=new ArrayList<>();
        minhaReferencia.child(usuarioId).child(CHAVE_PERSONAGEM).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        personagens.clear();
                        for (DataSnapshot dn:snapshot.getChildren()){
                            Personagem personagem = dn.getValue(Personagem.class);
                            personagens.add(personagem);
                        }
                        personagemAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}