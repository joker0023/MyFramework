package com.joker.myfw.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.joker.myfw.data.ImageFormat;
import com.joker.myfw.exception.UnknowFormatException;

/**
 * 
 * @author Joker
 *
 */
public class ImageUtil {

	public static final String CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	public static final int padding = 10;
	
	/**
	 * 根据图片文件读取图片的格式
	 * @param imgFile 图片文件
	 * @return 图片的格式
	 */
	public static ImageFormat getFormat(File imgFile) {
		ImageFormat format = null;
		ImageInputStream in = null;
		try {
			in = ImageIO.createImageInputStream(imgFile);
			return getFormat(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeInputStream(in);
			in = null;
		}
		return format;
	}
	
	/**
	 * 根据图片输入流读取图片的格式
	 * @param in 图片输入流
	 * @return 图片的格式
	 */
	public static ImageFormat getFormat(ImageInputStream in) {
		ImageFormat format = null;
		Iterator<ImageReader> itr = ImageIO.getImageReaders(in);
		if (itr.hasNext()) {
			format = ImageFormat.valueOfImageReader(itr.next());
		} else {
			throw new UnknowFormatException("未知的图片格式");
		}
		return format;
	}
	
	public static void closeInputStream(ImageInputStream in) {
		try {
			if (null != in) {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成图片验证码
	 * 
	 * @param authCode
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> createImgAuthCode() throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		// 在内存中创建图象
		int width = 140, height = 32;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		char[] ch = CODE.toCharArray();
		String authCode = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(ch[random.nextInt(ch.length)]);
			authCode += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 30 * i + 15, 26);
		}

		// 图象生效
		g.dispose();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
		ImageIO.write(image, "JPEG", imageOut);
		imageOut.close();
		ByteArrayInputStream input = new ByteArrayInputStream(output
				.toByteArray());

		result.put("authCode", authCode);
		result.put("authImage", input);

		return result;
	}
	
	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 生成图片
	 * 一张原图,一段说明
	 * @param file	原图
	 * @param text	文字
	 * @param outFile	生成的新图
	 * @param fontSize	文字大小
	 * @throws Exception
	 */
	public static void generateImg(String file, String text, String outFile, int fontSize) throws Exception {
		BufferedImage oriImg = ImageIO.read(new File(file));
		
		int width = oriImg.getWidth();
		int height1 = oriImg.getHeight();
		
		int height2 = getTextHeight(text, width, fontSize);
		int height = height1 + height2;
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		// 画背景
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, width, height);
		
		// 画图
		g.drawImage(oriImg, 0, 0, width, height1, null);
		
		// 设置字体
		g.setFont(new Font("宋体", Font.ITALIC, fontSize));
		g.setColor(new Color(204, 0, 0));	// 红色
		
		// 画文字
		drawString(g, text, width, height1 + padding + fontSize, fontSize);
		
		g.dispose();
		
		FileOutputStream output = new FileOutputStream(outFile);
		ImageIO.write(image, "JPG", output);
		output.close();
	}
	
	/**
	 * 合成图片
	 * @param list
	 * @param width
	 * @param backgroundColor
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayOutputStream generateImg(List<Material> list, int width, Color backgroundColor) throws Exception {
		// 生成的图片高度
		int height = getAllImgHeigth(list, width);
		
		if(height == 0){
			return null;
		}
		
		// new 一张空白图
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		// 画背景
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		
		//合成
		synthesis(g, list, width);
		
		//输出
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(image, "JPEG", output);
		
		output.close();
		return output;
	}
	
	/**
	 * 计算合成后的图片的 高度
	 * @param list
	 * @param width
	 * @return
	 * @throws IOException
	 */
	public static int getAllImgHeigth(List<Material> list, int width) throws IOException{
		int height = 0;
		// 原图
		BufferedImage oriImg = null;
		// 图片高度
		int imgHeight = 0;
		// 文字高度
		int textHeight = 0;
				
		// 计算总高度
		for(Material material : list){
			//计算图片高度,缩放后的高度
			if(null != material.getImageFile()){
				oriImg = ImageIO.read(material.getImageFile());
				imgHeight = oriImg.getHeight() * width / oriImg.getWidth();
			}else{
				oriImg = null;
				imgHeight = 0;
			}
			material.setImage(oriImg);
			material.setImgWidth(width);
			material.setImgHeight(imgHeight);
			height += imgHeight;
			
			// 计算文字高度
			if(null != material.getDescription()){
				textHeight = getTextHeight(material.getDescription(), width, material.getFontSize());
			}else{
				textHeight = 0;
			}
			material.setTextHeight(textHeight);
			height += textHeight;
		}
		
		return height;
	}
	
	/**
	 * 把list的图片和文字画在空白图上
	 * @param g
	 * @param list
	 * @param width
	 */
	public static void synthesis(Graphics g, List<Material> list, int width){
		int drawStartHeight = 0;
		for(Material material : list){
			g.setColor(material.getBackgroundColor());
			g.fillRect(0, drawStartHeight, width, material.getImgHeight() + material.getTextHeight());
			if(null != material.getImage()){
				// 画图
				g.drawImage(material.getImage(), 0, drawStartHeight, width, material.getImgHeight(), null);
				drawStartHeight += material.getImgHeight();
			}
			
			if(null != material.getDescription() && material.getTextHeight() > 0){
				// 设置字体
				g.setFont(new Font(material.getFontFamily(), material.getFontStyle(), material.getFontSize()));
				g.setColor(material.getFontColor());
				
				// 画文字
				drawString(g, material.getDescription(), width, drawStartHeight + padding + material.getFontSize(), material.getFontSize());
				drawStartHeight += material.getTextHeight();
			}
		}
				
		g.dispose();
	}
	
	/**
	 * 画文字到图片上
	 * @param g
	 * @param text
	 * @param width	图片宽度
	 * @param startHeight	开始画文字的y坐标
	 * @param fontSize	文字大小
	 * @return	画了多少行
	 */
	public static int drawString(Graphics g, String text, int width, int startHeight, int fontSize){
		String s = null;	//要画的单个文字
		int textLength = text.length();
		int charLength = 0;	//单个文字所占的长度
		int startWidth = padding;
		
		for(int i = 0; i < textLength; i++){
			s = String.valueOf(text.charAt(i));
			
			charLength = s.getBytes().length > 1 ? fontSize : fontSize/2;
			
			//判断当前行是否还有足够的位置画文字
			if(startWidth + charLength > width - padding){
				//换行
				g.drawString("\n", startWidth, startHeight);
				startWidth = padding;
				startHeight += fontSize;
			}
			g.drawString(s, startWidth, startHeight);
			startWidth += charLength;
		}
		
		return 0;
	}
	
	/**
	 * 获取字符串画在图片上所占的高度
	 * @param text
	 * @param width	图片宽度
	 * @param fontSize	文字大小
	 * @return
	 */
	public static int getTextHeight(String text, int width, int fontSize){
		//因为一个font-size占两个长度单位
		int fontNum = (width - padding*2) / fontSize;
		fontNum *= 2;
		
		if(fontNum < 1){
			return 0;
		}
		
		int height = 0;	
		int lineNum = 0;
		int index = fontNum;
		int textLength = text.length();
		for (int i = 0; i < textLength; i++) {
			if(index == fontNum){
				index = 0;
				lineNum++;
			}else if(index > fontNum){
				index = 2;
				lineNum++;
			}
			
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				index += 2;
			}else{
				index += 1;
			}
		}
		
		if(index > fontNum){
			lineNum++;
		}
		
		if(lineNum == 0){
			return 0;
		}
		
		height = lineNum * fontSize;
		height += padding * 2;
		
		return height;
	}
	
	/**
	 * 获取字符串的长度,中文占2个长度单位,非中文占1个长度单位
	 * 以font-size的一半为1个长度单位,方便计算,不然要用到0.5之类的,不爽
	 * @param text
	 * @return
	 */
	public static int getLength(String text) {
		 int textLength = text.length();
		 int length = textLength;
		 for (int i = 0; i < textLength; i++) {
			 if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				 length++;
			 }
		 }
		 return length;
	}
	
	public static class Material {

		private File imageFile;	// 上传的图片(必传)
		private String description;	// 相关的文字说明(必传)
		
		private Integer fontSize = 20;	//文字大小
		private Color fontColor = new Color(0xCC0000);	//文字颜色
		private Integer fontStyle = Font.ITALIC;	//文字样式
		private String fontFamily = "宋体";	//文字字体
		private Color backgroundColor = new Color(0xF7FCFF);	//背景颜色
		
		/*
		 * 以下参数为计算所得参数
		 */
		private int imgHeight;
		private int imgWidth;
		private int textHeight;
		private BufferedImage image;
		
		public Material(File imageFile, String description){
			this.imageFile = imageFile;
			this.description = description;
		}

		public File getImageFile() {
			return imageFile;
		}

		public void setImageFile(File imageFile) {
			this.imageFile = imageFile;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getFontSize() {
			return fontSize;
		}

		public void setFontSize(Integer fontSize) {
			this.fontSize = fontSize;
		}

		public Color getFontColor() {
			return fontColor;
		}

		public void setFontColor(Color fontColor) {
			this.fontColor = fontColor;
		}

		public Integer getFontStyle() {
			return fontStyle;
		}

		public void setFontStyle(Integer fontStyle) {
			this.fontStyle = fontStyle;
		}

		public String getFontFamily() {
			return fontFamily;
		}

		public void setFontFamily(String fontFamily) {
			this.fontFamily = fontFamily;
		}

		public int getImgHeight() {
			return imgHeight;
		}

		public void setImgHeight(int imgHeight) {
			this.imgHeight = imgHeight;
		}

		public int getImgWidth() {
			return imgWidth;
		}

		public void setImgWidth(int imgWidth) {
			this.imgWidth = imgWidth;
		}

		public int getTextHeight() {
			return textHeight;
		}

		public void setTextHeight(int textHeight) {
			this.textHeight = textHeight;
		}

		public BufferedImage getImage() {
			return image;
		}

		public void setImage(BufferedImage image) {
			this.image = image;
		}

		public Color getBackgroundColor() {
			return backgroundColor;
		}

		public void setBackgroundColor(Color backgroundColor) {
			this.backgroundColor = backgroundColor;
		}
		
	}
}
