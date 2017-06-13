package zmq.com.virtualvillage.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zmq162 on 14/12/16.
 */
public abstract class BaseSurface extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    SurfaceHolder surfaceHolder;
    GameThread gameThread;
    public boolean destroyed=false;
    public BaseSurface(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(gameThread==null){
            gameThread = new GameThread(surfaceHolder,this);
        }
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.running = false;
        destroyed = true;
        System.out.println(" BaseSurface surfaceDestroyed Called...");
    }

    public class GameThread extends Thread{
        SurfaceHolder surfaceHolder;
        BaseSurface newCanvas;
        public boolean running=true;
        public GameThread(SurfaceHolder surfaceHolder,BaseSurface newCanvas){
            this.surfaceHolder = surfaceHolder;
            this.newCanvas = newCanvas;
        }
        @Override
        public void run() {
            Canvas canvas;
           while (running){
               canvas=null;
               try{
                   canvas = surfaceHolder.lockCanvas(null);
                   synchronized (surfaceHolder){
                       if(running){
                           newCanvas.drawSomething(canvas);
                       }
                   }
                   try{
                       Thread.sleep(5);
                   }catch (InterruptedException e){

                   }
               }finally {
                   if(canvas!=null) {
                       surfaceHolder.unlockCanvasAndPost(canvas);
                   }
               }
           }

        }
    }
    abstract protected  void drawSomething(Canvas g) ;
}
