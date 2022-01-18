package com.example.bukuharianrembang;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.bukuharianrembang.databinding.FragmentEntriAktivitasBinding;
import com.example.bukuharianrembang.databinding.FragmentLoginBinding;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class EntriAktivitasFragment extends Fragment {
    private FragmentEntriAktivitasBinding binding;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static final String session = "session";


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEntriAktivitasBinding.inflate(inflater, container, false);
        return binding.getRoot();

//        AndroidNetworking.initialize(getApplicationContext());

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
