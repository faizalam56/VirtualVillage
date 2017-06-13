package zmq.com.virtualvillage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import zmq.com.virtualvillage.gamefloor.NewFloor;
import zmq.com.virtualvillage.utility.GlobalVariables;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new NewFloor(this));
        getSupportActionBar().hide();
        deviceInfo();
    }

    private void deviceInfo(){
        GlobalVariables.getResource = getResources();
        Display display = getWindowManager().getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float a = metrics.densityDpi;
        GlobalVariables.xScale_factor = (float) (metrics.widthPixels / 800.0);
        GlobalVariables.yScale_factor = (float) (metrics.heightPixels / 480.0);

        System.out.println(" X factor " + GlobalVariables.xScale_factor);
        System.out.println(" Y factor " + GlobalVariables.yScale_factor);

        GlobalVariables.width = metrics.widthPixels;
        GlobalVariables.height = metrics.heightPixels;

        System.out.println(" width " + GlobalVariables.width);
        System.out.println(" Height " + GlobalVariables.height);
    }
}
