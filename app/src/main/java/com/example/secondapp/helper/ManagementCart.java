package com.example.secondapp.helper;

import android.content.Context;
import android.widget.Toast;

import com.example.secondapp.Interface.ChangeNumberItemsListener;
import com.example.secondapp.domain.FoodDomain;

import java.util.ArrayList;

public class ManagementCart {

    private Context context;
    private TinyDB tinyDB;

    // Constructeur de la classe qui reçoit le context de l'application
    public ManagementCart(Context context) {
        this.context = context;
        // Initialisation de l'objet TinyDB avec le context de l'application
        this.tinyDB = new TinyDB(context);
    }

    // Méthode pour ajouter un produit dans le panier
    public void insertFood(FoodDomain item){
        // Obtention de la liste des produits déjà présents dans le panier
        ArrayList<FoodDomain> listFood = getListCart();
        boolean existAlready = false; // Variable pour vérifier si le produit existe déjà dans le panier
        int n = 0; // Variable pour stocker la position du produit s'il existe déjà dans le panier
        for (int i=0; i <listFood.size(); i++){ // Parcours de la liste des produits dans le panier
            if(listFood.get(i).getTitle().equals(item.getTitle())){ // Si le produit existe déjà dans le panier
                existAlready = true;
                n=i;
                break; // Sortir de la boucle
            }
        }
        // Si le produit existe déjà dans le panier, mettre à jour le nombre d'articles
        if (existAlready){
            listFood.get(n).setNumberIntCart(item.getNumberIntCart());
        // Si le produit n'existe pas dans le panier, ajouter le produit dans la liste
        }else {
            listFood.add(item);
        }
        tinyDB.putListObject("CartList", listFood); // Enregistrer la liste des produits dans le panier
        Toast.makeText(context,"",Toast.LENGTH_SHORT).show(); // Afficher un message (qui est vide ici)
    }

    // Méthode pour obtenir la liste des produits dans le panier
    public ArrayList<FoodDomain> getListCart() {
        return tinyDB.getListObject("CartList"); // Obtenir la liste des produits dans le panier à partir de TinyDB
    }

    // Méthode pour ajouter un article à un produit dans le panier
    public void plusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        // Ajouter 1 au nombre d'articles
        listFood.get(position).setNumberIntCart(listFood.get(position).getNumberIntCart()+1);
        // Enregistrer la liste des produits dans le panier
        tinyDB.putListObject("CartList", listFood);
        // Appeler la méthode "changed" de l'interface ChangeNumberItemsListener
        changeNumberItemsListener.changed();
    }

    // Méthode pour enlever un article d'un produit dans le panier
    public  void minusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if (listFood.get(position).getNumberIntCart()==1){
            listFood.remove(position);
        }else {

            listFood.get(position).setNumberIntCart(listFood.get(position).getNumberIntCart()-1);
        }

        tinyDB.putListObject("CartList", listFood);
        changeNumberItemsListener.changed();
    }

    public  Double getTotalFee(){
        ArrayList<FoodDomain> listFood = getListCart();
        double fee =0;
        for (int i = 0; i <listFood.size() ; i++) {
            fee = fee + (listFood.get(i).getFee() * listFood.get(i).getNumberIntCart());
        }
        return  fee;
    }


}
