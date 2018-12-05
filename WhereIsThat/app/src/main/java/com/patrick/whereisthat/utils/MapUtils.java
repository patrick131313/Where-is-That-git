package com.patrick.whereisthat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ViewDataBinding;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.databinding.ActivityLevelBinding;
import com.patrick.whereisthat.databinding.ActivitySprintBinding;

public class MapUtils {

    public static Icon getMarkerIcon(Context context, SharedPreferences sharedPreferences)
    {
        switch(sharedPreferences.getString("MarkerPref","4"))
        {
            case "1":
                return (IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_green));
            case "2":
                return(IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_magenta));
            case "3":
                return(IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_orange));
            case "4":
                return (IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_red));
            case "5":
                return(IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_blue));
            case "6":
                return(IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_yellow));
            default:
                return null;
        }

    }

    public static String getStyleMap(Context context, SharedPreferences sharedPreferences, ViewDataBinding viewDataBinding)
    {
        switch (sharedPreferences.getString("MapPref","1"))
        {
            case "1":
                return "mapbox://styles/patrick1313/cjo8jpszu0ras2sl5v1y870g9";
            case "2":
                return "mapbox://styles/patrick1313/cjo5rti5u0c182snzl828eeth";
            case "3":
                if(viewDataBinding instanceof ActivityLevelBinding)
                    bindLevelActivity(context,(ActivityLevelBinding) viewDataBinding);
                else
                    bindSprintActivity(context,(ActivitySprintBinding) viewDataBinding);
                return "mapbox://styles/patrick1313/cjo8k1p3u1ehk2spiuq3xhmky";
            case "4":
                if(viewDataBinding instanceof ActivityLevelBinding)
                    bindLevelActivity(context,(ActivityLevelBinding) viewDataBinding);
                else
                    bindSprintActivity(context,(ActivitySprintBinding) viewDataBinding);
                return "mapbox://styles/patrick1313/cjo8k5r2n2xna2snzzjkg3rvi";
            default:
                return null;

        }
    }
    private static void bindLevelActivity(Context context,ActivityLevelBinding levelBinding)
    {
        levelBinding.buttonConfirm.setImageDrawable(context.getDrawable(R.drawable.ic_check_white));
        levelBinding.textViewScore.setTextColor(context.getResources().getColor(R.color.colorWhite));
        levelBinding.textViewTimer.setTextColor(context.getResources().getColor(R.color.colorWhite));
        levelBinding.textViewWhere.setTextColor(context.getResources().getColor(R.color.colorWhite));
    }

    private static void bindSprintActivity(Context context, ActivitySprintBinding sprintBinding)
    {
        sprintBinding.buttonConfirmSprint.setImageDrawable(context.getDrawable(R.drawable.ic_check_white));
        sprintBinding.textViewCountdown.setTextColor(context.getResources().getColor(R.color.colorWhite));
        sprintBinding.twRound.setTextColor(context.getResources().getColor(R.color.colorWhite));
        sprintBinding.textViewSprintHs.setTextColor(context.getResources().getColor(R.color.colorWhite));
    }

    public static void moveCameraToInitial(MapboxMap mapbox)
    {
        mapbox.removeAnnotations();
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(53.009699,  5.693144)) // Sets the new camera position
                .zoom(3) // Sets the zoom
                .build(); // Creates a CameraPosition from the builder
        mapbox.animateCamera(CameraUpdateFactory
                .newCameraPosition(position),1000);
    }
}
