package com.example.secondapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.secondapp.Interface.ChangeNumberItemsListener;
import com.example.secondapp.R;
import com.example.secondapp.domain.FoodDomain;
import com.example.secondapp.helper.ManagementCart;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private final ArrayList<FoodDomain> foodDomains;
    private final ManagementCart managementCart;
    private final ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<FoodDomain> foodDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.foodDomains = foodDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }
/*
    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder,  int position) {
        holder.title.setText(foodDomains.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(foodDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf((foodDomains.get(position).getNumberIntCart()*foodDomains.get(position).getFee()*100)/100));
        holder.num.setText(String.valueOf(foodDomains.get(position).getNumberIntCart()));

        int drawableReSourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableReSourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.plusNumberFood(foodDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });


        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.minusNumberFood(foodDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });



    }*/


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        holder.title.setText(foodDomains.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(foodDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((foodDomains.get(position).getNumberIntCart() * foodDomains.get(position).getFee()) * 100) / 100));
        holder.num.setText(String.valueOf(foodDomains.get(position).getNumberIntCart()));

        int drawableReSourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableReSourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(view -> {
                managementCart.plusNumberFood(foodDomains, position, () -> {
                    notifyDataSetChanged();
                    changeNumberItemsListener.changed();
                });

        });

        holder.minusItem.setOnClickListener(view -> {
                managementCart.minusNumberFood(foodDomains, position, () -> {
                    notifyDataSetChanged();
                    changeNumberItemsListener.changed();
                });

        });

        // Affichage du contenu de l'itemView dans la console
        Log.d("CartListAdapter", "Content de l'itemView à la position " + position + " : " + holder.itemView);

    }


    @Override
    public int getItemCount() {
        return foodDomains.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, feeEachItem, num, totalEachItem;
        ImageView pic, plusItem,minusItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titleTxt);
            feeEachItem=itemView.findViewById(R.id.feeEachItem);
            pic=itemView.findViewById(R.id.picCart);
            totalEachItem=itemView.findViewById(R.id.totalEachItem);
            num=itemView.findViewById(R.id.numberItemTxt);
            plusItem=itemView.findViewById(R.id.plusCartBtn);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
        }
    }
}

/*
 @Override
    public int getItemCount() {
        return foodDomains.size();
    }
 */
