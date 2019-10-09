package sun.bob.mcalendarviewtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class HomeScreen extends AppCompatActivity {
    public Button login,next;
    public EditText username,password;
    public TextView tv;
    public Context mcontext;
    public RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mcontext=getApplicationContext();
        mQueue=Volley.newRequestQueue(mcontext);
        tv= findViewById(R.id.check);
        login= findViewById(R.id.login);
        username= findViewById(R.id.user);
        password=findViewById(R.id.pass);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,CalendarSection.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u,p;
                u=username.getText().toString();
                p=password.getText().toString();
                Login.login(mQueue,u,p,tv,mcontext);
            }
        });
    }
}
