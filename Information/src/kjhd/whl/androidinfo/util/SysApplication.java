package kjhd.whl.androidinfo.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class SysApplication extends Application { 
    private List<Activity> mList = new LinkedList<Activity>(); 
    private static SysApplication instance; 
 
    private SysApplication() {   
    } 
 
    public synchronized static SysApplication getInstance() { 
        if (null == instance) { 
            instance = new SysApplication(); 
        } 
        return instance; 
    } 
 
    // add Activity  
    public void addActivity(Activity activity) { 
        mList.add(activity); 
    } 
 
    public void exit() { 
        try { 
            for (Activity activity : mList) { 
                if (activity != null) 
                    activity.finish(); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            System.exit(0); 
        } 
    } 
 
    @Override 
    public void onLowMemory() { 
        super.onLowMemory();     
        System.gc(); 
    } 
 
   /* ��ÿ��Activity��onCreate������������ƴ��룺


    [java] public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);      
        SysApplication.getInstance().addActivity(this); 
    } 
             ����Ҫ�˳������ʱ�򣬵��ã�


    [java] SysApplication.getInstance().exit(); 


    */


} 
