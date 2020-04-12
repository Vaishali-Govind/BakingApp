package eu.anifantakis.bakingapp.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.test.espresso.IdlingResource;
import android.webkit.MimeTypeMap;

import eu.anifantakis.bakingapp.testing.SimpleIdlingResource;



public final class AppUtils {

    // no instances of App Utils are allowed
    private AppUtils() {}

    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    public static final String JSON_LOC = "/topher/2017/May/59121517_baking/baking.json";

    // the recipe being added as "extra" to the recipe info activity
    public static final String EXTRAS_RECIPE = "recipe";
    // the step extras passed from the recipe info activity to the details activity
    public static final String EXTRAS_STEP = "step";
    public static final String EXTRAS_RECIPE_NAME = "recipe_name";

    // preferences
    public static final String PREFERENCES_ID = "ID";
    public static final String PREFERENCES_WIDGET_TITLE = "WIDGET_TITLE";
    public static final String PREFERENCES_WIDGET_CONTENT = "WIDGET_CONTENT";

    public static final String MIME_VIDEO = "video/";
    public static final String MIME_IMAGE = "image/";


    public static String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }


    @SuppressLint("DefaultLocale")
    public static String fmt(double d) {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }


    public static String capitalizeFirstLetter(String input){
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private static SimpleIdlingResource sIdlingResource;
    public static IdlingResource getIdlingResource() {
        if (sIdlingResource == null) {
            sIdlingResource = new SimpleIdlingResource();
        }
        return sIdlingResource;
    }

    public static void setIdleResourceTo(boolean isIdleNow){
        if (sIdlingResource == null) {
            sIdlingResource = new SimpleIdlingResource();
        }
        sIdlingResource.setIdleState(isIdleNow);
    }
}