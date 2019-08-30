package com.yaozou.jdk.base;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2019/7/1 09:12
 */
public class TestGraphics2D {

    public static void drawTable(Graphics2D g,
                          int x, int y ,
                          int rows,int columns,
                          int rowWidth,int rowHeight){
        // 横线
        for(int i = 0;i<rows+1;i++){
            g.drawLine(x, y+(i*rowHeight), columns*rowWidth+x, y+(i*rowHeight));
        }
        //竖线
        for(int i = 1 ; i < columns;i++){
            g.drawLine(x+(i*rowWidth), y, x+(i*rowWidth), y+rowHeight*rows);
        }
    }
    public static void main(String[] args){
        int imgWidth = 700,imgHeight = 620;
        //空白面板  也可是张图片
        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        try{
            Graphics2D g = null;
            int x = 0,y=0;
            g = image.createGraphics();// 得到图形上下文
            g.setBackground(Color.WHITE);//设置背景色
            g.fillRect(x, y, imgWidth, imgHeight);//填充整个屏幕
            g.setColor(Color.BLACK);//设置画笔颜色

            int rowHeight = 40,rowWidth = 50;
            int rows = 4 , columns = 5;
            g.setFont(new Font("微软雅黑", Font.TYPE1_FONT, 12));
            int tableX = x+10,tableY = y+10;
            drawTable(g,tableX,tableY,rows,columns,rowWidth,rowHeight);

            int y1 = tableY;
            for (int i = 0;i<rows;i++){
                int x1 = tableX;
                y1 += rowHeight;
                for(int j =0;j<columns;j++){
                    g.drawString("a"+i+j,x1+rowWidth*j+10,y1-15);
                }
            }

            /*int dataStart = 15; //数据缩进
            g.drawString("", dataStart, rowHeight - 5);*/
            g.dispose();

            File file = new File("E:\\test.jpg");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ImageIO.write(image, "jpg", fos);
            fos.close();

        }catch (Exception e){

        }

    }
}
