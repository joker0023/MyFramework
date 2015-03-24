package test.joker.myfw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import redis.clients.jedis.JedisPoolConfig;
import sun.misc.BASE64Decoder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.joker.myfw.data.Data;
import com.joker.myfw.util.ImageUtil;
import com.joker.myfw.util.WebUtil;
import com.joker.myfw.vo.comm.ActionRecord;
import com.joker23.orm.util.GenerateSqlUtil;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

public class CommTest {
	
	public static void main(String[] args) {
		try{
			String text = "【淘店长交易】亲，您购买的宝贝已发申通，快递单号：2532523523，请注意查收，欢迎下次光临！";
			String apikey = "1167fc885383eb670387e351f62ac925";
			String mobile = "15017564850";
			String url = "http://yunpian.com/v1/sms/send.json";
			
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("apikey", apikey);
			params.put("mobile", mobile);
			params.put("text", text);
			String result = WebUtil.doPost(url, params, null);
			System.out.println(result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void base64imgTest() {
		try{
			String imgData = "iVBORw0KGgoAAAANSUhEUgAAASwAAAEsCAYAAAB5fY51AAAEq0lEQVR4nO3SQQ3AMADEsEIv8w7B3peHLYVBzgEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAg411JfxGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHLErIeQyhGzHkIqR8x6CKkcMeshpHIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANHzm5NFo61jt6QAAAABJRU5ErkJggg==";
			
			String file = "e://imgTest.jpeg";
			
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] data = decoder.decodeBuffer(imgData);
			
			for (int i = 0; i < data.length; ++i) {
				if (data[i] < 0) {// 调整异常数据  
					data[i] += 256;  
				}  
			}  
			
			OutputStream out = new FileOutputStream(file);  
			out.write(data);  
			out.flush();  
			out.close();  
 		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void imgTest() {
		try{
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
