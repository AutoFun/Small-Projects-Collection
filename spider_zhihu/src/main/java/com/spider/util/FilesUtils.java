package com.spider.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;
@Log
public class FilesUtils {
	private static final String path = System.getProperty("user.dir")+ File.separator + "images" + File.separator;
	
	public static String imageOut(HttpServletRequest request, 
			MultipartFile image,String fileName) throws Exception {
		try {
			File uploadFilePath = new File(path);
			if (uploadFilePath.exists() == false) {
				uploadFilePath.mkdirs();
			}
			image.transferTo(new File(path + fileName));
			return path + fileName;
		}catch (Exception e) {
			log.info(e.getMessage());
		}
		return path + fileName;
	}

	public static void imageIn(HttpServletResponse response, String image,
			Integer type) throws Exception {
		try {
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition", "inline;");
			BufferedOutputStream bos = null;
			InputStream fis = null;
			BufferedInputStream bis = null;
			try {
				bos = new BufferedOutputStream(response.getOutputStream());
				if(type==1) {
					fis = new FileInputStream(path+image);
				}else if(type==2) {
					fis = new FileInputStream(image);
				}
				bis = new BufferedInputStream(fis);
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
				bos.flush();
			} catch (Exception e) {
				//e.printStackTrace();
				log.info(e.getMessage());
			} finally {
				if (bos != null) {
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (fis != null) {
					fis.close();
				}
			}
		}catch (Exception e) {
			log.info(e.getMessage());
		}
	}
//	/**
//	 * 移动端图片压缩
//	 */
//	public static void weuiImageIn(HttpServletResponse response, String image) throws Exception {
//		try {
//			response.setContentType("image/jpeg");
//			response.setHeader("Content-Disposition", "inline;");
//			BufferedOutputStream bos = null;
//			InputStream fis = null;
//			BufferedInputStream bis = null;
//			try {
//				bos = new BufferedOutputStream(response.getOutputStream());
//				
//				String linpath = path+UUID.randomUUID()+".jpg";
//				File distfile = new File(linpath);
//		        //图片压缩0.5
//		        Thumbnails.of(path+image).scale(1f).outputQuality(0.5f).toFile(linpath);
//				//fis = new FileInputStream(path+image);
//				fis = new FileInputStream(linpath);//使用临时压缩图片
//				
//				bis = new BufferedInputStream(fis);
//				byte[] buff = new byte[2048];
//				int bytesRead;
//				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//					bos.write(buff, 0, bytesRead);
//				}
//				bos.flush();
//				bos.close();bis.close();fis.close();
//				//System.gc();
//				distfile.delete();//删除临时图片
//			} catch (Exception e) {
//				log.info(e.getMessage());
//			} finally {
//				if (bos != null) {
//					bos.close();
//				}
//				if (bis != null) {
//					bis.close();
//				}
//				if (fis != null) {
//					fis.close();
//				}
//			}
//		}catch (Exception e) {
//			log.info(e.getMessage());
//		}
//	}
}
