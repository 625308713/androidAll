package com.dai.daicommon.ui.activity.camera;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dai.daicommon.R;
import com.dai.daicommon.ui.adapter.ImageAdapter;
import com.jph.takephoto.model.TImage;

import java.util.ArrayList;

public class ResultActivity extends Activity {
    ArrayList<TImage> images;
    private RecyclerView my_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_layout);
        my_recycler = findViewById(R.id.my_recycler);
        my_recycler.setLayoutManager(new LinearLayoutManager(this));
        images = (ArrayList<TImage>) getIntent().getSerializableExtra("images");
        ImageAdapter imageAdapter = new ImageAdapter(R.layout.adapter_book,images);
        my_recycler.setAdapter(imageAdapter);
    }
}
