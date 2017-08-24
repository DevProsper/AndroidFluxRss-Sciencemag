package com.venteaucongo.devprosper.fluxrss;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DevProsper on 24/08/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<FeedItem> list;
    Context context;

    public MyAdapter(ArrayList<FeedItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_rows, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(0)
                .playOn(holder.cardView);
        final FeedItem item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.pubDate.setText(item.getPubDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("Link", item.getLink());
                context.startActivity(i);
            }
        });
        Picasso.with(context).load(item.getThumbnailUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, pubDate, link;
        ImageView imageView;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            description = (TextView)itemView.findViewById(R.id.description);
            pubDate = (TextView)itemView.findViewById(R.id.pubDate);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
        }
    }
}
