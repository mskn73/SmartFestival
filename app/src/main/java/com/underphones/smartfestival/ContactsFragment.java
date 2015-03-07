package com.underphones.smartfestival;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.underphones.smartfestival.controller.Client;
import com.underphones.smartfestival.controller.IClientCodeEvents;

import java.util.ArrayList;


public class ContactsFragment extends Fragment implements IClientCodeEvents{

    private ListView contactListView;
    private Client mClient;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        contactListView = (ListView) rootView.findViewById(R.id.contact_list_view);
        ContactsAdapter ca = new ContactsAdapter(getActivity(), new ArrayList<ContactBean>());
        contactListView.setAdapter(ca);

        mClient=new Client(this, getActivity().getApplicationContext());
        mClient.getUsers();
        return rootView;
    }

    @Override
    public void CodeStartedRequest(String methodName) {
        Toast.makeText(getActivity(), "Loading contacts", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CodeFinished(String methodName, Object data) {
        if (methodName.equalsIgnoreCase(Client.METHOD_GET_USERS)){
            ContactsAdapter ca = new ContactsAdapter(getActivity(),  (ArrayList<ContactBean>) data);
            contactListView.setAdapter(ca);
        }
    }

    @Override
    public void CodeFinishedWithException(int statusCode, String methodName, Throwable ex) {

    }
}
