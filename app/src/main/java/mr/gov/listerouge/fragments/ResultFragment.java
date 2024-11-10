package mr.gov.listerouge.fragments;

import static android.content.Context.MODE_PRIVATE;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import mr.gov.listerouge.R;

public class ResultFragment extends Fragment {

    String nni = null;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    private TextView textName, textTypePerson, textNNI, textRepresentationId, textScore, textDecision, textRequestId, textNUD;
   private ImageView backbtn;
   Context context;
   private ImageView btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize TextViews
        if (sharedPreferences == null) {
            sharedPreferences = getActivity().getSharedPreferences("data", MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        textName = view.findViewById(R.id.text_name);
        textNNI = view.findViewById(R.id.text_nni);
        textScore = view.findViewById(R.id.text_score);
        textTypePerson = view.findViewById(R.id.typepersonne);
        textDecision = view.findViewById(R.id.text_decision);
        backbtn = view.findViewById(R.id.backbutton);
       // textNUD = view.findViewById(R.id.nud);
        btn = view.findViewById(R.id.morebutton);
        context = getContext();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               IdentificationFragment fragment = new IdentificationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .commit();
            }
        });
        if (getArguments() != null) {
            String responseString = getArguments().getString("responseString");
            if(responseString!=null){
                populatedata(responseString);
            }


        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nni==null){

                }else{
                    LoadingFragment detailsFragment = new LoadingFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("nni", nni);
                    bundle.putInt("type", 3);
                    detailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, detailsFragment)
                            .commit();
                }

            }
        });
    }
    private  void populatedata(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            // Set data to TextViews
            textName.setText(jsonObject.getString("nom"));
            textNNI.setText(jsonObject.getString("nni"));
           String nud = jsonObject.getString("nud");
           String requestid = jsonObject.getString("requestId");
            editor.putString("nud",nud);
            editor.putString("requestid",requestid);
            editor.commit();

            //textNUD.setText(nud);
            String typeperson = jsonObject.getString("typePersonne");
            if(typeperson.equals("C")){
                textTypePerson.setText("Type Personne: Citoyen");
            }
            if(typeperson.equals("R")){
                textTypePerson.setText("Type Personne: Résident");
            }
            if(typeperson.equals("V")){
                textTypePerson.setText("Type Personne: Visiteur");
            }
          int result = jsonObject.getInt("score");

            String decision = jsonObject.getString("decision");
            if(decision.equals("HIT")){
                textDecision.setText("CANDIDAT CONFIRMÉ");
                textDecision.setTextColor(ContextCompat.getColor(context, R.color.light_green)); // Assuming you have defined a color resource named red
            }else{
                textDecision.setTextColor(ContextCompat.getColor(context, R.color.red)); // Assuming you have defined a color resource named red
                textDecision.setText("CANDIDAT POTENTIEL");
            }

            textScore.setText(String.valueOf(result));
            nni = jsonObject.getString("nni");
            if(nni==null){
                btn.setEnabled(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}