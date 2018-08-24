package com.nineteenwang.electricalimi.utill;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-25 오전 4:09
 * @Homepage : https://github.com/gusdnd852
 */
public class PreferenceHelper {

    private final String PREF_NAME = "PrefName";
    private static PreferenceHelper mInstance = null;
    private SharedPreferences mSharedPreference;;

    private PreferenceHelper(Context context) {
        this.mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public static synchronized PreferenceHelper getInstance(Context context) {
        if (mInstance == null) {
        }
        try {
            if (mInstance == null)
                mInstance = new PreferenceHelper(context);
            return mInstance;
        } finally {
        }
    } // Singleton


    public void setPreferenceRemove(String paramString) {
        SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
        localEditor.remove(paramString);
        localEditor.commit();
    }

    public void setItem(String paramString, List<String> paramList) {
        SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
        Gson gson = new Gson();
        String json = gson.toJson(paramList);
        localEditor.putString(paramString, json);
        localEditor.commit();
    }

    public List<String> getItem(String paramString) {
        Type type = new TypeToken<List<String>>() {
        }.getType();
        Gson gson = new Gson();
        List<String> target = gson.fromJson(this.mSharedPreference.getString(paramString, ""), type);
        return target;
    }

    public boolean getBoolean(String paramString, boolean paramBoolean) {
        return this.mSharedPreference.getBoolean(paramString, paramBoolean);
    }

    public double getDouble(String paramString) {

        return Double.longBitsToDouble(this.mSharedPreference.getLong(paramString, Double.doubleToRawLongBits(-1.0D)));
    }

    public float getFloat(String paramString, float paramFloat) {
        return this.mSharedPreference.getFloat(paramString, paramFloat);
    }

    public int getInt(String paramString) {
        return this.mSharedPreference.getInt(paramString, -1);
    }

    public long getLong(String paramString, long paramLong) {
        try {
            paramLong = this.mSharedPreference.getLong(paramString, paramLong);
            return paramLong;
        } catch (ClassCastException e) {
            return 0L;
        }

    }

    public String getString(String paramString1, String paramString2) {
        return this.mSharedPreference.getString(paramString1, paramString2);
    }

    public Set<String> getStringSet(String paramString) {
        return this.mSharedPreference.getStringSet(paramString, new HashSet());
    }

    public boolean setBoolean(String paramString, boolean paramBoolean) {
        SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
        localEditor.putBoolean(paramString, paramBoolean);
        return localEditor.commit();
    }

    public boolean setDouble(String paramString, double paramDouble) {
        SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
        localEditor.putLong(paramString, Double.doubleToRawLongBits(paramDouble));
        return localEditor.commit();
    }

    public boolean setFloat(String paramString, float paramFloat) {
        SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
        localEditor.putFloat(paramString, paramFloat);
        return localEditor.commit();
    }

    public boolean setInt(String paramString, int paramInt) {
        SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
        localEditor.putInt(paramString, paramInt);
        return localEditor.commit();
    }

    public boolean setLong(String paramString, long paramLong) {
        SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
        localEditor.putLong(paramString, paramLong);
        return localEditor.commit();
    }

    public void setPreference(SharedPreferences paramSharedPreferences) {
        this.mSharedPreference = paramSharedPreferences;
    }

    public boolean setString(String paramString1, String paramString2) {
        SharedPreferences.Editor localEditor = mSharedPreference.edit();
        localEditor.putString(paramString1, paramString2);
        return localEditor.commit();
    }

    public boolean setStringSet(String paramString, Set<String> paramSet) {
        SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
        localEditor.putStringSet(paramString, paramSet);
        return localEditor.commit();
    }


    public boolean setArray(String[] array, String arrayName) {
        SharedPreferences.Editor localEditor = mSharedPreference.edit();
        localEditor.putString(arrayName + "_size", String.valueOf(array.length));
        for (int i = 0; i < array.length; i++)
            localEditor.putString(arrayName + "_" + i, array[i]);
        return localEditor.commit();
    }

    public String[] getArray(String arrayName) {
        int size = this.mSharedPreference.getInt(arrayName + "_size", -1);
        String array[] = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = this.mSharedPreference.getString(arrayName + "_" + i, array[i]);
        return array;
    }
}