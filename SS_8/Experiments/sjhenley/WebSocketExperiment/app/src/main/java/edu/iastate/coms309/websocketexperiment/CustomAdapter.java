package edu.iastate.coms309.websocketexperiment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> events, companies;
    LayoutInflater infltr;

    public CustomAdapter(Context applicationContext, ArrayList<String> events, ArrayList<String> companies) {
        this.context = applicationContext;
        this.events = events;
        this.companies = companies;
        infltr = (LayoutInflater.from(applicationContext));
    }



    @Override
    public int getCount() {
        return events.size();
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
        view = infltr.inflate(R.layout.activity_list_view, null);
        TextView event =  view.findViewById(R.id.textViewEvent);
        TextView company = view.findViewById(R.id.textViewCompany);
        event.setText(events.get(i));
        company.setText(companies.get(i));
        return view;
    }

    public void add(String event, String company) {
        events.add(0, event);
        companies.add(0, company);
        notifyDataSetChanged();
    }

}
