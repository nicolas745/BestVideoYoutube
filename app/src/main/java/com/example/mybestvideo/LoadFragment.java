package com.example.mybestvideo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LoadFragment {

    private FragmentManager fragmentManager;

    // Constructeur prenant un FragmentManager en paramètre
    public LoadFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    // Méthode pour charger un fragment
    public void load(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
