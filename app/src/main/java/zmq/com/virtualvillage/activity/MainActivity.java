package zmq.com.virtualvillage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import zmq.com.virtualvillage.R;
import zmq.com.virtualvillage.utility.GlobalVariables;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout splash;
    LinearLayout menu;
    ImageButton designMap,playGame,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        splash = (FrameLayout) findViewById(R.id.splash);
        menu = (LinearLayout) findViewById(R.id.menu);
        designMap = (ImageButton) findViewById(R.id.design_map);
        playGame = (ImageButton) findViewById(R.id.play_game);
        exit = (ImageButton) findViewById(R.id.exit_game);

        splash.setOnClickListener(this);
        menu.setOnClickListener(this);
        designMap.setOnClickListener(this);
        playGame.setOnClickListener(this);
        exit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.splash:
                splash.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
                break;
            case R.id.design_map:
                Intent intent = new Intent();
                intent.setClass(this,GameActivity.class);
                startActivity(intent);
                break;
            case R.id.play_game:
                break;
            case R.id.exit_game:
                exitApp();
                break;
        }
    }

    private void exitApp(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit Application?");
        dialog.setMessage("Click yes to exit!");
//        dialog.setIcon(R.drawable.icon);

        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }
}
