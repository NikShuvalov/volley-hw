package shuvalov.nikita.whatartinwalmart;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String API_KEY= "6we7gvkbu5sujb78rge8y5dy";


    //Search parameter is added in the form of %s to be used in String.format()
    public static final String REQUEST_URL = "http://api.walmartlabs.com/v1/search?query=%s&format=json&apiKey=6we7gvkbu5sujb78rge8y5dy";

    Button chocoButt, teaButt, cerealButt;
    RecyclerView mRecyclerView;
    RecyclerWalmartAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chocoButt= (Button)findViewById(R.id.chocolate_button);
        teaButt= (Button)findViewById(R.id.tea_button);
        cerealButt= (Button)findViewById(R.id.cereal_button);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                String searchParam="";
                if(info!=null && info.isConnected()){
                    RequestQueue queue = Volley.newRequestQueue(view.getContext());
                    switch (view.getId()){
                        case R.id.cereal_button:
                            searchParam="cereal";
                            break;
                        case R.id.chocolate_button:
                            searchParam="chocolate";
                            break;
                        case R.id.tea_button:
                            searchParam="tea";
                            break;
                    }
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, String.format(REQUEST_URL, searchParam), new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ArrayList<WalmartItem> walmartItems = new ArrayList<>();
                            try {
                                JSONObject fullData = new JSONObject(response);
                                JSONArray itemsArray = fullData.getJSONArray("items");
                                for (int i = 0; i<itemsArray.length();i++){
                                    JSONObject individualItem= itemsArray.getJSONObject(i);
                                    String name = individualItem.getString("name");
                                    walmartItems.add(new WalmartItem(name));
                                }
                                mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false);
                                mAdapter = new RecyclerWalmartAdapter(walmartItems);
                                mRecyclerView.setLayoutManager(linearLayoutManager);
                                mRecyclerView.setAdapter(mAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(stringRequest);


                }else{
                    Toast.makeText(view.getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        chocoButt.setOnClickListener(onClickListener);
        teaButt.setOnClickListener(onClickListener);
        cerealButt.setOnClickListener(onClickListener);
    }

}
