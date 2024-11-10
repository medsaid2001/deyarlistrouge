package mr.gov.listerouge.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mr.gov.listerouge.R;
import mr.gov.listerouge.databinding.FragmentCartedashBinding;
import mr.gov.listerouge.databinding.FragmentListRougeBinding;

public class CartedashFragment extends Fragment {

    private Context context;
    int SELECT_PICTURE = 200;
    private View viewContainer;
    private SharedPreferences sharedPreferences;
    FragmentCartedashBinding cardash;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cardash = FragmentCartedashBinding.inflate(inflater, container, false);
        return cardash.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isAdded()) {
            assert getActivity() != null;
            sharedPreferences = getActivity().getSharedPreferences("data", Activity.MODE_PRIVATE);
        }
        cardash.listOfReds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ListFragment());
            }
        });
        cardash.addRedlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddredcarFragment());
            }
        });


    }
    private  void replaceFragment(Fragment fragment) {
        assert getActivity() != null;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}