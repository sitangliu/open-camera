package com.bail.camera.web.work;

import java.util.concurrent.Callable;

/**
 * @Description：create
 * @author: ext.liukai3
 * @date: 2021/12/24 18:27
 */
public class CameraJob implements Callable {

    private volatile Boolean isAlive = false;
    private volatile long timestamp = 50;

    public CameraJob(boolean isAlive,Long timestamp){
        this.isAlive = isAlive;
        this.timestamp = timestamp==null?10L:timestamp;
    }



    @Override
    public Object call() throws Exception {
        try {
            Camera camera = new Camera(isAlive,timestamp);
            camera.startRecord();
            return camera;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
