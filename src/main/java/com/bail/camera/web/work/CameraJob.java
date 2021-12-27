package com.bail.camera.web.work;

import com.bail.camera.web.CameraConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @Description：create
 * @author: ext.liukai3
 * @date: 2021/12/24 18:27
 */
public class CameraJob implements Callable {

    private volatile Boolean isAlive = false;
    private volatile long timestamp = 50;
    private Map<String, Camera> jobMap = new HashMap<>();

    public CameraJob(boolean isAlive,Long timestamp,Map<String, Camera> jobMap){
        this.isAlive = isAlive;
        this.timestamp = timestamp==null?10L:timestamp;
        this.jobMap = jobMap;
    }



    @Override
    public Object call() throws Exception {
        try {
            Camera camera = new Camera(isAlive,timestamp);
            jobMap.put(CameraConstants.KEY,camera);
            camera.startRecord();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
