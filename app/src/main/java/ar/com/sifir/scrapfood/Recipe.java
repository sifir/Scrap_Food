package ar.com.sifir.scrapfood;

import android.util.Log;

/**
 * Created by Sifir on 02/11/2017.
 */

public class Recipe {

    private String title;
    private String[] ingredients;
    private String url;
    private String img;
    private Step[] pasos;


    public Recipe(String title, String[] ingredients, Step[] pasos, String url, String img ){
        this.title = title;
        this.ingredients = ingredients;
        this.pasos = pasos;
        this.url = url;
        this.img = img;
    }

    public String getTitle () {return title;};
    public void  setTitle(String title) {this.title = title;}

    public String[] getIngredients() {return  ingredients;}
    public void setIngredients(String[] ingredients){this.ingredients = ingredients;}

    public String getIngredientsString(){
        String temp ="";
        for (String s: this.ingredients) {
            temp += s+", ";
        }
        return temp;
    }

    public  String getUrl(){return url;}
    public void setUrl(String url){this.url = url;}

    public String getImg() {return img;}
    public void setImg(String img) {this.img = img;}

    public String getId() {

        String[] separate = this.url.split("/");
        return separate[2];
    }

    public Step[] getSteps(){return  pasos; }

    public int getStepCount() {
        return pasos.length;
    }
    

    public String[] getStepsText(){
        int i = 0;
        String[] temp = new String[pasos.length];
        for (Step p : pasos) {
            temp[i] = pasos[i].getText();
            i++;
        }
        return  temp;

    }

    public String[] getStepsImg(){
        int i = 0;
        String[] temp = new String[pasos.length];
        for (Step p : pasos) {
            temp[i] = pasos[i].getImg();
            i++;
        }
        return  temp;

    }

    @Override
    public String toString() {
        return "Titulo: " + title + "Url: " + url + "Url de imagen: " + url;
    }
}

