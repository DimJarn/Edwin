package fr.eseo.pfe.edwin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.back_tuto_sheets,R.drawable.back_tuto_dico,R.drawable.back_tuto_quiz,R.drawable.back_tuto_go};
    private String [] textes = {"Des fiches informatives","Un glossaire","Un quiz de verification","Edwin vous accompagne"};
    private Integer [] logos = {R.drawable.logo_sheets,R.drawable.logo_dico,R.drawable.logo_quiz,R.drawable.logo_sheets2};
    private String [] textesDetails = {"Pourquoi se faire opérer ? \n Quel est le principe de l'intervention ? \nTout ce que vous devez savoir"
            ,"Un doute sur la signification d'un terme ?","Testez vos connaissances sur votre intervention",""};
    private Integer [] alpha = {0,0,0,1};
    private Button buttonMenu = null;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.tutorial_image_content, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(textes[position]);

        ImageView logoView = (ImageView) view.findViewById(R.id.logo);
        logoView.setImageResource(logos[position]);

        TextView textView2 = (TextView) view.findViewById(R.id.textDetails);
        textView2.setText(textesDetails[position]);

        final Button buttonMenu = (Button) view.findViewById(R.id.toMenu);
        //CACHE LE BOUTON DEMARRER DANS LES 3 PREMIERES VIEWPAGE MAIS PAS DANS LA DERNIERE
        buttonMenu.setAlpha(alpha[position]);
        buttonMenu.setClickable(false);

        //PERMET DE CLIQUER SUR LE BOUTON DEMARRER UNIQUEMENT DANS LA DERNIERE VIEWPAGE
        if(position == 3){
            buttonMenu.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    Intent i = new Intent(context,MenuActivity.class);
                    context.startActivity(i);

                }
            });
        }


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
