package puzi.compa.jigsaw.activity;

import android.graphics.Color;

public class ColorUtil {

    public static int getRandomColor(){

        String[] colors = {"9BA0FF","4AFF9B","FF9A73","4EB3FF","65BEA0","BE624A","BE8654","A546BE","2B6EBE","30BE5C","3DB8BE","BE3639"
                ,"50BE14","9EBE31","E3AAEE","1CEEE0","EE1A5C","EE964B","89EE1D","EF4119" ,"283A5F"};

        int index = (int)  (Math.random() * colors.length);
        int color;

        try{
              color = Color.parseColor("#"+ colors[index]);
        }catch(Exception ex) {
            System.out.println(ex);
            return Color.parseColor("#EE4457");
        }

        return color;
    }
}
