package com.servicebind;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

/**
 * Created by renan on 07/01/16.
 */
public class MyService extends Service {


    private final IBinder myBind = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    public void onCreate() {
        //create the service
    }

    public void startProgress(final ProgressBar pb) {

        pb.setMax(100);
        Runnable r = new Runnable() {
            public void run() {
                int cont = 0;
                while (cont <= 100) {
                    pb.setProgress(cont);
                    synchronized (this) {
                        try {
                            wait(500);
                            cont++;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                stopSelf();
            }
        };

        Thread t = new Thread(r);
        t.start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }

    }

}
