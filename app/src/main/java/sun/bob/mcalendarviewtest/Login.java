package sun.bob.mcalendarviewtest;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Login {
    public static String sessionId;

    public  static void login(RequestQueue mQueue, String user, String password, final TextView textView, final Context context) {

            final String url1="https://www.eniso.info/ws/core/login/"+user+"?password="+password;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                         /*   Intent i = new Intent(getApplicationContext(),Groupes.class);
                            startActivity(i);*/
                            JSONObject res = response.getJSONObject("$1");
                            String usrid = res.getString("userId");
                            String usft = res.getString("sessionId");
                            Login.sessionId=usft;
                            textView.setText("success");




                        } catch (JSONException e) {
                            try {
                                JSONObject res1 = response.getJSONObject("$error");
                                String m = res1.getString("message");
                                textView.setText(m);

                            } catch (JSONException a) {

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("no response");
            }
        });

        mQueue.add(request);
    }
}