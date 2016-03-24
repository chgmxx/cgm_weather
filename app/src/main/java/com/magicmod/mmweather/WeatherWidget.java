/*************************************************************************

Copyright 2014 MagicMod Project

This file is part of MagicMod Weather.

MagicMod Weather is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

MagicMod Weather is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with MagicMod Weather. If not, see <http://www.gnu.org/licenses/>.

*************************************************************************/

package com.magicmod.mmweather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.magicmod.mmweather.utils.Constants;

public class WeatherWidget extends AppWidgetProvider {
    private static final String TAG = "WeatherWidget";
    private static final boolean DBG = Constants.DEBUG;
    
    public static final String ACTION_WIDGET_ICON_HOTAREA = "WidgetWeatherIcon";
    //public static final String ACTION_WIDGET_TIME_HOTAREA = "WidgetTime";
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {// 每添加一个小插件调用一次，跟onDeleted对应
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        
        if (DBG) Log.i(TAG, "onUpdate");

        Intent intent = new Intent(context, WeatherUpdateService.class);
        context.startService(intent);
    }

    @Override
    public void onEnabled(Context context) {// 第一个小插件添加时调用，跟onDisabled对应
        if (DBG) Log.i(TAG, "onEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {// 最后一个小插件删除时会调用
        super.onDisabled(context);
        if (DBG) Log.i(TAG, "onDisabled");
        Intent intent = new Intent(context, WeatherUpdateService.class);
        context.stopService(intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {// 小插件每删除一个调用一次
        super.onDeleted(context, appWidgetIds);
        if (DBG) Log.i(TAG, "onDeleted");
    }

    @Override
    public void onReceive(Context context, Intent intent) {// 任何添加删除操作都会调用
        super.onReceive(context, intent);
        
        String action = intent.getAction();
        
        if (DBG) Log.d(TAG, "onReceive action = " + action);
        
        if (action.equals("android.intent.action.USER_PRESENT")) {// 用户唤醒设备时启动服务
            context.startService(new Intent(context, WeatherUpdateService.class));
        } else if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, WeatherUpdateService.class));
        } else if (action.equals(ACTION_WIDGET_ICON_HOTAREA)) {
            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);            
        } /*else if (action.equals(ACTION_WIDGET_TIME_HOTAREA)) {
            Intent i = new Intent("android.intent.action.SET_ALARM");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }*/
        
        /*else if (action.equals(TEXTINFO_LEFT_HOTAREA_ACTION)){
            L.i("widget get weather action.........");
            Intent updateIntent = new Intent(context, WeatherUpdateService.class);
            updateIntent.setAction(TEXTINFO_LEFT_HOTAREA_ACTION);
            context.startService(updateIntent);
        }*/
        // else if (action.equals(WEATHERICON_HOTAREA_ACTION)) {
        // Intent i = new Intent(context, MainActivity.class);
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // context.startActivity(i);
        // }
    }
}
