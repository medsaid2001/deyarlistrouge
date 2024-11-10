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
import mr.gov.listerouge.databinding.FragmentListRougeDashBinding;

public class ListRougeDashFragment extends Fragment {

    private Context context;
    int SELECT_PICTURE = 200;
    private View viewContainer;

    FragmentListRougeDashBinding listdash;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listdash = FragmentListRougeDashBinding.inflate(inflater, container, false);
        return listdash.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listdash.listerougecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CartedashFragment());
            }
        });
        listdash.listerougepersonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ListRougeFragment());
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