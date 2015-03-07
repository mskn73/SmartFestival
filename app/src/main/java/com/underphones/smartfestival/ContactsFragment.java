package com.underphones.smartfestival;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class ContactsFragment extends Fragment {

    private ListView contactListView;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        contactListView = (ListView) rootView.findViewById(R.id.contact_list_view);
        ContactsAdapter ca = new ContactsAdapter(getActivity(), new ArrayList<ContactBean>());
        contactListView.setAdapter(ca);

        return rootView;
    }


}
