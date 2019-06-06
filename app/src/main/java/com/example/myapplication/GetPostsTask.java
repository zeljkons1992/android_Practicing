package com.example.myapplication;

import android.os.AsyncTask;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetPostsTask extends AsyncTask<String, Void, String> {

    private RecyclerViewActivity activity;

    public GetPostsTask(RecyclerViewActivity a) {
        this.activity = a;
    }

    String stringUrl = "https://jsonplaceholder.typicode.com/posts";
    String result;
    String inputLine;
    String REQUEST_METHOD = "GET";
    int READ_TIMEOUT = 15000;
    int CONNECTION_TIMEOUT = 15000;
    int i = 0;

    @Override
    protected String doInBackground(String... params){

        try {
            URL myUrl = new URL(stringUrl);
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();

            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            result = stringBuilder.toString();
            Gson gson = new Gson();
            Model mod[] = gson.fromJson(result, Model[].class);

            for(Model model : mod) {
                activity.myDataset[i] = mod[i].getText();
                i++;
            }
            return result;
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        activity.mAdapter.notifyDataSetChanged();
    }
}
