package com.example.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListOfPostsActivity extends Activity {
    private SharedPreferences sharedpreferences;
    @BindView(R.id.my_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.btnSlider)Button slider;
    @BindView(R.id.btnLogout)Button btnLogout;
    @BindView(R.id.btnFragment) Button btnFragment;
    public RecyclerView.Adapter mAdapter;
    public String[] myDataset = new String[100];
    NetworkReceiver networkReceiver = new NetworkReceiver(this::RetrofitGet);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_posts);
        ButterKnife.bind(this);
        mAdapter = new RecyclerViewAdapter(myDataset);
    }

    @Override
    public void onStart(){
        super.onStart();
        RecyclerView.LayoutManager layoutManager;
        myDataset[0] = "No data available";

        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        sharedpreferences = getSharedPreferences("mypref",Context.MODE_PRIVATE);
        TextView infoText = (TextView)findViewById(R.id.textView);
        Intent intent = getIntent();
        if(sharedpreferences.contains("mail")) {
            infoText.setText("Welcome: " + sharedpreferences.getString("mail", ""));
        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListOfPostsActivity.this, ImagesViewActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(ListOfPostsActivity.this, dynamicFragmentActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences.edit().remove("mail").commit();
                Intent mainIntent =
                        new Intent(ListOfPostsActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                finish();
            }
        });
    }

    @Override
    public void onStop(){
        super.onStop();

        if(networkReceiver != null) {
            unregisterReceiver(networkReceiver);
        }
    }

    public void RetrofitGet() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {

            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Code " + response.code());
                    return;
                }

                List<Post> post = response.body();
                for(int i = 0; i < post.size(); i++){
                    myDataset[i] = post.get(i).getTitle();
                }

                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private class NetworkReceiver extends BroadcastReceiver{

        private RefreshPosts refreshPostsCallback;

        public NetworkReceiver(RefreshPosts refreshPostsCallback){
            this.refreshPostsCallback = refreshPostsCallback;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (isOnline(context)) {
                    refreshPostsCallback.refresh();
                } else {
                    Toast.makeText(context, "Connection failed", Toast.LENGTH_LONG).show();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        private boolean isOnline(Context context) {
            try {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                //should check null because in airplane mode it will be null
                return (netInfo != null && netInfo.isConnected());
            } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}