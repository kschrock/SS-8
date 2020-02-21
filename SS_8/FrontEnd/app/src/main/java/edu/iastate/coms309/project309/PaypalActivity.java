package edu.iastate.coms309.project309;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.List;

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;
import edu.iastate.coms309.project309.util.arraylistAdapter;

public class PaypalActivity extends AppCompatActivity {
  //  private TextView mTextViewResult;
    private RequestQueue mQueue;


    private static final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Const.PaypalClientCode);
    String items[]={"9"};
    Button payButton;
    EditText amountEdit;
    ArrayList<String> array;
    String amount="";
    arraylistAdapter adapter;
    @Override
    protected void onDestroy(){
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypal_payscreen);
       // mTextViewResult = findViewById(R.id.text_view_result);
        ListView listView =(ListView) findViewById(R.id.listView);

        mQueue = Volley.newRequestQueue(this);
        array= new ArrayList<String>();
        String url ="http://coms-309-ss-8.misc.iastate.edu:8080/owners/"+Const.username+"/findSubscriptions";
        String url3=Const.URL_SHOW_USERS+"/user/password/targetNorth2/paid";
      //  String url1="https://api.myjson.com/bins/kp9wz";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                  new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.e("response",response.toString());
                          JSONArray jsonArray=response.getJSONArray("UserSubscriptions");

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject company=jsonArray.getJSONObject(i);
                                array.add("You have");
                                array.add(company.getString("CompanyUserPoints"));
                                array.add("points for");
                                array.add(company.getString("Company"));
                               // array.add(company.getString("UserSubscriptions"));
                            }
                            /*
                            int b=response.length();
                            System.out.println((b));
                            Log.d("size", Integer.toString(b));
                            for(int i=1; i<=response.length();i++){

                                    String a = response.getString(Integer.toString(i));
                                    Log.d("test",a);
                                    array.add(a);



                            }*/
   // mTextViewResult.append(a);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("array",array.toString());
                        System.out.println("arr: " + array.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(AppController.TAG, "Error: " + error.getMessage());
                Log.e(AppController.TAG, "Error: " + error.getMessage());
                Toast t = Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT);
                t.show();
            }
        });

        mQueue.add(request);


        adapter=new arraylistAdapter(getApplicationContext(),array);
       listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Const.companyForDiscount=adapter.getCompany(position);
                System.out.println(adapter.getCompany(position));
            }
        });


        Intent intent=new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        payButton=(Button)findViewById(R.id.payNow);
        amountEdit=(EditText)findViewById(R.id.Amount);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();
            }
        });
    }
    private void processPayment(){
        amount=amountEdit.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Pay to ProjOutreach", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetails.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amount)
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }
    }




