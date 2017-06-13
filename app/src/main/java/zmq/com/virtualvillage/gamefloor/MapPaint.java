package zmq.com.virtualvillage.gamefloor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import zmq.com.virtualvillage.sprite.ShahSprite;


/**
 * Created by zmq162 on 28/12/16.
 */
public class MapPaint {
    ShahSprite designSprite,roadSprite;
    Paint paint;
    int array[][];
    String houseNum[][];
    int initial_x;
    int initial_y;

    public MapPaint(ShahSprite designSprite,ShahSprite roadSprite,int array[][],String houseNum[][],int initial_x,int initial_y){

        this.designSprite=designSprite;
        this.roadSprite=roadSprite;
        this.array=array;
        this.houseNum=houseNum;
        this.initial_x=initial_x;
        this.initial_y=initial_y;
        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    public void toolTilePaint(Canvas g,int number_tool)
    {
        for(int i=0;i<number_tool-1;i++)
        {
            int x=(i%4)*designSprite.getWidth();
            int y=(i/4)*designSprite.getHeight();

            if(i<11){
                designSprite.setPosition(x, y);
                designSprite.setFrame(i);
                designSprite.paint(g,null);
            }
            else if(i>11){
                roadSprite.setPosition(x, y);
                roadSprite.setFrame(i-12);
                roadSprite.paint(g,null);
            }
        }
    }

    public void designMapPaint(Canvas g,ShahSprite moveSprite64_64,ShahSprite moveSprite64_32,ShahSprite moveSprite,int tempNumber){
        int x_delta=designSprite.getWidth()/2;
        int y_delta=designSprite.getHeight()/4;
        int x_move=moveSprite.getX();
        int y_move=moveSprite.getY();

        for(int i=0;i<array.length;i++)
        {
            for(int j=0;j<array[0].length;j++)
            {
                int x=initial_x+j*x_delta;
                int y=initial_y+i*y_delta;
                int temp=array[i][j];
                if(x_move==x&&y_move==y){
                    currentToolPaint(g,moveSprite64_64,moveSprite64_32,moveSprite,tempNumber);
                }
                if(temp>=0)
                {
                    if(temp<11){
                        designSprite.setFrame(temp);
                        designSprite.setPosition(x, y - 32);
                        designSprite.paint(g, null);
//                        if(temp==5||temp==6){
//                            g.drawText(houseNum[i][j], x + 22, y + 10, paint);
//                        }
                    }
                    else if(temp!=11){
                        roadSprite.setFrame(temp-12);
                        roadSprite.setPosition(x, y);
                        roadSprite.paint(g, null);
                    }
                }
            }
        }



    }



    public void currentToolPaint(Canvas g,ShahSprite moveSprite64_64,ShahSprite moveSprite64_32,ShahSprite moveSprite,int tempNumber) {
        if(tempNumber!=11){
            if(tempNumber<11){
                designSprite.setFrame(tempNumber);
                designSprite.setPosition(moveSprite.getX(),moveSprite.getY()-32);
                moveSprite64_64.setPosition(moveSprite.getX(), moveSprite.getY()-32);
                designSprite.paint(g,null);
                moveSprite64_64.paint(g,null);
            }
            else{
                roadSprite.setPosition(moveSprite.getX(),moveSprite.getY());
                roadSprite.setFrame(tempNumber-12);
                moveSprite64_32.setPosition(moveSprite.getX(),moveSprite.getY());
                roadSprite.paint(g,null);
                moveSprite64_32.paint(g,null);
            }
        }
    }


    public void roadPaint(Canvas g){
        int x_delta=designSprite.getWidth()/2;
        int y_delta=designSprite.getHeight()/4;
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[0].length;j++){
                int x=initial_x + j * x_delta;
                int y=initial_y + i * y_delta;
                if(array[i][j]>11){
                    roadSprite.setFrame(array[i][j]-12);
                    roadSprite.setPosition(x, y);
                    roadSprite.paint(g,null);
                }

            }
        }
    }



    public void designPaint(ShahSprite manMove,Canvas g)
    {
        boolean man_flag_behind=true;
        int x_delta=designSprite.getWidth()/2;
        int y_delta=designSprite.getHeight()/4;

        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < array[0].length; j++)
            {
                int x=initial_x + j * x_delta;
                int y=initial_y + i * y_delta ;
                if(array[i][j]<11&&array[i][j]>=0){
                    if(array[i][j]==5||array[i][j]==6){
                        designSprite.setFrame(array[i][j]);
                        designSprite.setPosition(x, y-32);
                        if(manMove.getY()-y<32&&manMove.getY()-y<-32&&man_flag_behind){
                            manMove.paint(g,null);
                            designSprite.paint(g, null);
//                            g.drawString(houseNum[i][j], x+22, y+10, Graphics.LEFT|Graphics.TOP);
                            man_flag_behind=false;
                        }
                        else{
                            designSprite.paint(g, null);
//                            g.drawString(houseNum[i][j], x+22, y+10, Graphics.LEFT|Graphics.TOP);
                        }
                    }
                    else{
                        designSprite.setFrame(array[i][j]);
                        designSprite.setPosition(x, y-32);
                        if(manMove.getY()-y<32&&manMove.getY()-y<-32&&man_flag_behind){
                            manMove.paint(g,null);
                            designSprite.paint(g,null);
                            man_flag_behind=false;
                        }
                        else
                            designSprite.paint(g,null);
                    }

                }
            }
        }
        if(man_flag_behind){
            manMove.paint(g,null);
        }
    }

}
