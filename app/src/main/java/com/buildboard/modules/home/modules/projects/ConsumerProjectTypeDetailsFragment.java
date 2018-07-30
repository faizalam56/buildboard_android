package com.buildboard.modules.home.modules.projects;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.buildboard.R;


public class ConsumerProjectTypeDetailsFragment extends Fragment {

    public static ConsumerProjectTypeDetailsFragment newInstance() {
        return new ConsumerProjectTypeDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_consumer_project_type_details, container, false);
       return  view;
    }
}
