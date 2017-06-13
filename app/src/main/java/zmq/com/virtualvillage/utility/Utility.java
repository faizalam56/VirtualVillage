package zmq.com.virtualvillage.utility;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.HashMap;


public class Utility {

    public static HashMap getMap(String[]keys,int[]values){
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        for(int i=0;i<keys.length;i++){
            map.put(keys[i],values[i]);
        }
        return  map;
    }


    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
//	public static Bitmap createImage(int resId){
//		  Bitmap btmp= Constant.decodeSampledBitmapFromResource(GlobalVariables.getResource, resId);
//		  return btmp;
//	  }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId ) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        int imageHeight = (int)(options.outHeight* GlobalVariables.yScale_factor);//scaled height   by shah
        int imageWidth = (int)(options.outWidth * GlobalVariables.xScale_factor);// scaled width    by shah
        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, imageWidth, imageHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return Utility.getResizedBitmap(BitmapFactory.decodeResource(res, resId, options), imageWidth, imageHeight);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
