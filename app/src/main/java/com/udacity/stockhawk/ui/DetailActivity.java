package com.udacity.stockhawk.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.stockhawk.R;

import java.util.Arrays;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.recycler_details)
    RecyclerView recyclerDetail;
    DetailHistoryAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;

    String history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.history = getIntent().getExtras().getString(getString(R.string.extra_history));
        getSupportActionBar().setTitle(getIntent().getExtras().getString(getString(R.string.extra_symbol)));
        fillRecyclerHistory();
    }

    void fillRecyclerHistory(){
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerDetail.setLayoutManager(mLayoutManager);
        String[] data = this.history.split("\n");
        adapter = new DetailHistoryAdapter(Arrays.asList(data));
        recyclerDetail.setAdapter(adapter);
    }

}
