package mr.gov.listerouge.fragments;

import static mr.gov.listerouge.Constant.DEYAR;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import mr.gov.listerouge.R;
import mr.gov.listerouge.api.RetrofitClientInstance;
import mr.gov.listerouge.databinding.FragmentSearchCarBinding;
import mr.gov.listerouge.models.VehiRequest;
import mr.gov.listerouge.models.VehicleDetailsResponse;
import mr.gov.listerouge.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCarFragment extends Fragment {

    private FragmentSearchCarBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String nnitxt;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchCarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isAdded()) {
            assert getActivity() != null;
            sharedPreferences = getActivity().getSharedPreferences("data", Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        nnitxt = sharedPreferences.getString("nni", null);
        binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matricule = binding.textmatricule.getText().toString();
                editor.putString("matricule",matricule);
                editor.commit();
                binding.progressBar.setVisibility(View.VISIBLE);
                fetchVehicleDetails(matricule);
            }
        });
        binding.textmatricule.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        binding.addlistrouge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AddredcarFragment());
            }
        });
        binding.addlistrouge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AddredcarFragment());
            }
        });
        binding.addlistrouge3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(new AddredcarFragment());
            }
        });
        binding.persondetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nniprop = binding.nniProperietaire.getText().toString();
                if(nniprop!=null) {
                    LoadingFragment detailsFragment = new LoadingFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("nni", nniprop);
                    bundle.putInt("type", 3);
                    detailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, detailsFragment)
                            .commit();
                }
            }
        });
    }

    private void fetchVehicleDetails(String query) {
        String chassis = ""; // Replace or extract based on your app's logic
        String matricule = query; // Assuming query is the matricule
        VehiRequest res = new VehiRequest(chassis,matricule,nnitxt);
        NetworkService service = RetrofitClientInstance.getRetrofitInstance().create(NetworkService.class);
        Call<VehicleDetailsResponse> call = service.getVehiculeDetails(
                res,
                DEYAR
        );

        call.enqueue(new Callback<VehicleDetailsResponse>() {
            @Override
            public void onResponse(Call<VehicleDetailsResponse> call, Response<VehicleDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    VehicleDetailsResponse vehicleDetails = response.body();
                    updateUIWithData(vehicleDetails);
                } else {

                    Toast.makeText(getContext(), "Données non récupérées", Toast.LENGTH_SHORT).show();
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<VehicleDetailsResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SearchCarFragment", "Erreur lors de la récupération des détails du véhicule", t);
            }
        });
    }

    private void updateUIWithData(VehicleDetailsResponse vehicleDetails) {
        binding.numChassus.setText(vehicleDetails.getNumChassus());
        binding.marque.setText(vehicleDetails.getMarque());
        binding.typev.setText(vehicleDetails.getTypev());
        binding.couleurFr.setText(vehicleDetails.getCouleurFr());
        binding.nbrPlacesFr.setText(String.valueOf(vehicleDetails.getNbrPlaces()));
        binding.sourceEnergieFr.setText(vehicleDetails.getSourceEnergieFr());
        binding.dateQuitanceDedouanementFr.setText(vehicleDetails.getDateQuitanceDedouanement());
        binding.serieFr.setText(vehicleDetails.getSerie());
        binding.bureauDedouanementFr.setText(vehicleDetails.getBureauDedouanement());
        binding.anneEnregistrementFr.setText(vehicleDetails.getAnneEnregistrement());
        binding.dateImatriculationFr.setText(vehicleDetails.getDateImatriculation());
        binding.nniProperietaire.setText(vehicleDetails.getNniProprietaire());
        binding.nomProperietaireFr.setText(vehicleDetails.getNomProprietaireFr());

        if (vehicleDetails.isInListRouge()) {
            binding.addlistrouge2.setText("Cette voiture est dans la liste rouge");
        } else {
            binding.addlistrouge2.setText("Ajouter à la liste rouge");
        }
        binding.restlayout.setVisibility(View.VISIBLE);
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
