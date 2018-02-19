package fr.mbds.hamdigazzah.barcode_battler.Shield;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import fr.mbds.hamdigazzah.barcode_battler.BeforeBattleActivity;
import fr.mbds.hamdigazzah.barcode_battler.DataBaseHandler.DBHandler;
import fr.mbds.hamdigazzah.barcode_battler.Model.Shield;
import fr.mbds.hamdigazzah.barcode_battler.R;
import fr.mbds.hamdigazzah.barcode_battler.Services.BackgroundSoundService;

public class ShieldListActivity extends AppCompatActivity {

    List<Shield> shieldList;
    ListView shieldListView;

    DBHandler db;
    Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shield_list);

        db = new DBHandler(this);
        svc=new Intent(this, BackgroundSoundService.class);

        shieldList = db.getAllShields();

        for (Shield ch : shieldList){
            Gson gson = new Gson();
            String json = gson.toJson(ch);
            Log.d("shields ",json);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShieldListActivity.this,ShieldAddActivity.class));
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        shieldListView = (ListView) findViewById(R.id.shieldListView);
        final ShieldListActivity.ShieldAdapter adapter = new ShieldListActivity.ShieldAdapter(shieldList);
        shieldListView.setAdapter(adapter);
        shieldListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent oldIntent = getIntent();
                Intent i = new Intent(ShieldListActivity.this, BeforeBattleActivity.class);
                i.putExtra("characterId", oldIntent.getLongExtra("characterId", 0));
                i.putExtra("weaponId", oldIntent.getLongExtra("weaponId", 0));
                i.putExtra("shieldId",id);
                startActivity(i);
            }
        });
    }

    public class ShieldAdapter extends BaseAdapter {

        public ShieldAdapter(List<Shield> list) {
        }

        @Override
        public int getCount() {
            return shieldList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return shieldList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.shield_item,null);

            ImageView image = (ImageView) convertView.findViewById(R.id.shield_image_view_item);
            TextView text_view_name = (TextView)convertView.findViewById(R.id.shield_text_view_name);
            ProgressBar capacity = (ProgressBar) convertView.findViewById(R.id.shield_capacity_progress_bar);
            capacity.setMax(20);

            int id = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + shieldList.get(position).getImagePath(), null, null);
            image.setImageDrawable(getResources().getDrawable(id));

            text_view_name.setText(shieldList.get(position).getName());
            capacity.setProgress(shieldList.get(position).getCapacity());
            return convertView;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(svc);
    }

}
