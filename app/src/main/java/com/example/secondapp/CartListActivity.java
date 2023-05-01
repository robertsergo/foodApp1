package com.example.secondapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.secondapp.Interface.ChangeNumberItemsListener;
import com.example.secondapp.adapter.CartListAdapter;
import com.example.secondapp.domain.FoodDomain;
import com.example.secondapp.helper.ManagementCart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {

    // Définition des variables
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxeTxt, deliveryTxt, totalTxt, emptyTxt;
    private double taxe;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        // Initialisation de la gestion du panier
        managementCart = new ManagementCart(this);

        // Initialisation des vues
        initview();

        // Initialisation de la liste des éléments dans le panier
        initList();

        // Calcul du coût total du panier
        CalculateCart();

        // Ajout de la navigation en bas de la page
        bottomNavigation();
    }

    // Fonction pour la navigation en bas de la page
    private void bottomNavigation(){
        // On récupère les éléments de la vue
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        // Action à effectuer quand l'utilisateur clique sur le bouton panier
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(CartListActivity.this, CartListActivity.class));
            }
        });

        // Action à effectuer quand l'utilisateur clique sur le bouton d'accueil
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity( new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }

    // Fonction pour initialiser les vues
    private void initview() {
        // On récupère les éléments de la vue
        recyclerViewList =findViewById(R.id.recyclerView);
        totalFeeTxt=findViewById(R.id.totalFeeTxt);
        taxeTxt=findViewById(R.id.taxeTxt);
        deliveryTxt=findViewById(R.id.deliveryTxt);
        totalTxt=findViewById(R.id.totalTxt);
        emptyTxt=findViewById(R.id.emptyTxt);
        scrollView=findViewById(R.id.scrollView3);
        recyclerViewList=findViewById(R.id.cartView);
    }

    // Fonction pour initialiser la liste des éléments dans le panier
    private void initList(){

        ArrayList<FoodDomain> cartList = managementCart.getListCart();
        for (FoodDomain food : cartList) {
            Log.d("Cartlist", food.getTitle() + " - " + food.getNumberIntCart());
        }


        // On crée un LinearLayoutManager pour afficher la liste des éléments dans le panier
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        // On crée un adapter pour la liste des éléments dans le panier
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                // On recalcule le coût total du panier quand l'utilisateur change le nombre d'un élément dans le panier
                CalculateCart();
            }
        });

        // On attache l'adapter à la RecyclerView
        recyclerViewList.setAdapter(adapter);

        // Si le panier est vide, on affiche le texte "Panier vide" et on cache la RecyclerView
        if (managementCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }else {

            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private  void CalculateCart(){
        double percentTax = 0.02;
        double delivery = 10;

        // calcul de la taxe sur le montant total du panier
        taxe = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100;
        // calcul du total de la commande, y compris la taxe et les frais de livraison
        double total = Math.round((managementCart.getTotalFee() + taxe + delivery) * 100) / 100;
        // calcul du sous-total de la commande, sans taxe ni frais de livraison
        double itemTotal = Math.round(managementCart.getTotalFee()* 100) / 100;

        // affichage des différents montants sur l'interface utilisateur
        totalFeeTxt.setText("$"+itemTotal);
        taxeTxt.setText("$"+taxe);
        deliveryTxt.setText("$"+delivery);
        totalTxt.setText("$"+total);

    }
}