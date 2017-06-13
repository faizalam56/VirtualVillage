package zmq.com.virtualvillage.gamefloor;

import android.graphics.Canvas;
import android.graphics.Paint;

import zmq.com.virtualvillage.sprite.ShahSprite;
import zmq.com.virtualvillage.utility.GlobalVariables;


/**
 * Created by zmq162 on 28/12/16.
 */
public class CanvasBackgroundPaint {

    int width = GlobalVariables.width;
    int height = GlobalVariables.height;
    Paint paint;

    public CanvasBackgroundPaint(){
        paint = new Paint();
    }

    public void backGroundPiant(Canvas g,ShahSprite sprite) {
        int x_delta=sprite.getWidth()/2;
        int y_delta=sprite.getHeight()/2;
        boolean flag=false;
        for(int i=0;i<=width/x_delta+1;i++)
        {
            for(int j=0;j<=height/y_delta+1;j++)
            {
                if(flag==false)
                {
                    if(i%2==0)
                        j=0;
                    else
                        j=1;
                    flag=true;
                }
                sprite.setPosition((i-1)*x_delta, (j-1)*y_delta);
                sprite.paint(g,null);
            }
            flag=false;
        }
    }

}
