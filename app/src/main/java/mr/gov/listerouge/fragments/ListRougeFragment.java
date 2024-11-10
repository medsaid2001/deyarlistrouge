package mr.gov.listerouge.fragments;

import android.content.Context;
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
import mr.gov.listerouge.databinding.FragmentIdentificationBinding;
import mr.gov.listerouge.databinding.FragmentListRougeBinding;

public class ListRougeFragment extends Fragment {

    private Context context;
    int SELECT_PICTURE = 200;
    private View viewContainer;

    FragmentListRougeBinding listrouge;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listrouge = FragmentListRougeBinding.inflate(inflater, container, false);
        return listrouge.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listrouge.listOfReds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ListFragment());
            }
        });
        listrouge.addRedlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddFragment());
            }
        });

    }
    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}