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

import java.util.HashMap;
import java.util.Map;

public class dataTest extends AppCompatActivity {
    private Button fetch, button;
    private TextView showData;
    private Context mcontext;
    RequestQueue mQueue;
    private TextView text ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_test);
        mcontext=getApplicationContext();

        mQueue=Volley.newRequestQueue(mcontext);

       // CalendarView cal = findViewById(R.id.calendar);



        showData= findViewById(R.id.textView2);
        fetch = findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
            }
        });

    }

    public void getdata(){
        Context c;

        String url="https://www.eniso.info/ws/core/wscript?s=Return(bean(\"calendars\").findMyEventCalendarEvents(\"\"))";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray alldata= response.getJSONArray("$1");
                            for(int i=0;i<alldata.length();i++){
                                JSONObject myobject=alldata.getJSONObject(i);
                                String title=myobject.getString("title");
                                String startdate=myobject.getString("startDate");
                                showData.append("\n"+title+"  "+startdate);
                            }




                        } catch (JSONException e) {
                            try {
                                JSONObject res1 = response.getJSONObject("$error");
                                String m = res1.getString("message");
                                showData.setText(m);
                            } catch (JSONException a) {

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showData.setText("No response");

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
