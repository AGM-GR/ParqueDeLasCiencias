package rafalex.pdm.ugr.parquedelasciencias;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MapaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        ExtendedViewPager mViewPager = (ExtendedViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter());

        return view;
    }

    static class TouchImageAdapter extends PagerAdapter {

        private static int[] images = { R.drawable.mapa, R.drawable.plano, R.drawable.macroscopio, R.drawable.rapaces, R.drawable.espacios_naturales, R.drawable.pabellon_darwin, R.drawable.mariposario,
                R.drawable.botanico, R.drawable.gimnasia_mental, R.drawable.agua_energia, R.drawable.via_lactea, R.drawable.pendulo, R.drawable.lago, R.drawable.torre, R.drawable.planetario,
                R.drawable.observatorio, R.drawable.astronomia};

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());
            img.setImageResource(images[position]);
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
