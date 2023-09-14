package com.example.menulateralgerenciadorwarspear.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menulateralgerenciadorwarspear.databinding.FragmentHomeBinding;
import com.example.menulateralgerenciadorwarspear.model.Personagem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListaPersonagemAdapter personagemAdapter;
    private List<Personagem> personagens;
    private ProgressDialog progressDialog;
    private FirebaseAuth minhaAutenticacao;
    private DatabaseReference minhaReferencia;
    private RecyclerView recyclerView;
    private String[] mensagens={"Carregando dados...","Erro de conexão..."};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListaPersonagemAdapter homeViewModel =
                new ViewModelProvider(this).get(ListaPersonagemAdapter.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}