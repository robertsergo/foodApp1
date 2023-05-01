package com.example.secondapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.secondapp.domain.FoodDomain;
import com.example.secondapp.helper.ManagementCart;

public class ShowDetailActivity extends AppCompatActivity {

    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private FoodDomain object;
    int numberOrder = 1;

    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        managementCart = new ManagementCart(this);
        intiView();
        getBundle();
    }

    private void getBundle() {

        object = (FoodDomain) getIntent().getSerializableExtra("object");
        int drawableReSourceId = this.getResources().getIdentifier(object.getPic(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableReSourceId)
                .into(picFood);

        titleTxt.setText(object.getTitle());
        feeTxt.setText("$"+object.getFee());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));


        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder=numberOrder+1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder>1){
                    numberOrder=numberOrder-1;
                    numberOrderTxt.setText(String.valueOf(numberOrder));
                }
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberIntCart(numberOrder);
                managementCart.insertFood(object);
                // Fermeture de l'activité courante et retour à la précédente
                finish();
            }
        });

    }

    private void intiView() {
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        plusBtn = findViewById(R.id.plusCartBtn);
        minusBtn = findViewById(R.id.minusCartBtn);
        picFood= findViewById(R.id.picFood);

    }

    @Override
    public void onBackPressed() {
        // retour à la page précédente en appuyant sur le bouton de retour
        Intent intent = new Intent(ShowDetailActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
