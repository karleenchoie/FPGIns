package com.example.fpgins.BottomNavigation.ClientDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.BottomNavigation.Claims.DraftsActivity;
import com.example.fpgins.DataModel.FirstSlideMenuData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.ui.NotificationMessage.NotifMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewAll extends AppCompatActivity {

    private List<FirstSlideMenuData> mData = new ArrayList<>();
    private GridView mGridView;
    private ViewAllAdapter mAdapter;
    private ImageView mBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_dashboard);

        mGridView = findViewById(R.id.grid);
        mAdapter = new ViewAllAdapter(mData, getApplicationContext());

        mGridView.setAdapter(mAdapter);
        mBackButton = findViewById(R.id.img_backbutton);

        getFirstMenuData();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirstSlideMenuData data = mData.get(position);

                Intent intent = new Intent(ViewAll.this, NewsandEvents.class);

                //Extras upon viewing
                intent.putExtra("id", data.getId());
                intent.putExtra("title", data.getTitle());
                intent.putExtra("description", data.getDescription());
                intent.putExtra("link", data.getLink());
                intent.putExtra("postDate", data.getPostDate());
                intent.putExtra("categoryName", data.getCategoryName());
                intent.putExtra("pictures", data.getPictures());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void getFirstMenuData(){

        mData.clear();

        Cloud.getNewsAndEvents(new Cloud.ResultListener() {
            @Override
            public void onResult(JSONObject result) {
                int returnCode;
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject = result;
                    returnCode = Integer.parseInt(jsonObject.get("code").toString());
                } catch (JSONException e){
                    returnCode = Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR;
                    e.printStackTrace();
                }

                if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
                    //FAIL
                    try {
                        String message = jsonObject.getString("message");
                        Toast.makeText(ViewAll.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
                        mGridView.setAdapter(mAdapter);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });

        mAdapter.notifyDataSetChanged();

    }

    private void generateResult(JSONArray jsonArray){
        ArrayList<String> pictures = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String title = Html.fromHtml(jsonObject.getString("title")).toString();
                String content = Html.fromHtml(jsonObject.getString("content")).toString();
                String link = jsonObject.getString("link");
                String postDate = jsonObject.getString("post_date");
                String categoryName = jsonObject.getString("category_name");

                JSONArray files = jsonObject.getJSONArray("files");
                JSONObject obj = files.getJSONObject(0);
                String pic = obj.getString("file_url");

                if (files.length() > 0){
                    for (int m = 0; m < files.length(); m++){
                        JSONObject jsonPics = files.getJSONObject(m);
                        String pict = jsonPics.getString("file_url");
                        pictures.add(pict);
                    }
                } else {
                    pictures.add(null);
                }

                FirstSlideMenuData data = new FirstSlideMenuData(id, title, content, link, postDate, categoryName, pictures);
                mData.add(data);

                pictures.clear();
            }



        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
