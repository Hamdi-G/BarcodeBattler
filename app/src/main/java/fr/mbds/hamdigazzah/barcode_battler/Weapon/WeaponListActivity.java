package fr.mbds.hamdigazzah.barcode_battler.Weapon;

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

import fr.mbds.hamdigazzah.barcode_battler.Services.BackgroundSoundService;
import fr.mbds.hamdigazzah.barcode_battler.Shield.ShieldListActivity;
import fr.mbds.hamdigazzah.barcode_battler.DataBaseHandler.DBHandler;
import fr.mbds.hamdigazzah.barcode_battler.Model.Weapon;
import fr.mbds.hamdigazzah.barcode_battler.R;

public class WeaponListActivity extends AppCompatActivity {

    List<Weapon> weaponList;
    ListView weaponListView;
    DBHandler db;
    Intent svc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_list);

        db = new DBHandler(this);
        svc=new Intent(this, BackgroundSoundService.class);

        weaponList = db.getAllWeapons();

        for (Weapon ch : weaponList){
            Gson gson = new Gson();
            String json = gson.toJson(ch);
            Log.d("weapons ",json);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeaponListActivity.this,WeaponAddActivity.class));
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        weaponListView = (ListView) findViewById(R.id.weaponListView);
        final WeaponListActivity.WeaponAdapter adapter = new WeaponListActivity.WeaponAdapter(weaponList);
        weaponListView.setAdapter(adapter);
        weaponListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent oldIntent = getIntent();
                Intent i = new Intent(WeaponListActivity.this,ShieldListActivity.class);
                i.putExtra("characterId",oldIntent.getLongExtra("characterId", 0));
                i.putExtra("weaponId",id);
                startActivity(i);
            }
        });
    }

    public class WeaponAdapter extends BaseAdapter {

        public WeaponAdapter(List<Weapon> list) {
        }

        @Override
        public int getCount() {
            return weaponList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return weaponList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.weapon_item,null);

            ImageView image = (ImageView) convertView.findViewById(R.id.weapon_image_view_item);
            TextView text_view_name = (TextView)convertView.findViewById(R.id.weapon_text_view_name);
            ProgressBar damage = (ProgressBar) convertView.findViewById(R.id.weapon_damage_progress_bar);
            damage.setMax(25);

            int id = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + weaponList.get(position).getImagePath(), null, null);
            image.setImageDrawable(getResources().getDrawable(id));

            text_view_name.setText(weaponList.get(position).getName());
            damage.setProgress(weaponList.get(position).getDemage());
            return convertView;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(svc);
    }

}
