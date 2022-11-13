package eu.ase.ro.livescoringapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.classes.Match;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ListView listView;
    ArrayList<Match> matches = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.list_view_homeFragment);
        Thread jsonParser = new Thread(new JSONParser());
        jsonParser.start();
//        try {
//            JSONObject obj = new JSONObject(loadFromJsonAsset());
//            JSONArray array = obj.getJSONArray("fixtures");
//            HashMap<String,String> list;
//            ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject o = array.getJSONObject(i);
//                String id = o.getString("id");
//                String teams = o.getString("teams");
//                list = new HashMap<>();
//                list.put("id",id);
//                list.put("teams",teams);
//                arrayList.add(list);
//            }
//            ListAdapter adapter = new SimpleAdapter(getContext(),arrayList,R.layout.list_view_design,new String[]{"id","teams"},new int[]{R.id.id,R.id.teams});
//            listView.setAdapter(adapter);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return view;
    }

    public String loadFromJsonAsset(){
        String json = null;
        try {
            InputStream in =getContext().getAssets().open("fixtures.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            json = new String(buffer,"UTF-8");
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public class JSONParser implements Runnable {

        @Override
        public void run() {
            try {
                URL url = new URL("https://www.jsonkeeper.com/b/LP9I");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = bufferedReader.readLine();
                Log.i("Test",line);
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                connection.disconnect();

                getMatchesFromJson(line);

                Handler threadHandler = new Handler(Looper.getMainLooper());
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // UI logic
                        ArrayAdapter<Match> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, matches);
                        listView.setAdapter(adapter);;
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void getMatchesFromJson(String json) {
        if(json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray jsonArray = jsonObject.getJSONArray("matches");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject matchJson = jsonArray.getJSONObject(i);
                    Integer id = matchJson.getInt("id");
                    String homeTeam = matchJson.getString("home");
                    String awayTeam = matchJson.getString("away");

                    Match newMatch = new Match(id,homeTeam,awayTeam);
                    matches.add(newMatch);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}