package fr.mbds.hamdigazzah.barcode_battler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import fr.mbds.hamdigazzah.barcode_battler.DataBaseHandler.DBHandler;
import fr.mbds.hamdigazzah.barcode_battler.Model.Character;
import fr.mbds.hamdigazzah.barcode_battler.Model.Shield;
import fr.mbds.hamdigazzah.barcode_battler.Model.Weapon;
import fr.mbds.hamdigazzah.barcode_battler.Services.BackgroundSoundService;
import fr.mbds.hamdigazzah.barcode_battler.Utils.CharacterGenerator;
import fr.mbds.hamdigazzah.barcode_battler.Utils.ShieldGenerator;
import fr.mbds.hamdigazzah.barcode_battler.Utils.Utils;
import fr.mbds.hamdigazzah.barcode_battler.Utils.WeaponGenerator;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class BattleActivity extends AppCompatActivity {

    DBHandler db;
    Intent svc;

    Character playerCharacter;
    ProgressBar playerLife;
    ProgressBar playerWeaponDamage;
    ProgressBar playerShieldCapacity;
    TextView playerCharacterName;
    ImageView playerCharacterImageView;
    ImageView playerWeaponImageView;
    ImageView playerShieldImageView;

    Character CPUCharacter;
    ProgressBar CPULife;
    ProgressBar CPUWeaponDamage;
    ProgressBar CPUShieldCapacity;
    TextView CPUCharacterName;
    ImageView CPUCharacterImageView;
    ImageView CPUWeaponImageView;
    ImageView CPUShieldImageView;

    TextView commentTextView;
    Button attackbButton;
    Button potionButton;
    KonfettiView konfettiView;
    RelativeLayout layout;

    MediaPlayer player;
    MediaPlayer player2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        commentTextView = (TextView) findViewById(R.id.comment_textView);
        attackbButton = (Button) findViewById(R.id.attack_button);
        potionButton = (Button) findViewById(R.id.potion_button);
        konfettiView = (KonfettiView) findViewById(R.id.konfettiView);
        layout = (RelativeLayout) findViewById(R.id.layout_background);

        layout.setBackground(getResources().getDrawable(R.drawable.battle_background4));

        attackbButton.setVisibility(View.GONE);
        potionButton.setVisibility(View.GONE);

        player = new MediaPlayer();
        player2 = new MediaPlayer();

        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("steels.mp3");
            player2.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player2.prepare();
            player2.setVolume(50, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }


        db = new DBHandler(this);
        svc = new Intent(this, BackgroundSoundService.class);


        initPlayerCharacter();
        initCPUCharacter();

        welcomeMessage();

        //battle core
        attackbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CPUCharacter.getLife() > 0) {
                    attackEnemy();
                    attackbButton.setVisibility(View.GONE);;

                }
                if (playerCharacter.getLife() > 0) {

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    reciveAttack();
                                    attackbButton.setVisibility(View.VISIBLE);
                                }
                            }, 4000);
                }
            }
        });

        potionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int potion =  Utils.randomInt(100 - playerCharacter.getLife());
                playerCharacter.setLife(playerCharacter.getLife() + potion);
                playerLife.setProgress(playerCharacter.getLife());
                potionButton.setVisibility(View.GONE);
                commentTextView.setText("vous avez gagné un potion de " + potion + " points!");
            }
        });

    }

    public void initPlayerCharacter() {

        playerLife = (ProgressBar) findViewById(R.id.p_life_progressBar);
        playerWeaponDamage = (ProgressBar) findViewById(R.id.p_weapon_progressBar);
        playerShieldCapacity = (ProgressBar) findViewById(R.id.p_shield_progressBar);
        playerCharacterName = (TextView) findViewById(R.id.p_character_name_textView);
        playerCharacterImageView = (ImageView) findViewById(R.id.p_character_imageView);
        playerWeaponImageView = (ImageView) findViewById(R.id.p_weapon_imageView);
        playerShieldImageView = (ImageView) findViewById(R.id.p_shield_imageView);

        playerWeaponDamage.setMax(25);
        playerShieldCapacity.setMax(20);

        Intent i = getIntent();

        playerCharacter = db.getCharacter(i.getIntExtra("characterId", 0));
        Weapon playerWeapon = db.getWeapon(i.getIntExtra("weaponId", 0));
        playerCharacter.setWeapon(playerWeapon);
        Shield playerShield = db.getShield(i.getIntExtra("shieldId", 0));
        playerCharacter.setShield(playerShield);

        int idCh = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + playerCharacter.getImagePath(), null, null);
        playerCharacterImageView.setImageDrawable(getResources().getDrawable(idCh));

        int idW = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + playerWeapon.getImagePath(), null, null);
        playerWeaponImageView.setImageDrawable(getResources().getDrawable(idW));

        int idSh = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + playerShield.getImagePath(), null, null);
        playerShieldImageView.setImageDrawable(getResources().getDrawable(idSh));

        playerLife.setProgress(playerCharacter.getLife());
        playerWeaponDamage.setProgress(playerCharacter.getWeapon().getDemage());
        playerShieldCapacity.setProgress(playerCharacter.getShield().getCapacity());
        playerCharacterName.setText(playerCharacter.getName());

    }

    public void initCPUCharacter() {

        CPULife = (ProgressBar) findViewById(R.id.c_life_progressBar);
        CPUWeaponDamage = (ProgressBar) findViewById(R.id.c_weapon_progressBar);
        CPUShieldCapacity = (ProgressBar) findViewById(R.id.c_shield_progressBar);
        CPUCharacterName = (TextView) findViewById(R.id.c_character_name_textView);
        CPUCharacterImageView = (ImageView) findViewById(R.id.c_character_imageView);
        CPUWeaponImageView = (ImageView) findViewById(R.id.c_weapon_imageView);
        CPUShieldImageView = (ImageView) findViewById(R.id.c_shield_imageView);

        CPUWeaponDamage.setMax(25);
        CPUShieldCapacity.setMax(20);

        CPUCharacter = new CharacterGenerator().generate(generateRandomBarCode());
        Weapon CPUWeapon = new WeaponGenerator().generate(generateRandomBarCode());
        CPUCharacter.setWeapon(CPUWeapon);
        Shield CPUShield = new ShieldGenerator().generate(generateRandomBarCode());
        CPUCharacter.setShield(CPUShield);

        int idCh = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + CPUCharacter.getImagePath(), null, null);
        CPUCharacterImageView.setImageDrawable(getResources().getDrawable(idCh));

        int idW = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + CPUWeapon.getImagePath(), null, null);
        CPUWeaponImageView.setImageDrawable(getResources().getDrawable(idW));

        int idSh = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + CPUShield.getImagePath(), null, null);
        CPUShieldImageView.setImageDrawable(getResources().getDrawable(idSh));

        CPULife.setProgress(CPUCharacter.getLife());
        CPUWeaponDamage.setProgress(CPUCharacter.getWeapon().getDemage());
        CPUShieldCapacity.setProgress(CPUCharacter.getShield().getCapacity());
        CPUCharacterName.setText(CPUCharacter.getName());

    }

    public void attackEnemy() {

        //sound effect
        player2.start();

        //animation
        TranslateAnimation animation = new TranslateAnimation(0, 200, 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        playerCharacterImageView.startAnimation(animation);

        //attack
        commentTextView.setText("Vous avez attaqué le CPU avec " + String.valueOf(
                playerCharacter.attack(CPUCharacter)
        ) + " points");
        CPULife.setProgress(CPUCharacter.getLife());
        if (CPUCharacter.getLife() <= 0) {
            gameWin();
        }
    }

    public void reciveAttack() {

        //sound effect
        player2.start();

        TranslateAnimation animation = new TranslateAnimation(0, -200, 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(false);

        CPUCharacterImageView.startAnimation(animation);

        commentTextView.setText("Le CPU vous a attaqué avec " + String.valueOf(
                CPUCharacter.attack(playerCharacter)
        ) + " points");
        playerLife.setProgress(playerCharacter.getLife());
        if (playerCharacter.getLife() <= 0) {
            gameLose();
        }
    }

    public void gameWin() {
        commentTextView.setText("You win");
        konfettiView.build()
                .addColors(R.color.colorAccent, R.color.beige, Color.BLACK)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .stream(300, 5000L);

        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("victory.mp3");
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.setVolume(100, 100);
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        player.stop();
                        exitBattle();
                    }
                },
                9000);
    }

    public void gameLose() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over")
                .setPositiveButton("Rejouer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        player.stop();
                        exitBattle();
                    }
                })
                .show();

        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("gameover.mp3");
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.setVolume(100, 100);
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exitBattle() {
        startActivity(new Intent(BattleActivity.this, MainActivity.class));
    }

    public String generateRandomBarCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(0, 999999999));
    }

    private void welcomeMessage() {
        commentTextView.setText("3");
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        commentTextView.setText("2");
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        commentTextView.setText("1");
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        commentTextView.setText("C'est parti!");
                                                        new android.os.Handler().postDelayed(
                                                                new Runnable() {
                                                                    public void run() {
                                                                        commentTextView.setText("");
                                                                        attackbButton.setVisibility(View.VISIBLE);
                                                                        potionButton.setVisibility(View.VISIBLE);
                                                                    }
                                                                }, 2000);
                                                    }
                                                }, 2000);
                                    }
                                }, 2000);
                    }
                }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(svc);
    }
}
