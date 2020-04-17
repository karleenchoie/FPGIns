package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fpgins.DataModel.ClientNameData;
import com.example.fpgins.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientListFragment extends Fragment {

    private ImageView mBackButton;
    private ArrayList<ClientNameData> mClientNameData;
    private RecyclerView mRecyclerView;
    private ClientNamesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_list, container, false);

        createExampleList();
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new ClientNamesAdapter(mClientNameData,getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        EditText editText = view.findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().trim());
            }
        });



        return view;
    }

    private void filter(String text) {
        ArrayList<ClientNameData> filteredList = new ArrayList<>();

        for (ClientNameData item : mClientNameData) {
            if (item.getClientName().toLowerCase().contains(text.toLowerCase())
                    ||item.getClientAddress().toLowerCase().contains(text.toLowerCase())
                    ||item.getClientPolicy().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void createExampleList() {
        mClientNameData = new ArrayList<>();
        mClientNameData.add(new ClientNameData("Karleen Choie Galicia", "CLT0850007000031", "ACTIVE"));
        mClientNameData.add(new ClientNameData("Maximo Sevidal", "CLT0940006000022", "ACTIVE"));
        mClientNameData.add(new ClientNameData("Jeffrey Dimla", "CLT0850007900039",  "PENDING"));
        mClientNameData.add(new ClientNameData("Hector Thomas Javier", "CLT0130007000075",  "ACTIVE"));
        mClientNameData.add(new ClientNameData("Matthew Dominic Estive", "CLT0930217000090" , "PENDING"));
        mClientNameData.add(new ClientNameData("Ivan Eubans", "CLT0930217008990" , "ACTIVE"));
        mClientNameData.add(new ClientNameData("Christian Grande", "CLT0850007900659", "PENDING"));
        mClientNameData.add(new ClientNameData("Aldrene Victor Atienza", "CLT0850007907030", "ACTIVE"));
        mClientNameData.add(new ClientNameData("Gabrielle Mohan", "CLT0850007000041", "ACTIVE"));
    }

}