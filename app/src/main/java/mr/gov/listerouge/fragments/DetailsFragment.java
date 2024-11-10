package mr.gov.listerouge.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mr.gov.listerouge.R;
import mr.gov.listerouge.adapter.PersonDetailsAdapter;
import mr.gov.listerouge.interfaces.FinishCallback;
import mr.gov.listerouge.models.PersonDetailModel;
import mr.gov.listerouge.models.RedListItem;

public class DetailsFragment extends Fragment implements FinishCallback {

    private FinishCallback callback;
    private TextView rednni,condui,desc, nni, decisionview, scoreview, patronymeAr, patronymeFr, perePrenomAr, perePrenomFr, prenomAr, prenomFr, sexeCode, dateNaissance, lieuNaissanceAr, lieuNaissanceFr;
    private ImageView photo;
    String profile,op_nni,opphone;
    AppCompatButton redlist;
    private View viewContainer;
    CardView cardviewlist;
    SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private PersonDetailsAdapter redListAdapter;
    private List<PersonDetailModel> redListItems;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_details, container, false);
        if(sharedPreferences==null){
            sharedPreferences=  requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        }
        nni = viewContainer.findViewById(R.id.nni);
        patronymeAr = viewContainer.findViewById(R.id.patronyme_ar);
        patronymeFr = viewContainer.findViewById(R.id.patronyme_fr);
        perePrenomAr = viewContainer.findViewById(R.id.pere_prenom_ar);
        perePrenomFr = viewContainer.findViewById(R.id.pere_prenom_fr);
        prenomAr = viewContainer.findViewById(R.id.prenom_ar);
        prenomFr = viewContainer.findViewById(R.id.prenom_fr);
        sexeCode = viewContainer.findViewById(R.id.sexe_code);
        rednni = viewContainer.findViewById(R.id.nnilr);
        recyclerView = viewContainer.findViewById(R.id.recyclerView);
        desc = viewContainer.findViewById(R.id.description);
        condui = viewContainer.findViewById(R.id.conduitContact);
        dateNaissance = viewContainer.findViewById(R.id.date_naissance_ar2);
        lieuNaissanceAr = viewContainer.findViewById(R.id.lieu_naissance_ar2);
         photo = viewContainer.findViewById(R.id.photo);
         redlist = viewContainer.findViewById(R.id.viewredlist);
         cardviewlist = viewContainer.findViewById(R.id.cardView_lrdata);
        callback = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        redListItems = new ArrayList<>();
        redListAdapter = new PersonDetailsAdapter(redListItems);
        recyclerView.setAdapter(redListAdapter);
        recyclerView.setAdapter(redListAdapter);
        if (getArguments() != null) {
            String responseString = getArguments().getString("responseString");
            parseAndSetDetails(responseString);
        }

        return viewContainer;
    }

    private void parseAndSetDetails(String responseString) {
        try {
            JSONObject responseObject = new JSONObject(responseString);
            JSONObject personne = responseObject.getJSONObject("personne");
            String nnitxt = personne.getString("nni");
            nni.setText(nnitxt);
            patronymeAr.setText("الإسم العائلي"+"  "+personne.getString("patronymeAr"));
            patronymeFr.setText("Patronyme  "+personne.getString("patronymeFr"));
            perePrenomAr.setText("إسم الأب"+"  "+personne.getString("perePrenomAr"));
            perePrenomFr.setText("Pere Prenom "+personne.getString("perePrenomFr"));
            prenomAr.setText("الإسم"+"  "+personne.getString("prenomAr"));
            prenomFr.setText("Prenom  "+personne.getString("prenomFr"));
            sexeCode.setText(personne.getString("sexeCode"));
            profile =  sharedPreferences.getString("profile",null);
            op_nni =  sharedPreferences.getString("nni",null);
            opphone =  sharedPreferences.getString("mobile",null);

            String lieu = personne.getString("lieuNaissanceFr")+"/"+personne.getString("lieuNaissanceAr");
             //lieuNaissanceFr.setText(personne.getString("lieuNaissanceFr"));
            lieuNaissanceAr.setText(lieu);
            // Parse the datetime string to a Date object
            SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            try {
                String dn = personne.getString("dateNaissance");
                Date date = datetimeFormat.parse(dn);

                // Format the Date object to show only the date part
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(date);
                dateNaissance.setText(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Assuming photo is a base64 encoded string
            String photoBase64 = responseObject.getString("photo");
            if (!photoBase64.isEmpty()) {
                byte[] decodedString = android.util.Base64.decode(photoBase64, android.util.Base64.DEFAULT);
                Glide.with(getContext()).load(decodedString).into(photo);
            }
            try{
                if(profile.equals("admin")){
                    redlist.setVisibility(View.VISIBLE);
                    redlist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddFragment updateFragment = new AddFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("nni", nnitxt);
                            bundle.putString("mobile", opphone);
                            updateFragment.setArguments(bundle);
                            ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_layout, updateFragment)
                                    .addToBackStack(null)  // Add this transaction to the back stack
                                    .commit();
                        }
                    });

                }
                if (responseObject.has("lrData")) {
                    JSONArray lrDataArray = responseObject.getJSONArray("lrData");
                    if (lrDataArray.length() > 0) {
                        if(isAdded()) {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cardviewlist.setVisibility(View.VISIBLE);

                                    parseJsonAndPopulateRecyclerView(lrDataArray);
                                }
                            });

                        }

                    }
                }

            }catch (Exception ex){

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onSuccess() {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onError(String message) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(message) // Set your message
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Dismiss the dialog
                            }
                        });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private void parseJsonAndPopulateRecyclerView( JSONArray lrDataArray) {
        try {



            // Clear existing items
            redListItems.clear();

            // Iterate through the data array and create RedListItem objects
            for (int i = 0; i < lrDataArray.length(); i++) {
                JSONObject itemObject = lrDataArray.getJSONObject(i);

                String id = itemObject.getString("_id");
                String nni = itemObject.getString("nni");
                String nud = itemObject.getString("nud");
                String requestId = itemObject.getString("requestId");
                String matricule = itemObject.getString("matricule");
                String description = itemObject.getString("description");
                String conduitContact = itemObject.getString("conduitContact");

                // Create a RedListItem object and add it to the list
                PersonDetailModel redListItem = new PersonDetailModel(id, nni, nud, requestId, matricule, description, conduitContact, "", "");
                redListItems.add(redListItem);
            }

            // Notify the adapter that data has changed
            redListAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            Log.e("MainActivity", "Error parsing JSON", e);
        }
    }
}
