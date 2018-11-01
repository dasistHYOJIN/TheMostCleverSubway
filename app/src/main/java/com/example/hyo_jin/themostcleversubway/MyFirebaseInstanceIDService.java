package com.example.hyo_jin.themostcleversubway;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hyo-Jin on 2018-10-31.
 * https://github.com/firebase/quickstart-android/blob/1b310e0e0ea2279d7e1363edb7e4923d0fc55369/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/MyFirebaseInstanceIDService.java#L35-L45
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceIDService";
    private static final String HOST = "http://192.168.0.33:3000";//"http://54.180.32.17:3000";

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) {
        // TODO: Implement this method to send token to your app server.

/*        setting = getActivity().getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();

        String url = HOST + "/user/fcmToken";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "onResponse "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "error "+error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", setting.getString("token", null));
                params.put("fcm_token", token);

                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(request);*/


    }
}