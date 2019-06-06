package com.example.myapplication;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class dynamicFragment extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener {
    Tab1 fragGlide = new Tab1();
    Tab2 fragPicasso= new Tab2();
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragment);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGlide)
    void addGlide(View view) {
        if(!fragGlide.isAdded())
        {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragLayout, fragGlide);
            ft.remove(fragPicasso);
            ft.commit();
        }

    }

    @OnClick(R.id.btnPicasso)
    void addPicaso(View view){
        if(!fragPicasso.isAdded()){
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragLayout, fragPicasso);
            ft.remove(fragGlide);
            ft.commit();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
