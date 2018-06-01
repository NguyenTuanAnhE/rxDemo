package com.rxandroid.anhnt.rxdemo.utils.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.CONNECT_TIMEOUT;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.READ_TIMEOUT;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.REQUEST_METHOD;

public class TrackIconRemote extends AsyncTask<String, Void, Bitmap> {
    private OnLoadBitmapListener mListener;

    TrackIconRemote(OnLoadBitmapListener listener) {
        mListener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setDoOutput(true);
            connection.connect();
            bitmap = BitmapFactory.decodeStream(url.openStream());
        } catch (IOException e) {
            mListener.onLoadFail();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mListener.onLoadSuccess(bitmap);
    }

    public interface OnLoadBitmapListener {
        void onLoadSuccess(Bitmap bitmap);

        void onLoadFail();
    }
}
