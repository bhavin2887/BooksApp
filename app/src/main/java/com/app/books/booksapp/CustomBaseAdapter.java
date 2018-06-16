package com.app.books.booksapp;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CustomBaseAdapter extends BaseAdapter {
	Context context;
    List<RowItem> rowItems;
    boolean flagInternet;
    
    public CustomBaseAdapter(Context context, List<RowItem> items, boolean flag) {
        this.context = context;
        this.rowItems = items;
        flagInternet = flag;
    }
     
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        LinearLayout linear;
        TextView txtDesc;
    }
    
    public int getCount() {     
        return rowItems.size();
    }
 
    public Object getItem(int position) {
        return rowItems.get(position);
    }
 
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

	public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
         
        LayoutInflater mInflater = (LayoutInflater) 
            context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_file, null);
            holder = new ViewHolder();
            
            holder.linear = (LinearLayout) convertView.findViewById(R.id.thumbnail_list);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
         
        final RowItem rowItem = (RowItem) getItem(position);
         
        holder.txtDesc.setText(rowItem.getAuthor());
        holder.txtTitle.setText(rowItem.getBook_name());
        /*holder.imageView.setImageResource(rowItem.getImageId());*/
         
        holder.linear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (flagInternet) {
					Intent it = new Intent(context,YouTubePlay.class);
					it.putExtra("val", rowItem.getYoutube_link());
					context.startActivity(it);
				}else{
					Toast.makeText(context,
							"Please check your Internet Connection.",
							Toast.LENGTH_SHORT).show();
				}
				
			}
		});
        return convertView;
	}
	
}