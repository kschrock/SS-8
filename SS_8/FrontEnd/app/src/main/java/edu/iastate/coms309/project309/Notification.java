package edu.iastate.coms309.project309;
import  androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

public class Notification extends AppCompatActivity {


    EditText name, location,date,time;
    Button b1;
    TextView t1;
    private WebSocketClient cc;
    JSONObject event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event=new JSONObject();
        setContentView(R.layout.notification);
        name=findViewById(R.id.name);
        location=findViewById(R.id.location);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        t1=findViewById(R.id.t1);
        b1=findViewById(R.id.b);
        Draft[] drafts = {new Draft_6455()};
        //String w = "ws://10.26.13.93:8080/websocket/"+e1.getText().toString();
        String w = "";
        w = "ws://coms-309-ss-8.misc.iastate.edu:8080/notify/" + Const.username;
        try {
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    t1.setText(message);

                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    event.put("eventName", name.getText().toString());
                    event.put("location", location.getText().toString());
                    event.put("date", date.getText().toString());
                    event.put("time", time.getText().toString());
                    event.put("username", Const.username);


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                try {
                    cc.send(event.toString());
                    Log.d("check", event.toString());
                }
                catch (Exception e)
                {
                    try {
                        Log.d("ExceptionSendMessage:", e.getMessage().toString());
                    } catch(NullPointerException err) {}
                }

            finish();
            }



        });

    }

}