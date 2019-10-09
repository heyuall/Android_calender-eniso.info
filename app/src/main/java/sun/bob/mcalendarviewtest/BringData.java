package sun.bob.mcalendarviewtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BringData extends AppCompatActivity {
    private Button fetch, button;
    private TextView tv;
    private Context mcontext;
    RequestQueue mQueue;
    private TextView text ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mcontext=getApplicationContext();

        mQueue=Volley.newRequestQueue(mcontext);




        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
            }
        });

    }

    public void getdata(){

        final ArrayList<NewEvent> myevents = new ArrayList<>();
       /* for( NewEvent e : myevents){
            myevents.add(e);
        }*/

        Context c;

        String url="https://www.eniso.info/ws/core/wscript?s=Return(bean(\"calendars\").findMyEventCalendarEvents(\"\"))";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray alldata= response.getJSONArray("$1");
                            for(int i=0;i<alldata.length();i++){
                                NewEvent e = new NewEvent();
                                JSONObject myobject=alldata.getJSONObject(i);
                                String title=myobject.getString("title");
                                String startdate=myobject.getString("startDate");
                                String enddate =myobject.getString("endDate");
                                tv.append("\n"+title+"  "+startdate);
                                //////
                                e.setTitle(title);
                                e.setStartdate(startdate);
                                e.setEnddate(enddate);
                                myevents.add(e);


                            }




                        } catch (JSONException e) {
                            try {
                                JSONObject res1 = response.getJSONObject("$error");
                                String m = res1.getString("message");
                                tv.setText(m);
                            } catch (JSONException a) {

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("No response");

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Cookie", "JSESSIONID="+Login.sessionId);


                return params;
            }
        };

        mQueue.add(request);

    }
}
