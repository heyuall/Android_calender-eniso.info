package sun.bob.mcalendarviewtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;


public class MainActivity extends AppCompatActivity {
    private Context mcontext;
    RequestQueue mQueue;
    private TextView tv;
    public PopupWindow popupWindow;
    EventAdapter mAdapter ;
    RecyclerView rv;


    public MCalendarView calendarView;
    public TextView titre, start, End;
    Button ClosePopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mcontext = getApplicationContext();

        mQueue = Volley.newRequestQueue(mcontext);
        // tv = findViewById(R.id.ind);

        getdata();


//      Get instance.
        calendarView = ((MCalendarView) findViewById(R.id.calendar));


//      Set up listeners.
        calendarView.travelTo(new DateData(2019, 01, 1));
        // calendarView.travelTo(new DateData(2016, 11, 1));
        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {

            }
        }).setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                ((TextView) findViewById(R.id.ind)).setText(String.format("%d-%d", year, month));
//                Toast.makeText(MainActivity.this, String.format("%d-%d", year, month), Toast.LENGTH_SHORT).show();
//                calendarView.markDate(year, month, 5);
//                MarkedDates.getInstance().notifyObservers();
            }
        })
        ;
               /* .markDate(new DateData(2019, 1, 2).setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.GREEN)))
                .hasTitle(false)

                .markDate(new DateData(2019,01,9)
                        .setMarkStyle(new MarkStyle(MarkStyle.RIGHTSIDEBAR, Color.RED)));

        */


    }

    public void getdata() {


        final ArrayList<NewEvent> myevents = new ArrayList<NewEvent>();


        String url = "https://www.eniso.info/ws/core/wscript?s=Return(bean(%22calendars%22).findMyEventCalendarEvents(%22%22))";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray alldata = response.getJSONArray("$1");
                            for (int i = 0; i < alldata.length(); i++) {
                                NewEvent e = new NewEvent();
                                JSONObject myobject = alldata.getJSONObject(i);
                                String title = myobject.getString("title");
                                String startdate = myobject.getString("startDate");
                                String enddate = myobject.getString("endDate");



                                 tv.append("\n" + title);
                                //////
                                e.setTitle(title);
                                e.setStartdate(startdate);
                                e.setEnddate(enddate);


                                String[] s = e.getStartdate().split(" ");
                                s[1] = s[1].substring(0, s[1].length() - 1);
                                //Log.d("cases","\n"+s[0]+"/"+s[1]+"/"+s[2]);


                                String[] s2 = e.getEnddate().split(" ");
                                s2[1] = s2[1].substring(0, s2[1].length() - 1);


                                int month = intmonth(s[0]);
                                int month2 = intmonth(s2[0]);
                                int day = Integer.parseInt(s[1]);
                                int day2 = Integer.parseInt(s2[1]);
                                int year = Integer.parseInt(s[2]);
                                int year2 = Integer.parseInt(s2[2]);


                                e.setStart(new DateData(year, month, day));
                                e.setEnd(new DateData(year2, month2, day2));


                                myevents.add(e);

                                //Log.d("event e","1-"+title +"\n"+"2- "+day+" "+year+" "+startdate+"\n"+"3-"+day2+"\n");
                                if (e.getEnd().equals(e.getStart())) {
                                    calendarView.markDate(e.getStart().setMarkStyle(MarkStyle.DOT, Color.BLUE));
                                } else {
                                    for (int j = e.getStart().getDay(); j <= e.getEnd().getDay(); j++) {

                                        calendarView.markDate(new DateData(e.getStart().getYear(), e.getStart().getMonth(), j).setMarkStyle(MarkStyle.DOT, Color.GREEN));
                                    }
                                }


                            }
                            calendarView.setOnDateClickListener(new OnDateClickListener() {
                                @Override
                                public void onDateClick(View view, DateData date) {
                                    ArrayList<NewEvent> occlist;
                                    occlist= new ArrayList<>();



                                    for (NewEvent evt : myevents) {
                                        if ((evt.getStart().equals(date)) && (evt.getStart().equals(evt.getEnd()))) {
                                            occlist.add(evt);







                                        } else if (((date.getDay() >= evt.getStart().getDay()) && (date.getMonth() == evt.getStart().getMonth()) && (evt.getStart().getYear() == date.getYear())) && (((date.getDay() <= evt.getEnd().getDay()) && (date.getMonth() == evt.getEnd().getMonth()) && (evt.getEnd().getYear() == date.getYear())))) {
                                            //Toast.makeText(MainActivity.this, String.format("%d-%d", date.getMonth(), date.getDay()), Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(MainActivity.this, evt.getTitle() + "\n start Day :" + evt.getStartdate() + "\n end Day :" + evt.getEnddate(), Toast.LENGTH_SHORT).show();
                                            occlist.add(evt);



                                          /*  popupWindow = new PopupWindow(customView, 400,500);

                                            RelativeLayout relativeLayout = findViewById(R.id.main);
                                            popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
                                            ClosePopup.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    popupWindow.dismiss();
                                                }
                                            });*/
                                        }
                                    }
                                    LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View customView = layoutInflater.inflate(R.layout.popup, null);
                                    rv = customView.findViewById(R.id.rvevents);
                                    mAdapter= new EventAdapter(mcontext,occlist);
                                    rv.setAdapter(mAdapter);
                                    RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mcontext);
                                    rv.setLayoutManager(layoutManager);
                                    mAdapter.notifyDataSetChanged();
                                    popupWindow = new PopupWindow(customView, 400,800);

                                    RelativeLayout relativeLayout = findViewById(R.id.main);
                                    popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
                                    Button close = customView.findViewById(R.id.close);
                                    close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupWindow.dismiss();
                                        }
                                    });



                                }
                            });


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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", "JSESSIONID=" + Login.sessionId);


                return params;
            }
        };

        mQueue.add(request);


    }

    public int intmonth(String stringMonth) {
        int i = 0;
        switch (stringMonth) {
            case "Jan":
                i = 1;
                break;
            case "Feb":
                i = 2;
                break;
            case "Mar":
                i = 3;
                break;
            case "Apr":
                i = 4;
                break;
            case "May":
                i = 5;
                break;
            case "Jun":
                i = 6;
                break;
            case "Jul":
                i = 7;
                break;
            case "Aug":
                i = 8;
                break;
            case "Sep":
                i = 9;
                break;
            case "Oct":
                i = 10;
                break;
            case "Nov":
                i = 11;
                break;
            case "Dec":
                i = 12;
                break;

        }
        return i;
    }


}
