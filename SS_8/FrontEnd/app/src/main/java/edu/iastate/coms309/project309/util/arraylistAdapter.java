package edu.iastate.coms309.project309.util;

import java.util.ArrayList;
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
import android.widget.BaseAdapter;
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
import android.view.LayoutInflater;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import android.content.Context;
import java.math.BigDecimal;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.iastate.coms309.project309.R;
import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;
public class arraylistAdapter extends BaseAdapter {
    Context context;
    ArrayList<String>  companies;
    LayoutInflater infltr;

    public arraylistAdapter(Context applicationContext, ArrayList<String> companies) {
        this.context = applicationContext;
        this.companies = companies;
        infltr = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return companies.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = infltr.inflate(R.layout.adapterview, null);
        TextView company = view.findViewById(R.id.textViewCompany);
        company.setText(companies.get(i));
        return view;
    }

    public void add( String company) {
        companies.add( company);
        notifyDataSetChanged();
    }


    public String getCompany(int i) {
        return companies.get(i);
    }


}