package fr.eseo.pfe.edwin;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.back_tuto_sheets,R.drawable.back_tuto_dico,R.drawable.back_tuto_quiz,R.drawable.back_tuto_go};
    private String [] textes = {"Des fiches informatives","Un glossaire","Un quiz de verification","C'est parti"};
    private Integer [] logos = {R.drawable.logo_sheets,R.drawable.logo_dico,R.drawable.logo_quiz,R.drawable.logo_sheets2};

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
