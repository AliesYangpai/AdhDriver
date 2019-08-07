package org.alie.adhdriver;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

import org.litepal.LitePalApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2017/4/8 0008.
 * 类描述
 * 版本
 */
public class AdhApplication extends LitePalApplication implements Application.ActivityLifecycleCallbacks {


    public static final String TAG = AdhApplication.class.getSimpleName();

    private static AdhApplication mInstance;

    public static AdhApplication getInstance() {
        if (mInstance == null) {
            synchronized (AdhApplication.class) {
                if (mInstance == null) {
                    mInstance = new AdhApplication();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        mInstance = this;

        initNoHttp();


        registerActivityLifecycleCallbacks(mInstance);

        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);

    }


    /**
     * 初始化网路请求框架
     */
    private void initNoHttp() {

        Logger.setDebug(true); // 开启NoHttp调试模式。
        Logger.setTag("NoHttpMyTest"); // 设置NoHttp打印Log的TAG。
        /**
         * 1.超时配置
         * 2.配置缓存 （实际就是数据的本地存储）
         * 3.配置Cookie
         * 4.配置网络层 （这大概是http网络层的异常处理）
         *
         */
        NoHttp.Config config = new NoHttp.Config();
        config.setConnectTimeout(30 * 1000); // 全局连接超时时间，单位毫秒。
        config.setReadTimeout(30 * 1000); // 全局服务器响应超时时间，单位毫秒。
        config.setNetworkExecutor(new OkHttpNetworkExecutor()); //2.配置网络层

        NoHttp.initialize(mInstance, config);


    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // 每个Activity创建都会调用这个方法
        Log.i("AdhApplicationActivty", activity.getLocalClassName() + " onActivityCreated...");
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // 每个Activity销毁都会调用这个方法
        Log.i("AdhApplicationActivty", activity.getLocalClassName() + " onActivityDestroyed...");
    }


    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable throwable) {
            try {
                Log.e(TAG, Log.getStackTraceString(throwable));
                throwable.printStackTrace();
                // 指定输出日志的路径
                File errerLog = new File(getSdcardPackagePath() + "/adhErrorLog.log");
                FileWriter fw = new FileWriter(errerLog, true);
                fw.write("\n\n" + SimpleDateFormat.getInstance().format(new Date()) + "\n");
                PrintWriter pw = new PrintWriter(fw);
                throwable.printStackTrace(pw);
                fw.close();
                pw.close();

                // 捕获未捕获的异常,杀掉进程闪退
                catheUnhanldeCatch();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 杀死当前进程
     */
    private void catheUnhanldeCatch() {
        // 得到当前应用的进程ID
        int pid = android.os.Process.myPid();
        // 杀死进程 闪退
        android.os.Process.killProcess(pid);
    }


    private String getSdcardPackagePath() {
        // 判断sdcard是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 获取根目录
            return Environment.getExternalStorageDirectory().getPath();
        } else {
            return getFilesDir().getPath();
        }
    }


}
