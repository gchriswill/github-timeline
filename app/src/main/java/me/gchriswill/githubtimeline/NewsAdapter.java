package me.gchriswill.githubtimeline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class NewsAdapter extends BaseAdapter implements View.OnClickListener{

    Activity context;
    List<News> rowItems;
    int pos;

    ViewHolder holder;

    public NewsAdapter(Context context, List<News> items) {

        this.context = (Activity) context;

        this.rowItems = items;

    }

    News rowItem;

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {

        holder = null;
        pos = position;
        rowItem = (News) getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.row_article, null);
            holder = new ViewHolder();

            holder.tvTitle= (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvEmail= (TextView) convertView.findViewById(R.id.tvEmail);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvTitle.setText(rowItem.title);
        holder.tvName.setText(rowItem.name);
        holder.tvEmail.setText(rowItem.email);

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {

        return rowItems.get(position);

    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public long getItemId(int position) {

        return rowItems.indexOf(getItem(position) );

    }

    @Override
    public void onClick(View v) {



    }
}

class ViewHolder {

    TextView tvTitle;
    TextView tvName;
    TextView tvEmail;

}

