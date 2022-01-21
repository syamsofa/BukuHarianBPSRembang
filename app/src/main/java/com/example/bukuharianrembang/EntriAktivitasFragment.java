package com.example.bukuharianrembang;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EntriAktivitasFragment extends Fragment {
    private FragmentEntriAktivitasBinding binding;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static final String session = "session";
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar myCalendar;
    SimpleDateFormat dateFormatter;
    String getNama;

    String getUsername;

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

        getDataPref();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        Date dateNow = new Date();
        System.out.println(dateFormatter.format(dateNow));
        binding.etTanggal.setText(dateFormatter.format(dateNow));

        binding.etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        System.out.println(dateFormatter.format(newDate.getTime()));
                        binding.etTanggal.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, myCalendar.get((Calendar.YEAR)), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        binding.etWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();

                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newDate = Calendar.getInstance();
                        System.out.println(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

                        binding.etWaktu.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

                    }
                }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext()));

                timePickerDialog.show();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();

                // Then set the JacksonParserFactory like below
                AndroidNetworking.setParserFactory(new JacksonParserFactory());
                AndroidNetworking.post(Konfigurasi.serverApiDev + "create_kegiatan_harian")
                        .addBodyParameter("Tanggal", binding.etTanggal.getText().toString() + " " + binding.etWaktu.getText().toString())
                        .addBodyParameter("Kegiatan", binding.etKegiatan.getText().toString())
                        .addBodyParameter("Username", getUsername)
                        .setTag("test")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                System.out.println(response);
                                try {
                                    Boolean sukses = response.getBoolean("sukses");

                                    if (sukses.equals(true))
                                    {
                                        Toast.makeText(getContext(), "Berhasil Simpan Data", Toast.LENGTH_SHORT).show();
                                        NavHostFragment.findNavController(EntriAktivitasFragment.this)
                                                .navigate(R.id.action_EntriAktivitasFragment_to_MenuUtamaFragment);
                                    }
                                        else
                                        Toast.makeText(getContext(), "Tidak berhasil Simpan Data", Toast.LENGTH_SHORT).show();
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

    void getDataPref() {
        pref = getActivity().getSharedPreferences(session, Context.MODE_PRIVATE);
        getNama = pref.getString("getNama", "-");
        getUsername = pref.getString("getUsername", "-");

    }

}
