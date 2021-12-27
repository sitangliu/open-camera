package com.bail.camera.web.work;

import org.bytedeco.javacv.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

/**
 * @Description：create
 * @author: ext.liukai3
 * @date: 2021/12/24 18:20
 */
public class Camera {

   public Camera(Boolean isAlive,long timestamp){
      this.isAlive = isAlive;
      this.timestamp = timestamp;
   }

   private volatile Boolean isAlive = false;
   private volatile long timestamp = 10;

   public void startRecord() throws Exception {
      VideoInputFrameGrabber grabber = VideoInputFrameGrabber.createDefault(0);
      grabber.start();
      CanvasFrame canvasFrame = new CanvasFrame("摄像头");
      canvasFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      canvasFrame.setAlwaysOnTop(true);

      while (true){
         System.out.println("isAlive:"+isAlive);
         System.out.println("timestamp:"+timestamp);
         if (!isAlive) {
            grabber.stop();
            Thread.currentThread().interrupt();
            canvasFrame.dispose();
            break;
         }
         Frame frame = grabber.grab();
         int seconds = new Date().getSeconds();
         if(seconds%10==0){
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage bufferedImage = converter.convert(frame);
            File file = new File("D:/我的坚果云/images" + System.currentTimeMillis() + ".jpg");
            ImageIO.write(bufferedImage, "jpg", file);
         }

         canvasFrame.showImage(frame);
         Thread.sleep(timestamp);
      }
   }

   public Boolean getAlive() {
      return isAlive;
   }

   public void setAlive(Boolean alive) {
      isAlive = alive;
   }

   public long getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }
}
