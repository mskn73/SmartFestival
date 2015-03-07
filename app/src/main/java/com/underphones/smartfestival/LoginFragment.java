package com.underphones.smartfestival;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gorbin.asne.core.AccessToken;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestAccessTokenCompleteListener;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;

import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.underphones.smartfestival.LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {

    public static SocialNetworkManager mSocialNetworkManager;
    private OnFragmentInteractionListener mListener;

    private Button googleplus;

    private SocialNetwork mSocialNetworkLogged;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        googleplus = (Button) rootView.findViewById(R.id.googleplus);
        googleplus.setOnClickListener(loginClick);

        //Use manager to manage SocialNetworks
        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(MainActivity.SOCIAL_NETWORK_TAG);


        //Check if manager exist
        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = new SocialNetworkManager();

            //Init and add to manager LinkedInSocialNetwork
            GooglePlusSocialNetwork gpNetwork = new GooglePlusSocialNetwork(this);
            mSocialNetworkManager.addSocialNetwork(gpNetwork);;

            //Initiate every network from mSocialNetworkManager
            getFragmentManager().beginTransaction().add(mSocialNetworkManager, MainActivity.SOCIAL_NETWORK_TAG).commit();
            mSocialNetworkManager.setOnInitializationCompleteListener(this);

            if (gpNetwork.isConnected()){
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ContactsFragment())
                        .commit();
            }
        } else {
            //if manager exist - get and setup login only for initialized SocialNetworks
            if(!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
                List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
                for (SocialNetwork socialNetwork : socialNetworks) {
                    socialNetwork.setOnLoginCompleteListener(this);
                    initSocialNetwork(socialNetwork);
                }
            }
        }


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentLogin(String token, String mail);
    }


    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
        }
    }

    //Login listener

    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int networkId = 0;
            switch (view.getId()){
                case R.id.googleplus:
                    networkId = GooglePlusSocialNetwork.ID;
                    break;
            }
            SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
            if (!socialNetwork.isConnected())
                socialNetwork.requestLogin();

            startProfile(socialNetwork.getID());
        }
    };

    @Override
    public void onLoginSuccess(int networkId) {
        MainActivity.hideProgress();
        startProfile(networkId);
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        MainActivity.hideProgress();
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    private void startProfile(int networkId){
        /*ProfileFragment profile = ProfileFragment.newInstannce(networkId);
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("profile")
                .replace(R.id.container, profile)
                .commit();
                */

        MainActivity.showProgress("Loading person");
        mSocialNetworkLogged = mSocialNetworkManager.getSocialNetwork(networkId);
        mSocialNetworkLogged.requestAccessToken(new OnRequestAccessTokenCompleteListener() {
            @Override
            public void onRequestAccessTokenComplete(int socialNetworkID, AccessToken accessToken) {
                MainActivity.hideProgress();
                mListener.onFragmentLogin(accessToken.token, null);
                Log.d("Access token: ", "SocialNetwork id = " + socialNetworkID + " accesstoken = " + accessToken.token + " secret = " + accessToken.secret);
            }

            @Override
            public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
            }
        });

        //mListener.onFragmentLogin(null);
    }


    private void initSocialNetwork(SocialNetwork socialNetwork){
        if(socialNetwork.isConnected()){
            switch (socialNetwork.getID()){
                case GooglePlusSocialNetwork.ID:
                    googleplus.setText("Show GooglePlus profile");
                    break;
            }
        }
    }

}
