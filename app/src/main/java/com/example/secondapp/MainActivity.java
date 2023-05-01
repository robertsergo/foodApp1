package com.example.secondapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.secondapp.adapter.CategoryAdaptor;
import com.example.secondapp.adapter.PopularAdaptor;
import com.example.secondapp.domain.CategoryDomain;
import com.example.secondapp.domain.FoodDomain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Déclaration des adaptateurs et des recycler view
    private RecyclerView.Adapter adapter, adapter_Popular;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialisation des recycler view
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
    }

    //Gestion de la navigation inférieure
    private void  bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirection vers l'activité du panier
                startActivity( new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirection vers l'activité principale
                startActivity( new Intent(MainActivity.this, MainActivity.class));

            }
        });
    }

    //Initialisation du recycler view de catégorie
    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Pizza","cat_1"));
        category.add(new CategoryDomain("Burger","cat_2"));
        category.add(new CategoryDomain("Hotdog","cat_3"));
        category.add(new CategoryDomain("Drink","cat_4"));
        category.add(new CategoryDomain("Donut","cat_5"));

        //Initialisation de l'adaptateur de catégorie
        adapter = new CategoryAdaptor(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    //Initialisation du recycler view des plats populaires
    private void recyclerViewPopular(){{
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Peperroni pizza","pizza","slices pepperoni, mozzerella cheese", 10.76));
        foodList.add(new FoodDomain("Cheeze Burger","pop_1","beef , cheese, tomato", 8.76));
        foodList.add(new FoodDomain("Vegetale pizza","pop_2","oil olive, vegetable, pitted cherry", 9.76));
        foodList.add(new FoodDomain("Good Buger","pizza","slices pepperoni, mozzerella cheese", 10.76));
        foodList.add(new FoodDomain("Hamburger","pop_1","beef , cheese, tomato", 8.76));

        //Initialisation de l'adaptateur des plats populaires
        adapter_Popular = new PopularAdaptor(foodList);
        recyclerViewPopularList.setAdapter(adapter_Popular);
    }

    }




}