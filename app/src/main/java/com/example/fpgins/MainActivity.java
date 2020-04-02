package com.example.fpgins;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.fpgins.BottomNavigation.FPGAssist.FPGAssist;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.Login;
import com.example.fpgins.Login.Session.UserSessionManager;
import com.example.fpgins.SQLiteDB.DBHelper;
import com.example.fpgins.Utility.DefaultDialog;
import com.example.fpgins.BottomNavigation.Dashboard.DashboardFragment;
import com.example.fpgins.ui.customercare.CustomerCareFragment;
import com.example.fpgins.ui.motors.MotorsFragment;
import com.google.android.material.navigation.NavigationView;
import com.mindorks.paracamera.Camera;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    karleen choie

    private UserSessionManager mSession;
    private AppBarConfiguration mAppBarConfiguration;
    private ExpandableListView mExpandableListView;
    ExpandableListAdapter mExpandableListAdapter;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private Camera mCamera;
    private String mImageName;
    private ActionBarDrawerToggle mDrawerToggle;

    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setChildIndicator(null);
        mExpandableListView.setChildDivider(getResources().getDrawable(R.color.white));
        mExpandableListView.setDivider(getResources().getDrawable(R.color.white));
        mExpandableListView.setDividerHeight(2);

        prepareMenuData();
        populateExpandableList();

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //StartUp Screen
        DashboardFragment dashboardFragment = new DashboardFragment();
        setFragmentView(true, 0, 0, "tag_overview", dashboardFragment);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_overview, R.id.nav_products, R.id.nav_slideshow,
//                R.id.nav_customercare, R.id.nav_share, R.id.nav_send)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragment instanceof DashboardFragment) {

            new DefaultDialog.Builder(this)
                    .message("Are you sure you want to logout?")
                    .detail("")
                    .negativeAction("No", new DefaultDialog.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, String et) {
                            dialog.dismiss();
                        }
                    })
                    .positiveAction("Yes", new DefaultDialog.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, String et) {
                            Logout();
                        }
                    })
                    .build()
                    .show();

        } else {
            super.onBackPressed();
        }

    }

    private void Logout(){
        mSession = UserSessionManager.getInstance(MainActivity.this);
        mSession.clearPrefs();
        Intent i = new Intent(MainActivity.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        finish();
    }

    @Override
    protected void onResume() {


//        Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//
//        if (fragment instanceof MotorsFragment){
//            mMenu.setIcon(R.drawable.next);
//        } else {
//            mMenu.setIcon(R.drawable.email);
//        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_messages){
//
////            mToolbar.setTitle("Inbox");
//
//            NotifMessageFragment notifMessage = new NotifMessageFragment();
//
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment, notifMessage, "")
//                    .addToBackStack(null)
//                    .commit();
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Dashboard", true, false, "overview"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Claim", true, true, "claim");
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("File", false, false, "file");
        childModelsList.add(childModel);

        childModel = new MenuModel("All Records", false, false, "AllRecodrd");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Product", true, true, "product");
        headerList.add(menuModel);
        childModel = new MenuModel("Motor", false, false, "motor");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Transaction", true, false, "transaction");
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Customer Care", true, false, "customercare");
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("FPG 24/7 Assist", true, false, "fpgAssist"); //Menu of Java Tutorials
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
    }

//    private void populateMenu(MenuModel menuModels){
//
//        for (int i = 0; i <= menuModels.length - 1; i++){
//            if (menuModels)
//        }
//
//    }

    private void populateExpandableList() {

        mExpandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        mExpandableListView.setAdapter(mExpandableListAdapter);

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

            switch (headerList.get(groupPosition).idName){
                case "overview":
                    DashboardFragment dashboardFragment = new DashboardFragment();
                    setFragmentView(true, groupPosition, 0, "tag_overview", dashboardFragment);
                    break;
                case "customercare":
                    CustomerCareFragment customerCareFragment = new CustomerCareFragment();
                    setFragmentView(true, groupPosition, 0, "tag_customercare", customerCareFragment);
                    break;
                case "fpgAssist":
                    FPGAssist fpgAssist = new FPGAssist();
                    setFragmentView(true, groupPosition, 0, "", fpgAssist);
//                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                        startActivity(intent);
                    break;
                default:
                    break;
            }

            if (headerList.get(groupPosition).hasChildren == false){
                onBackPressed();
            }

            return false;
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    switch (model.idName){
                        case "motor":
                            MotorsFragment motorsFragment = new MotorsFragment();
                            setFragmentView(false, groupPosition, childPosition, "tag_child_motor", motorsFragment);
                            break;
                        case "file":
//                            Intent intent = new Intent
//                            mBundle = new Bundle();
//                            mBundle.putString("draftsCount", "");
//                            draftsFragment.setArguments(mBundle);
//                            setFragmentView(true, groupPosition, 0, "claims", draftsFragment);
                            break;
                        default:
                            break;
                    }
                }

                onBackPressed();

                return false;
            }
        });
    }

    public void takePictures(){
        mImageName = "Image" + System.currentTimeMillis();
        mCamera = new Camera.Builder()
                .resetToCorrectOrientation(true)
                .setTakePhotoRequestCode(0)
                .setDirectory("FPG")
                .setName(mImageName)
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)
                .build(MainActivity.this);

        try {
            mCamera.takePicture();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO){
            Bitmap bitmap = mCamera.getCameraBitmap();
            if (bitmap != null){
                //upload image in SQLite
                uploadImage();
            } else {
                Toast.makeText(this, "Picture not taken", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage(){

        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(MainActivity.this));

        String draft = userData.getDraftsCount();

        if (userData.isDrafts() && userData.getDraftsCount() != null){
            //To increment the trigger button is save as draft in motor file
            //when the value is TRUE it will automatically increment
            char lastChar = draft.charAt(draft.length() - 1);
            int count = Integer.parseInt(String.valueOf(lastChar));
            userData.setDraftsCount(userData.getEmail() + (count + 1));
        } else {
            if (draft == null || draft == ""){
                userData.setDraftsCount(userData.getEmail() + "1");
            }
        }

        userData.setDrafts(false);

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.open();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String formattedDate = df.format(c);
        boolean isInserted = dbHelper.inserImagesDetails(mCamera.getCameraBitmap(), userData.getDraftsCount(), formattedDate, mImageName);

        if (isInserted){
            Toast.makeText(this, "Upload completed", Toast.LENGTH_LONG).show();
//            ClaimsActivity claimsFragment = (ClaimsActivity) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//            claimsFragment.getImages(userData.getDraftsCount());
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private void setFragmentView(boolean isHeader, int position, int childPosition, String tag, Fragment fragment){

//        if (isHeader){
//            mToolbar.setTitle(headerList.get(position).menuName);
//        } else {
//            MenuModel model = childList.get(headerList.get(position)).get(childPosition);
//            mToolbar.setTitle(model.menuName);
//        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, tag)
                .addToBackStack(headerList.get(position).menuName)
                .commit();
    }

}
