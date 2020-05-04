package com.wildcardenter.myfab.nanodegreemoviesproject.utils;

/*
                                #  #           #  #     
    Created by Asif Mondal on 04-05-2020 at 00:30
*/


import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.REQUEST_METHOD_GET;

public class UrlConnection {

    @Nullable
    public static String loadMoviesFromUrl(String url) {
        HttpURLConnection urlConnection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder buffer = new StringBuilder();
        try {
            URL movieUrl = new URL(url);
            urlConnection = (HttpURLConnection) movieUrl.openConnection();
            urlConnection.setRequestMethod(REQUEST_METHOD_GET);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);

            inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            String response = bufferedReader.readLine();
            while (response != null) {
                buffer.append(response);
                response = bufferedReader.readLine();
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    bufferedReader = null;
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                    inputStreamReader = null;
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    urlConnection = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
