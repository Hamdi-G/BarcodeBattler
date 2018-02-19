package fr.mbds.hamdigazzah.barcode_battler.Character;

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

import fr.mbds.hamdigazzah.barcode_battler.DataBaseHandler.DBHandler;
import fr.mbds.hamdigazzah.barcode_battler.Model.Character;
import fr.mbds.hamdigazzah.barcode_battler.R;
import fr.mbds.hamdigazzah.barcode_battler.Services.BackgroundSoundService;
import fr.mbds.hamdigazzah.barcode_battler.Weapon.WeaponListActivity;

public class CharacterListActivity extends AppCompatActivity {
    
    List<Character> characterList;
    ListView characterListView;

    DBHandler db;
    Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);


        db = new DBHandler(this);
        svc=new Intent(this, BackgroundSoundService.class);


        characterList = db.getAllCharacters();

        for (Character ch : characterList){
            Gson gson = new Gson();
            String json = gson.toJson(ch);
            Log.d("characterrr ",json);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CharacterListActivity.this,CharacterAddActivity.class));
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        characterListView = (ListView) findViewById(R.id.characterListView);
        final CharacterAdapter adapter = new CharacterAdapter(characterList);
        characterListView.setAdapter(adapter);
        characterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CharacterListActivity.this,WeaponListActivity.class);
                i.putExtra("characterId",id);
                startActivity(i);
            }
        });
    }

    public class CharacterAdapter extends BaseAdapter {

        public CharacterAdapter(List<Character> list) {
        }

        @Override
        public int getCount() {
            return characterList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return characterList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.character_item,null);

            TextView text_view_name = (TextView)convertView.findViewById(R.id.text_view_name);
            ProgressBar life = (ProgressBar) convertView.findViewById(R.id.lifePB);
            ImageView image = (ImageView) convertView.findViewById(R.id.character_image_view_item);

            String imageName = characterList.get(position).getImagePath();
            int id = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + imageName, null, null);
            image.setImageDrawable(getResources().getDrawable(id));

            text_view_name.setText(characterList.get(position).getName());
            life.setProgress(characterList.get(position).getLife());
            return convertView;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(svc);
    }

}
