package ar.com.sifir.scrapfood;

/**
 * Created by Sifir on 13/11/2017.
 */

public class Step {
    private String text;
    private String img;

    public Step(String text, String img){
        this.text = text;
        this.img = img;
    }

    public String getText(){
        return text;
    }

    public String getImg(){
        return img;
    }

}
