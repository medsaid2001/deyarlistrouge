package mr.gov.listerouge.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mr.gov.listerouge.R;
import mr.gov.listerouge.interfaces.FinishCallback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileFragment extends Fragment implements FinishCallback {

    private FinishCallback callback;
    private TextView nni, patronymeAr, patronymeFr, perePrenomAr, perePrenomFr, prenomAr, prenomFr, sexeCode, dateNaissance, lieuNaissanceAr;
    private ImageView photo;
    private View viewContainer;
    private SharedPreferences sharedPreferences;
    private View view;
    private static final String API_URL = "https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/getPersonne";
    private static final String API_KEY = "a96e90c5-d561-4a1d-8307-a22b8999cc9f";
    private static final String PREF_NNI = "nni";
    private static final String PREF_PERSONNE_DATA = "personne_data";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_profile, container, false);

        nni = viewContainer.findViewById(R.id.nni);
        patronymeAr = viewContainer.findViewById(R.id.patronyme_ar);
        patronymeFr = viewContainer.findViewById(R.id.patronyme_fr);
        perePrenomAr = viewContainer.findViewById(R.id.pere_prenom_ar);
        perePrenomFr = viewContainer.findViewById(R.id.pere_prenom_fr);
        prenomAr = viewContainer.findViewById(R.id.prenom_ar);
        prenomFr = viewContainer.findViewById(R.id.prenom_fr);
        sexeCode = viewContainer.findViewById(R.id.sexe_code);
        dateNaissance = viewContainer.findViewById(R.id.date_naissance_ar2);
        lieuNaissanceAr = viewContainer.findViewById(R.id.lieu_naissance_ar2);
        photo = viewContainer.findViewById(R.id.photo);
        return viewContainer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callback = this;
        sharedPreferences = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String nni = sharedPreferences.getString(PREF_NNI, null);
        if (nni != null) {
            String personneData = sharedPreferences.getString(PREF_PERSONNE_DATA, null);
            if (personneData != null) {
                parseAndSetDetails(personneData);
            } else {
                getpersonne(nni);
            }
        }
    }

    private void parseAndSetDetails(String responseString) {
        try {
            JSONObject responseObject = new JSONObject(responseString);
            JSONObject personne = responseObject.getJSONObject("personne");
            nni.setText(personne.getString("nni"));
            patronymeAr.setText("الإسم العائلي" + "  " + personne.getString("patronymeAr"));
            patronymeFr.setText("Patronyme  " + personne.getString("patronymeFr"));
            perePrenomAr.setText("إسم الأب" + "  " + personne.getString("perePrenomAr"));
            perePrenomFr.setText("Pere Prenom " + personne.getString("perePrenomFr"));
            prenomAr.setText("الإسم" + "  " + personne.getString("prenomAr"));
            prenomFr.setText("Prenom  " + personne.getString("prenomFr"));
            sexeCode.setText(personne.getString("sexeCode"));

            String lieu = personne.getString("lieuNaissanceFr") + "/" + personne.getString("lieuNaissanceAr");
            lieuNaissanceAr.setText(lieu);

            SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            try {
                String dn = personne.getString("dateNaissance");
                Date date = datetimeFormat.parse(dn);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(date);
                dateNaissance.setText(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String photoBase64 = responseObject.getString("photo");
            if (!photoBase64.isEmpty()) {
                byte[] decodedString = android.util.Base64.decode(photoBase64, android.util.Base64.DEFAULT);
                Glide.with(getContext()).load(decodedString).into(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String message) {
        requireActivity().runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(message)
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void getpersonne(String nni) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                JSONObject photoObject = new JSONObject();
                photoObject.put("nni", nni);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), photoObject.toString());
                Request request = new Request.Builder()
                        .url(API_URL)
                        .addHeader("entity-Api-Key", API_KEY)
                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    if (getActivity() == null)
                        return;
                    savePersonneData(responseBody);
                    getActivity().runOnUiThread(() -> parseAndSetDetails(responseBody));
                } else {
                    callback.onError("Error code: " + response.code());
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            }
        }).start();
    }

    private void savePersonneData(String responseString) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_PERSONNE_DATA, responseString);
        editor.apply();
    }
}
