package com.example.preeti.bartfare;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Preeti on 4/22/2015.
 */
public class DrawerLayoutTile extends ActionBarActivity implements AdapterView.OnItemClickListener{
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter,mAAdapter,mCAdapter,mLaAdapter,mLoAdapter;
    ArrayList<String> myFList,myAList,myCList,myLaList,myLoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        myFList = (ArrayList<String>) getIntent().getSerializableExtra("FuNameLst");
        myAList = (ArrayList<String>) getIntent().getSerializableExtra("AbbrLst");
        myCList = (ArrayList<String>) getIntent().getSerializableExtra("City");
        myLaList = (ArrayList<String>) getIntent().getSerializableExtra("Latitude");
        myLoList = (ArrayList<String>) getIntent().getSerializableExtra("Longitude");
//        Button Home= (Button)findViewById(R.id.slidehome);
//        Home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent HomeInt = new Intent(getApplication(),MainActivity.class);
//                startActivity(HomeInt);
//            }
//        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFList);
        mAAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myAList);
        mCAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myCList);
        mLaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myLaList);
        mLoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFList);
        mDrawerList.setAdapter(mAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.icon, R.string.open, R.string.close)  {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                Toast.makeText(getApplicationContext(),"Wait ! Loading Data....",Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();

            }


             public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                 getSupportActionBar().setTitle("Station Info");
                 Toast.makeText(getApplicationContext(),"Info Opened",Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

   private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);}

        private void selectItem(int position) {


            final InfoFragClass fragment = new InfoFragClass( );
            fragment.change(myFList.get(position), myAList.get(position), myCList.get(position));
            fragment.getLoc(myLaList.get(position),myLoList.get(position));
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragT = fragmentManager.beginTransaction();
            fragT.replace(R.id.content_frame, fragment).commit();
            fragmentManager.executePendingTransactions();






            //fragment.change(myFList.get(position), myAList.get(position), myCList.get(position));

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);

            mDrawerLayout.closeDrawer(mDrawerList);
        }}







    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


   }







