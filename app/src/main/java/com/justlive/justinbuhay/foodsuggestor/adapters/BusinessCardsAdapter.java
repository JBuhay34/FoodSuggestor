package com.justlive.justinbuhay.foodsuggestor.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.justlive.justinbuhay.foodsuggestor.MainActivity;
import com.justlive.justinbuhay.foodsuggestor.R;

import java.util.HashMap;
import java.util.List;

public class BusinessCardsAdapter extends RecyclerView.Adapter<BusinessCardsAdapter.BusinessCardViewHolder> {

    private Context mContext;
    private List<HashMap<String, String>> mBusinesses;

    public BusinessCardsAdapter(Context mContext, List<HashMap<String, String>> mBusinesses) {
        this.mBusinesses = mBusinesses;
        this.mContext = mContext;
    }

    public void onClick(View view) {
        ((MainActivity) mContext).onClickCard(view);
    }


    @NonNull
    @Override
    public BusinessCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View businessView = inflater.inflate(R.layout.business_card_list_item, parent, false);

        // Return a new holder instance
        BusinessCardViewHolder viewHolder = new BusinessCardViewHolder(businessView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessCardViewHolder holder, int position) {
        // Get the data model based on position
        String name = mBusinesses.get(position).get("name");
        String phone = mBusinesses.get(position).get("phone");
        float rating = Float.parseFloat(mBusinesses.get(position).get("rating"));
        String price = mBusinesses.get(position).get("price");
        String address = mBusinesses.get(position).get("address");
        String image_url = mBusinesses.get(position).get("image_url");

        final String url = mBusinesses.get(position).get("url");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                c.startActivity(intent);
            }
        });

        // Set item views based on your views and data model
        holder.nameTextView.setText(name);
        holder.phoneTextView.setText(phone);
        holder.ratingBar.setRating(rating);
        holder.priceTextView.setText(price);
        holder.addressTextView.setText(address);
        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Glide.with(mContext).load(image_url).apply(options).into(holder.businessImageView);


    }

    @Override
    public int getItemCount() {
        return mBusinesses.size();
    }

    public static class BusinessCardViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView, phoneTextView, priceTextView, addressTextView;
        public ImageView businessImageView;
        public RatingBar ratingBar;

        public BusinessCardViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.business_name_text_view);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            phoneTextView = (TextView) itemView.findViewById(R.id.phone_text_view);
            priceTextView = (TextView) itemView.findViewById(R.id.price_text_view);
            addressTextView = (TextView) itemView.findViewById(R.id.address_text_view);
            businessImageView = itemView.findViewById(R.id.business_image_view);
        }
    }

}
