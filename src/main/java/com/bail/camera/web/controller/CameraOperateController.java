package com.bail.camera.web.controller;

import com.bail.camera.web.work.Camera;
import com.bail.camera.web.work.CameraJob;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description：create
 * @author: ext.liukai3
 * @date: 2021/12/24 18:12
 */
@RestController
@RequestMapping("/camera/op")
public class CameraOperateController {

    private volatile Boolean openFlag = false;

    private Map<String, Camera> jobMap = new HashMap<>();
    private String key = "CURRENT_CAMERA_OBJ";
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    @RequestMapping("/open")
    public String open() throws Exception{

        Future<Camera> submit = executorService.submit(new CameraJob(true, 10L));
        jobMap.put(key, submit.get());
        return "相机启动成功";
    }

    @RequestMapping("/close")
    public String close(){
        Camera camera = jobMap.get(key);
        if(Objects.nonNull(camera)){
            camera.setAlive(false);
        }
        return "相机关闭成功";
    }

}
