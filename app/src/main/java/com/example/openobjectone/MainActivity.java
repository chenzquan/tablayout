package com.example.openobjectone;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.openobjectone.AnimatedIndicator.DachshundIndicator;
import com.example.openobjectone.AnimatedIndicator.LineMoveIndicator;
import com.example.openobjectone.pagefragmenttest.PageFragment;
import com.example.openobjectone.pagefragmenttest.SimpleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String [] DOG_BREEDS = {"chenzquan", "Beagle", "Bulldog", "Poodle"};



//  private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private DachshundTabLayout tabLayout;



  //  private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
//
//
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onClickDachshund(View view){
        tabLayout.setAnimatedIndicator(new DachshundIndicator(tabLayout));
    }

    public void onClickLineMove(View view){
        tabLayout.setAnimatedIndicator(new LineMoveIndicator(tabLayout));
    }



    public class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new PageFragment();
        }

        @Override
        public int getCount() {
            return DOG_BREEDS.length;
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return DOG_BREEDS[position];
        }
    }


    public static class PageFragment extends Fragment{

        public PageFragment(){

        }


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_page,container,false);

//            Button Dachshund = view.findViewById(R.id.asd);
//            Dachshund.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    tabLayout.setAnimatedIndicator(new DachshundIndicator(tabLayout));
//                }
//            });

            return view;
        }
    }



}
