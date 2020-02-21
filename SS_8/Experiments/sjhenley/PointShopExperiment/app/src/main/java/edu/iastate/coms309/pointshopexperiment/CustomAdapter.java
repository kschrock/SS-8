package edu.iastate.coms309.pointshopexperiment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater infltr;
    private ArrayList<String[]> data;

    public CustomAdapter(Context applicationContext, ArrayList<String[]> data)
    {
        this.context = applicationContext;
        this.data = data;
        infltr = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() { return data.size();}

    @Override
    public Object getItem(int i) { return null;}

    @Override
    public long getItemId(int i) {return 0;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        view = infltr.inflate(R.layout.activity_grid_view, null);
        TextView item = view.findViewById(R.id.textItemName);
        TextView price = view.findViewById(R.id.textItemPrice);
        item.setText(data.get(i)[0]);
        price.setText(data.get(i)[1]);

        return view;
    }

    public String getObject(int i) {
        return data.get(i)[0];
    }

    public String getPrice(int i) {
        return data.get(i)[0];
    }
}
