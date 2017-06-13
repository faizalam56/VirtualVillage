package zmq.com.virtualvillage.gamefloor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import zmq.com.virtualvillage.R;
import zmq.com.virtualvillage.canvas.BaseSurface;
import zmq.com.virtualvillage.interfaces.Recycler;
import zmq.com.virtualvillage.sprite.ShahSprite;
import zmq.com.virtualvillage.utility.GlobalVariables;
import zmq.com.virtualvillage.utility.Utility;


/**
 * Created by zmq162 on 28/12/16.
 */
public class NewFloor extends BaseSurface implements Runnable,Recycler {
    int width,height;
    int rectX,rectY;
    ShahSprite roadDesign, designSprites,moveRectangleTool,moveRectangleTool1,move,man_sprite;
    ShahSprite tile, up,left,right,down,ok,setting,play,leftTop,rightTop,leftBottom,rightBottom,paint;
    int canvasScene = 0;
    CanvasBackgroundPaint canvasBackgroundPaint;
    MapPaint mapPaint;
    int tile_number_array[][]=new int[UtilityType.row][UtilityType.column];
    int setHouseDirection[][]=new int[UtilityType.row][UtilityType.column];
    String houseNumber[][]=new String[UtilityType.row][UtilityType.column];
    int x_position,y_position;
    RectF touchRect;
    int tempNumber,lastDirection;
    int x_delta=32;
    int y_delta=16;
    int colCount,rowCount;
    int manDown[] = {0, 1, 2, 3};
    int manLeft[] = {4, 5, 6, 7};
    int manRight[] = {8, 9, 10, 11};
    int manUp[] = {12, 13, 14, 15};
    boolean windowManPosition;

    public NewFloor(Context context) {
        super(context);

    }

    @Override
    protected void drawSomething(Canvas g) {
        switch (canvasScene){
            case 0:
                loadImages();
                break;
            case 1:
                scene1(g);
                break;
            case 2:
                scene2(g);
                break;
            case 3:
                scene3(g);
            default:
                break;
        }
    }

    boolean initialiseThreadLoad=true;
    Thread localThread=null;
    public void loadImages(){
        if(initialiseThreadLoad){
            localThread=new Thread(this);
            localThread.start();
            System.out.println("Start Joinig.......");
            initialiseThreadLoad=false;
        }
    }

    @Override
    public void recycle() {

        tile.recycle();
        up.recycle();
        down.recycle();
        right.recycle();
        left.recycle();
        ok.recycle();
        setting.recycle();
        play.recycle();
        leftTop.recycle();
        leftBottom.recycle();
        rightTop.recycle();
        rightBottom.recycle();
        man_sprite.recycle();
        paint.recycle();
    }

    @Override
    public void run() {
        width = GlobalVariables.width;
        height = GlobalVariables.height;
        rectX = GlobalVariables.width/10;
        rectY = GlobalVariables.height/6;
        initializeValue(tile_number_array);
        canvasBackgroundPaint = new CanvasBackgroundPaint();

        up = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.arrowup));
        down = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.arrowdown));
        right = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.arrowright));
        left = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.arrowleft));
        ok = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.ok));
        setting = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.setting));
        play = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.play));
        paint = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource,R.drawable.paint));

        up.setPosition(rectX*5,rectY*4);
        left.setPosition(rectX*4,rectY*4+rectY/2);
        right.setPosition(rectX*6,rectY*4+rectY/2);
        down.setPosition(rectX*5,rectY*5);
        ok.setPosition(rectX*9,rectY*5);
        setting.setPosition(rectX * 9, rectY * 5);
        play.setPosition(0, rectY * 5);
        paint.setPosition(rectX * 8, rectY * 5);


        leftTop = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.lefttop));
        leftBottom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.leftbottom));
        rightTop = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.righttop));
        rightBottom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.rightbottom));

        leftTop.setPosition(rectX * 4, rectY * 4 + rectY/2);
        leftBottom.setPosition(rectX * 4, rectY * 5 + rectY/2);
        rightTop.setPosition(rectX*6,rectY*4+rectY/2);
        rightBottom.setPosition(rectX*6,rectY*5+rectY/2);

        tile = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.background));

        Bitmap roadSprite = Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource,R.drawable.roadtype);
        roadDesign = new ShahSprite(roadSprite,roadSprite.getWidth()/7,roadSprite.getHeight());

        Bitmap toolSprite = Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource,R.drawable.designtool);
        designSprites = new ShahSprite(toolSprite,toolSprite.getWidth()/11,toolSprite.getHeight());

        mapPaint = new MapPaint(designSprites,roadDesign,tile_number_array,houseNumber,x_position,y_position);

        moveRectangleTool = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.tool));
        moveRectangleTool1 = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.map));
        move = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource,R.drawable.map));

        Bitmap manSprite = Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource,R.drawable.man);
        man_sprite = new ShahSprite(manSprite,manSprite.getWidth()/16,manSprite.getHeight());
        man_sprite.setPosition(0, 0);
        man_sprite.setFrameSequence(manUp);

        canvasScene = 1;
    }

    public void initializeValue(int tileArray[][]){
        for(int i=0;i<tileArray.length;i++)
        {
            for(int j=0;j<tileArray[0].length;j++)
            {
                tileArray[i][j]=-19;
                houseNumber[i][j]="-1";
            }
        }
    }

    private void scene1(Canvas g){
        canvasBackgroundPaint.backGroundPiant(g, tile);
        mapPaint.toolTilePaint(g, 20);

        moveRectangleTool.paint(g,null);

        up.paint(g,null);
        down.paint(g, null);
        right.paint(g, null);
        left.paint(g, null);
        ok.paint(g, null);

    }

    private void scene2(Canvas g){
        canvasBackgroundPaint.backGroundPiant(g, tile);
        mapPaint.designMapPaint(g, moveRectangleTool, moveRectangleTool1, move, tempNumber);

        leftTop.paint(g, null);
        leftBottom.paint(g, null);
        rightTop.paint(g, null);
        rightBottom.paint(g, null);
        setting.paint(g, null);
        play.paint(g,null);
        paint.paint(g,null);
    }

    private void scene3(Canvas g){
        canvasBackgroundPaint.backGroundPiant(g, tile);
        mapPaint.roadPaint(g);
        mapPaint.designPaint(man_sprite, g);
        leftTop.paint(g, null);
        leftBottom.paint(g, null);
        rightTop.paint(g, null);
        rightBottom.paint(g, null);
        setting.paint(g, null);

        int x_man_position = man_sprite.getX();
        int y_man_position = man_sprite.getY();
        setManPosition(touchRect, man_sprite, x_man_position, y_man_position);
        if(checkCollision(tile_number_array)){
            System.out.println("collision done..");
            man_sprite.setPosition(x_man_position,y_man_position);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        touchRect = new RectF(event.getX(),event.getY(),event.getX()+1,event.getY()+1);

        int row=moveRectangleTool.getX()/moveRectangleTool.getHeight();
        int col=moveRectangleTool.getY()/moveRectangleTool.getWidth();

//        int x_man_position = man_sprite.getX();
//        int y_man_position = man_sprite.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                  windowManPosition = true;

            switch (canvasScene) {
                case 1:
                    spriteMoveTool(touchRect, moveRectangleTool, row, col);
                    if (ok.getDstRect().intersect(touchRect)) {
                        tempNumber = row + 4 * col;
                        canvasScene = 2;
                    }
                    break;
                case 2:
                    setWindowPosition(touchRect, move.getX(), move.getY(), move);
                    if (setting.getDstRect().intersect(touchRect)) {
                        canvasScene = 1;
                    } else if (paint.getDstRect().intersect(touchRect)) {
                        if (tempNumber < 12 && !checkTile()) {
                            insertTileDesign(tempNumber);
                        } else if (tempNumber >= 12) {
                            insertRoadTIle(tempNumber);
                        }
                    } else if (play.getDstRect().intersect(touchRect)) {
                        canvasScene = 3;
                    }
                    break;
                case 3:
//                    setManPosition(touchRect, man_sprite, x_man_position, y_man_position);
//                    if (checkCollision(tile_number_array)) {
//                        man_sprite.setPosition(x_man_position, y_man_position);
//                    }
                    if (setting.getDstRect().intersect(touchRect)) {
                        canvasScene = 2;
                    }
                    break;
            }
                break;
            case MotionEvent.ACTION_UP:
                windowManPosition = false;
                break;
//            return super.onTouchEvent(event);
        }
        return true;
    }

    private void spriteMoveTool(RectF touchRect,ShahSprite moveRectangleTool,int row,int col){

        if(up.getDstRect().intersect(touchRect)){

            if(col>0){
                col--;
            }
        }
        else if(down.getDstRect().intersect(touchRect)){

            if(col<4){
                col++;
            }
        }
        else if(left.getDstRect().intersect(touchRect)){

            if(row>0){
                row--;
            }
        }
        else if(right.getDstRect().intersect(touchRect)){

            if(row<3){
                row++;
            }
        }
        moveRectangleTool.setPosition(moveRectangleTool.getWidth()*row, moveRectangleTool.getHeight()*col);
    }

    private void  setWindowPosition(RectF touchRect,int x_sprite ,int y_sprite,ShahSprite sprite){

        if(leftTop.getDstRect().intersect(touchRect)){
            if(y_sprite>0&&x_sprite>0)
            {
                y_sprite-=y_delta;
                rowCount--;
                x_sprite-=x_delta;
                colCount--;
            }
//            else if(y_position<0&&x_position<0)
//            {
//                y_position+=y_delta;
//                rowCount--;
//                x_position+=x_delta;
//                colCount--;
//            }
        }
        else if(rightBottom.getDstRect().intersect(touchRect)){
            if(y_sprite<height-sprite.getHeight()-32&&x_sprite<width-sprite.getWidth())
            {
                y_sprite+=y_delta;
                x_sprite+=x_delta;
                colCount++;
                rowCount++;
            }
//            else if(x_position>-(UtilityType.column*x_delta-width)&&y_position>-(UtilityType.row*y_delta-height)-sprite.getHeight()-16)
//            {
//                y_position-=y_delta;
//                rowCount++;
//                x_position-=x_delta;
//                colCount++;
//            }
        }
        else if(leftBottom.getDstRect().intersect(touchRect)){
            if(x_sprite>0&&y_sprite<height-sprite.getHeight()-32)
            {
                x_sprite-=x_delta;
                colCount--;
                y_sprite+=y_delta;
                rowCount++;
            }
//            else if(x_position<0&&y_position>-(UtilityType.row*y_delta-height)-sprite.getHeight()-16)
//            {
//                x_position+=x_delta;
//                colCount--;
//                y_position-=y_delta;
//                rowCount++;
//            }
        }
        else if(rightTop.getDstRect().intersect(touchRect)){
            if(x_sprite<width-sprite.getWidth()&&y_sprite>0)
            {
                x_sprite+=x_delta;
                colCount++;
                y_sprite-=y_delta;
                rowCount--;
            }
//            else if(x_position>-(UtilityType.column*x_delta-width)&&y_position<0){
//                x_position-=x_delta;
//                colCount++;
//                y_position+=y_delta;
//                rowCount--;
//            }
        }
        sprite.setPosition(x_sprite, y_sprite);
    }

    public void insertTileDesign(int tilenumber)
    {
        tile_number_array[rowCount][colCount]=tilenumber;
    }

    public void insertRoadTIle(int tilenumber){
        tile_number_array[rowCount][colCount]=tilenumber;
    }

    public boolean checkTile(){
        boolean check=false;
        if(rowCount>0&&colCount>0&&rowCount<tile_number_array.length-1&&colCount<tile_number_array[0].length-1)
        {
            if(setHouseDirection[rowCount-1][colCount-1]==-1){
                check =true;
            }
            if(setHouseDirection[rowCount-1][colCount+1]==-3){
                check =true;
            }
            if(setHouseDirection[rowCount+1][colCount-1]==-4){
                check =true;
            }
            if(setHouseDirection[rowCount+1][colCount+1]==-2){
                check =true;
            }
        }
        return check;
    }

    private void setManPosition(RectF touchRect,ShahSprite man_sprite,int x_man_position,int y_man_position){

        if(windowManPosition) {

            if (leftTop.getDstRect().intersect(touchRect)) {

                if (lastDirection != -1) {
                    lastDirection = -1;
                    man_sprite.setFrameSequence(manUp);
                } else {
                    man_sprite.nextFrame();
                }

                if (x_man_position > 0 && y_man_position > 0) {
                    x_man_position -= 8;
                    y_man_position -= 4;
                }
//            else if (y_position < 0&&x_position<0&&!checkCollision(tile_number_array)) {
//                y_position += 4;
//                x_position+=8;
//            }
            } else if (rightBottom.getDstRect().intersect(touchRect)) {

                if (lastDirection != -2) {
                    lastDirection = -2;
                    man_sprite.setFrameSequence(manDown);
                } else {
                    man_sprite.nextFrame();
                }
                if (y_man_position < getHeight() - 64 - 32 && x_man_position < getWidth() - 32) {
                    x_man_position += 8;
                    y_man_position += 4;
                }
//            else if (!checkCollision(tile_number_array)&&y_position > -(tile_number_array.length * 16 - getHeight()) - 48&&x_position > -(tile_number_array[0].length * 32 - getWidth())) {
//                y_position -= 4;
//                x_position-=8;
//            }
            } else if (leftBottom.getDstRect().intersect(touchRect)) {

                if (lastDirection != -3) {
                    lastDirection = -3;
                    man_sprite.setFrameSequence(manLeft);
                } else {
                    man_sprite.nextFrame();
                }
                if (x_man_position > 0 && y_man_position < getHeight() - 96) {
                    x_man_position -= 8;
                    y_man_position += 4;
                }
//            else if (!checkCollision(tile_number_array)&&x_position < 0&&y_position > -(tile_number_array.length * 16 - getHeight()) - 48) {
//                x_position += 8;
//                y_position-=4;
//            }

            } else if (rightTop.getDstRect().intersect(touchRect)) {

                if (lastDirection != -4) {
                    lastDirection = -4;
                    man_sprite.setFrameSequence(manRight);
                } else {
                    man_sprite.nextFrame();
                }
                if (x_man_position < getWidth() - 32 && y_man_position > 0) {
                    x_man_position += 8;
                    y_man_position -= 4;
                }
//            else if (!checkCollision(tile_number_array)&&y_position<0&&x_position > -(tile_number_array[0].length * 32  - getWidth())) {
//                x_position -=8;
//                y_position+=4;
//            }
            }
        }

        man_sprite.setPosition(x_man_position, y_man_position);

    }


    public boolean checkCollision(int array[][])
    {

        boolean collision_flag=false;
        for(int i=0;i<array.length;i++)
        {
            for(int j=0;j<array[0].length;j++)
            {
                int x=x_position + j * 32;
                int y=y_position + i * 16;

                if(!collision_flag){

                    int temp = array[i][j];
                    if(temp>=0 && temp<11){
                        System.out.println("collision frame check.");
                        designSprites.setFrame(temp);
                        designSprites.setPosition(x, y - 32);
                        designSprites.defineCollisionRectangle(8, 40, 56, 24);
                        man_sprite.defineCollisionRectangle(10, 45, 28, 16);
                        if (man_sprite.collidesWith(designSprites, false)) {
                            collision_flag = true;
                            System.out.println("collision frame  "+temp +"  true..");
                        }
                    }
                }
            }
        }
        return collision_flag;
    }

}
