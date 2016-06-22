package com.example.sebastian.quierosercauca.controllers;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import com.example.sebastian.quierosercauca.R;

/**
 * Created by sebastian on 19/09/15.
 */
public class AyudaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

    }
}
