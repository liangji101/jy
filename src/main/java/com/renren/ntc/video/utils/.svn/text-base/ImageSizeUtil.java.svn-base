package com.renren.ntc.video.utils;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageSizeUtil {

	public static class ImageSize {
		private int width;
		private int height;
		
		public ImageSize() {
			super();
		}
		public ImageSize(int width, int height) {
			super();
			this.width = width;
			this.height = height;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
	}
	
	public static ImageSize getShowImageSize(ImageSize except,ImageSize imageSize){
		/**图片的无变形缩放方法，用于配合wrap超出部分截取而使用
		    *   ow: 图片原始宽度
		    *   oh: 图片元素高度
		    *   cw: 期望显示宽度
		    *   ch: 期望显示高度
		    */

        if(imageSize == null) {
            return except;
        }

        double cw = except.getWidth();
        double ch = except.getHeight();
        double oh = imageSize.getHeight();
        double ow = imageSize.getWidth();
        double rate = 1.0* ch / cw; //期望显示比例
        if (oh / ow > rate) {
            ch = cw * oh / ow;
        } else if (oh / ow < rate) {
            cw = ch * ow / oh;
        }
        ImageSize realSize = new ImageSize((int)cw,(int)ch);
        return realSize;
	}
	
	public static ImageSize getRealSize(String imageUrl){
		HttpURLConnection connection = null;
		DataInputStream in = null;
		try {
			URL url = new URL(imageUrl);
			connection = (HttpURLConnection) url.openConnection();
			in = new DataInputStream(connection.getInputStream());
			BufferedImage image = ImageIO.read(in);
			int width = image.getWidth();
			int height = image.getHeight();
			return new ImageSize(width,height);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(connection != null){
				connection.disconnect();
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
//		ImageSize size1 = new ImageSize(300,200);
//		ImageSize size2 = new ImageSize(400,300);
//		ImageSize size = ImageSizeUtil.getShowImageSize(size1, size2);
//		System.out.println(size.getWidth()+"height:"+size.getHeight());
		ImageSize size = getRealSize("gdgd");
		System.out.println("width:"+size.getWidth());
		System.out.println("height:"+size.getHeight());
	}
}
