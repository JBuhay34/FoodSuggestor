package com.justlive.justinbuhay.foodsuggestor.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.justlive.justinbuhay.foodsuggestor.R;

import java.util.HashMap;
import java.util.List;

public class BusinessCardsAdapter extends RecyclerView.Adapter<BusinessCardsAdapter.BusinessCardViewHolder> {

    private Context context;
    private List<HashMap<String, String>> mBusinesses;

    public BusinessCardsAdapter(Context context, List<HashMap<String, String>> mBussinesses){
        this.context = context;
        this.mBusinesses = mBussinesses;
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


        // Set item views based on your views and data model
        holder.nameTextView.setText(name);
        holder.phoneTextView.setText(phone);
        holder.ratingBar.setRating(rating);
        holder.priceTextView.setText(price);
        holder.addressTextView.setText(address);


    }

    @Override
    public int getItemCount() {
        return mBusinesses.size();
    }

    public static class BusinessCardViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView, phoneTextView, priceTextView, addressTextView;
        public RatingBar ratingBar;

        public BusinessCardViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView)itemView.findViewById(R.id.business_name_text_view);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            phoneTextView = (TextView) itemView.findViewById(R.id.phone_text_view);
            priceTextView = (TextView) itemView.findViewById(R.id.price_text_view);
            addressTextView = (TextView) itemView.findViewById(R.id.address_text_view);
        }
    }

}
