package com.example.myapplication;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class dynamicFragmentActivity extends AppCompatActivity implements GlideLoaderExampleFragment.OnFragmentInteractionListener, PicassoLoaderExampleFragment.OnFragmentInteractionListener {
    GlideLoaderExampleFragment fragGlide = new GlideLoaderExampleFragment();
    PicassoLoaderExampleFragment fragPicasso= new PicassoLoaderExampleFragment();
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragment);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGlide)
    void addGlide(View view) {
        if(!fragGlide.isAdded()) {
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
