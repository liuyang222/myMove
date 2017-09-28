package moveUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class TestConfig {
	private Properties propertie;
	private FileInputStream inputfile;

	// 读取配置文件
	public TestConfig(String filepath) {
		try {
			
			filepath = TestProperties.class.getClassLoader().getResource("").toURI().getPath()+ filepath;
			System.out.println("路径是:"+filepath);
			filepath=convertFilepath(filepath);

		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			propertie = new Properties();
			inputfile = new FileInputStream(filepath);
			propertie.load(inputfile);
			inputfile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 得到对应key的值
	public String getValue(String key) {
		
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);
			return value;
		} else
			return "";
	}
	
	/**
	 * 处理编译后的文件路径
	 * 
	 * @param filepath
	 * @return
	 */
	public static String convertFilepath(String filepath) throws Exception {

		if (filepath.contains("bin")) {
			filepath = filepath.replace("bin/", "");
		}
		return filepath;
	}

}
