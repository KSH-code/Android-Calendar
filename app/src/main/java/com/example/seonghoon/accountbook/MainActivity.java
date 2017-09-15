package com.example.seonghoon.accountbook;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static long time;
        private static ArrayList<TextView> touchedText = new ArrayList<>();

        public PlaceholderFragment() {
            time = System.currentTimeMillis();
        }


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * 현재 달력의 날짜를 ms로 반환
         * <pre>
         *  <b>History:</b>
         *      KSH-Code, 1.0, 2017.09.15
         * </pre>
         *
         * @return time
         */
        public static long getTime() {
            return time;
        }

        /**
         * 달력의 데이터를 변경하기 위해 time을 변경
         * <pre>
         * <b>History:</b>
         *  KSH-Code, 1.0, 2017.09.15
         * </pre>
         *
         * @param next 다음달로 넘어갈거면 true 아니면 false
         */
        public static void setTime(boolean next) {
            Date date = new Date(getTime());
            if (next) {
                date.setMonth(date.getMonth() + 1);
            } else {
                date.setMonth(date.getMonth() - 1);
            }
            time = date.getTime();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            rootView = inflater.inflate(R.layout.fragment_main1, container, false);
            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                // 달력의 데이터를 draw 한다.
                drawDate();

                // Prev 버튼 리스너 연결
                Button prev = (Button) getView().findViewById(R.id.prev);
                prev.setOnClickListener(new ButtonClickListner(false));
                // Next 버튼 리스너 연결
                Button next = (Button) getView().findViewById(R.id.next);
                next.setOnClickListener(new ButtonClickListner(true));
            }
        }

        /**
         * 달력의 데이터를 draw 해준다.
         * <pre>
         * <b>History:</b>
         *  KSH-Code, 1.0, 2017.09.15
         * </pre>
         */
        private void drawDate() {
            DateFormat df = new SimpleDateFormat("yyyy-MM");
            GridLayout gridLayout = (GridLayout) getView().findViewById(R.id.calendarView);
            LinearLayout linearLayout;
            TextView textView;
            int date = 1;
            Date today = new Date(getTime());
            today.setDate(1);
            int day = today.getDay();
            TextView textDate = (TextView) getView().findViewById(R.id.date);
            textDate.setText(df.format(today));
            for (int i = 1; i < gridLayout.getChildCount(); i++) {
                linearLayout = (LinearLayout) gridLayout.getChildAt(i);
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    textView = (TextView) linearLayout.getChildAt(j);
                    textView.setText("");
                    if (day > 1) {
                        day--;
                    } else {
                        textView.setText(date + "");
                        date++;
                    }
                }
            }
        }

        /**
         * Next와 Prev 버튼 작동을 위한 Listner
         */
        class ButtonClickListner implements View.OnClickListener {
            private final boolean next;

            /**
             * 리스너의 생성자
             * <pre>
             *  <b>History:</b>
             *      KSH-Code, 1.0, 2017.09.15
             * </pre>
             *
             * @param next 다음달이면 true 아니면 false
             */
            public ButtonClickListner(boolean next) {
                this.next = next;
            }

            @Override
            public void onClick(View view) {
                setTime(next);//날짜를 셋팅
                drawDate();//달력에 draw
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
