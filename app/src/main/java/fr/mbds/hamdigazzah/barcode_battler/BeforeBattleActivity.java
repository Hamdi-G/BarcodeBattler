package fr.mbds.hamdigazzah.barcode_battler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.mbds.hamdigazzah.barcode_battler.DataBaseHandler.DBHandler;
import fr.mbds.hamdigazzah.barcode_battler.Model.Character;
import fr.mbds.hamdigazzah.barcode_battler.Model.Shield;
import fr.mbds.hamdigazzah.barcode_battler.Model.Weapon;
import fr.mbds.hamdigazzah.barcode_battler.Services.BackgroundSoundService;

public class BeforeBattleActivity extends AppCompatActivity {

    Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_battle);

        ImageView characterImageView = (ImageView) findViewById(R.id.character_imageView);
        ImageView weaponImageView = (ImageView) findViewById(R.id.weapon_image_view);
        ImageView shieldImageView = (ImageView) findViewById(R.id.shield_imageView);
        TextView characterName = (TextView) findViewById(R.id.character_name_textView);
        TextView weaponName = (TextView) findViewById(R.id.weapon_name_textView);
        TextView shieldName = (TextView) findViewById(R.id.shield_name_textView);
        ProgressBar lifeProgressBar = (ProgressBar) findViewById(R.id.life_progressBar);
        ProgressBar attackProgressBar = (ProgressBar) findViewById(R.id.attack_progressBar);
        ProgressBar defenserogressBar = (ProgressBar) findViewById(R.id.defense_progressBar);
        Button buttonStart = (Button) findViewById(R.id.button_start_battle);

        attackProgressBar.setMax(20);
        defenserogressBar.setMax(20);

        DBHandler db = new DBHandler(this);
        svc=new Intent(this, BackgroundSoundService.class);


        Intent i = getIntent();

        final Character ch = db.getCharacter((int) i.getLongExtra("characterId", 0));
        final Weapon w = db.getWeapon((int) i.getLongExtra("weaponId", 0));
        ch.setWeapon(w);
        final Shield sh = db.getShield((int) i.getLongExtra("shieldId", 0));
        ch.setShield(sh);

        int idCh = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + ch.getImagePath(), null, null);
        characterImageView.setImageDrawable(getResources().getDrawable(idCh));

        int idW = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + w.getImagePath(), null, null);
        weaponImageView.setImageDrawable(getResources().getDrawable(idW));

        int idSh = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + sh.getImagePath(), null, null);
        shieldImageView.setImageDrawable(getResources().getDrawable(idSh));

        characterName.setText(ch.getName());
        weaponName.setText(ch.getWeapon().getName());
        shieldName.setText(ch.getShield().getName());

        lifeProgressBar.setProgress(ch.getLife());
        attackProgressBar.setProgress(ch.getWeapon().getDemage());
        defenserogressBar.setProgress(ch.getShield().getCapacity());

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BeforeBattleActivity.this, BattleActivity.class);
                i.putExtra("characterId", ch.getId());
                i.putExtra("weaponId", ch.getWeapon().getId());
                i.putExtra("shieldId", ch.getShield().getId());
                startActivity(i);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(svc);
    }
}
