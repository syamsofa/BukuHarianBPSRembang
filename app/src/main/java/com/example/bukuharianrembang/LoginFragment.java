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
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.bukuharianrembang.databinding.FragmentLoginBinding;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static final String session = "session";


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

//        AndroidNetworking.initialize(getApplicationContext());

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                EditText username = binding.etUsername;
                EditText password = binding.etPassword;

                // Then set the JacksonParserFactory like below
                AndroidNetworking.setParserFactory(new JacksonParserFactory());
                AndroidNetworking.post(Konfigurasi.jatengKlikLoginApi)
                        .addBodyParameter("username", username.getText().toString())
                        .addBodyParameter("password", password.getText().toString())
                        .setTag("test")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                System.out.println(response);
                                try {
                                    Boolean login = response.getBoolean("login");

                                    if (login.equals(true)) {
                                        pref = getActivity().getSharedPreferences(session, Context.MODE_PRIVATE);
                                        editor = pref.edit();
                                        editor.putString("getUsername", response.getString("username"));
                                        editor.putString("getNama", response.getString("nama"));
                                        editor.putString("getNipLama", response.getString("niplama"));
                                        editor.putString("getNipBaru", response.getString("nipbaru"));
                                        editor.putString("getEmail", response.getString("email"));
                                        editor.putString("getAvatar", response.getString("avatar"));
                                        editor.apply();


                                        NavHostFragment.findNavController(LoginFragment.this)
                                                .navigate(R.id.action_LoginFragment_to_MenuUtamaFragment);
                                    } else
                                        Toast.makeText(activity, "Kemungkinan Username Anda tidak terdaftar, atau anda salah memasukkan username dan password", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                                System.out.println(error);
                            }
                        });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
