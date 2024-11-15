package mr.gov.listerouge.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mr.gov.listerouge.R;
import mr.gov.listerouge.databinding.FragmentNudInfoBinding;

public class NudInfoFragment extends Fragment {

    private FragmentNudInfoBinding binding;
    private int colorWhite;
    private int colorRed;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNudInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String responseString = getArguments().getString("responseString");
            parseAndSetDetails(responseString);
        }
        initColors();
        setClickListeners();
    }

    private void initColors() {

        colorWhite = ContextCompat.getColor(requireContext(), R.color.colorBlue);
        colorRed = ContextCompat.getColor(requireContext(), R.color.yellow);
    }

    private void setClickListeners() {
        binding.flybyplane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCardBackgroundTints(colorWhite, colorRed, colorRed);
            }
        });
        binding.flybysea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCardBackgroundTints(colorRed, colorWhite, colorRed);
            }
        });
        binding.flybybus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCardBackgroundTints(colorRed, colorRed, colorWhite);
            }
        });
    }

    private void setCardBackgroundTints(int planeColor, int boatColor, int busColor) {
        binding.cardviewplane.setBackgroundTintList(ColorStateList.valueOf(planeColor));
        binding.cardviewboat.setBackgroundTintList(ColorStateList.valueOf(boatColor));
        binding.cardviewbus.setBackgroundTintList(ColorStateList.valueOf(busColor));
    }

    private void parseAndSetDetails(String responseString) {
        try {
            JSONObject responseObject = new JSONObject(responseString);
            JSONObject personne = responseObject.getJSONObject("personne");
            String nud = personne.getString("nud");
            String info = personne.getString("info");
            String type = personne.getString("type");
            String firstName = personne.getString("firstName");
            String lastName = personne.getString("lastName");
            String validityStart = personne.getString("validityStart");
            String validityEnd = personne.getString("validityEnd");
            String validity = personne.getString("validity");
            String location = personne.getString("location");
            String birthDate = personne.getString("birthDate");
            String gender = personne.getString("gender");
            String deliveryDate = personne.getString("deliveryDate");
            String expiryDate = personne.getString("expiryDate");
            String nationality = personne.getString("nationality");


            SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
            SimpleDateFormat inputFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            try {
                // Parse and format dates
                String formattedValidityStart = formatDate(inputFormat1, validityStart, outputFormat);
                String formattedValidityEnd = formatDate(inputFormat2, validityEnd, outputFormat);
                String formattedBirthDate = formatDate(inputFormat2, birthDate, outputFormat);
                String formattedDeliveryDate = formatDate(inputFormat2, deliveryDate, outputFormat);
                String formattedExpiryDate = formatDate(inputFormat2, expiryDate, outputFormat);

                // Log formatted dates
                System.out.println("Validity Start: " + formattedValidityStart);
                System.out.println("Validity End: " + formattedValidityEnd);
                System.out.println("Birth Date: " + formattedBirthDate);
                System.out.println("Delivery Date: " + formattedDeliveryDate);
                System.out.println("Expiry Date: " + formattedExpiryDate);

                // Check if expiry date is before or after the current date
                Date expiry = outputFormat.parse(formattedExpiryDate);
                Date currentDate = new Date();

                if (expiry != null) {
                    if (expiry.before(currentDate)) {
                        binding.visastatus.setText("--Visa Expiré--");
                        binding.visastatus.setTextColor(getResources().getColor(R.color.red)); // Set text color to red
                    } else {
                        binding.visastatus.setText("--Visa Valide--");
                        binding.visastatus.setTextColor(getResources().getColor(R.color.green)); // Set text color to green
                    }
                }

                binding.nud.setText(nud);
                binding.nomtxt.setText(firstName);
                binding.prenomtxt.setText(lastName);
                binding.birthdate.setText(formattedBirthDate);
                binding.nationalitytxt.setText(nationality);

                binding.location.setText(location);
                binding.typetxt.setText(type);
                binding.validitystart.setText(formattedValidityStart);
                binding.validityend.setText(formattedValidityEnd);
                binding.validity.setText(validity);
                binding.deliveryDate.setText(formattedDeliveryDate);
                binding.expiryDate.setText(formattedExpiryDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private static String formatDate(SimpleDateFormat inputFormat, String dateString, SimpleDateFormat outputFormat) throws ParseException {
        Date date = inputFormat.parse(dateString);
        return (date != null) ? outputFormat.format(date) : null;
    }
}