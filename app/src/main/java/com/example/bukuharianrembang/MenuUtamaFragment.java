package com.example.bukuharianrembang;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.bukuharianrembang.databinding.FragmentLoginBinding;
import com.example.bukuharianrembang.databinding.FragmentMenuUtamaBinding;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuUtamaFragment extends Fragment {
    private FragmentMenuUtamaBinding binding;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static final String session = "session";
    String getNama;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMenuUtamaBinding.inflate(inflater, container, false);
        return binding.getRoot();

//        AndroidNetworking.initialize(getApplicationContext());

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView welcome = binding.tvWelcome;
        getDataPref();
        welcome.setText("Hai " + getNama);
        binding.ivEntriAktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MenuUtamaFragment.this)
                        .navigate(R.id.action_MenuUtamaFragment_to_EntriAktivitasFragment);



                // Then set the JacksonParserFactory like below
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void getDataPref() {
        pref = getActivity().getSharedPreferences(session, Context.MODE_PRIVATE);
        getNama = pref.getString("getNama", "-");

    }
}

