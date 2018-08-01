package com.admodule.classes;

import android.os.AsyncTask;


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class SendPostJSONRequest extends AsyncTask<String, Void, String> {

    JSONObject jsonObject;
    public SendPostJSONRequest(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {

        int serverResponseCode = 0;

        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setDoInput(true);//Allow Inputs
            httpUrlConnection.setDoOutput(true);//Allow Outputs
            httpUrlConnection.setUseCaches(false);//Don't use a cached Copy
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            //httpUrlConnection.setRequestProperty("Accept", "application/json");
            Writer writer = new BufferedWriter(new OutputStreamWriter(httpUrlConnection.getOutputStream(), "UTF-8"));
            writer.write(jsonObject.toString());
            writer.close();

            serverResponseCode = httpUrlConnection.getResponseCode();
            if (serverResponseCode == HttpsURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());
                String serverResponseMessage = convertStreamToString(in).trim();
                in.close();
                httpUrlConnection.disconnect();
                return serverResponseMessage;
            } else {
                return "error";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("=>>>>>"+result.toString());
    /*    if (handler != null) {
            Message msg = new Message();
            msg.what = ResponseCodes.DONE;
            msg.obj = result;
            handler.sendMessage(msg);
        }*/
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

 