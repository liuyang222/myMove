package moveUtil;
public class TestProperties {
	
	//读取配置文件
	static TestConfig tc = new TestConfig("conf/env.properties");
	//下面是mySql的连接数据
		private static String dbType;//数据库类型
		private static String dbUserName;//数据库用户名
		private static String dbPassWord;//数据库密码
		private static String dbUrl;//数据库连接网址
		private static String dbHost;
	//ssH连接的信息
		private static String sshHost;//ssH 服务器地址
	    private static String sshUserName;//ssH用户名
	    private static String sshPwd;//ssH密码
	    private static String sshPort;//ssH端口
	    
	/**
	 * 数据库的连接类型
	 * @return dbType
	 */
	public static String getDbType() {
		
			dbType = tc.getValue("db_type");
			return dbType;
	}
		
	
	/**
	 * 数据库用户名
	 * @return dbUserName
	 */
	public static String getDbUserName() {
			
		dbUserName = tc.getValue("db_username");
		return dbUserName;
	}
	
	/**
	 * 数据库用户密码
	 * @return dbPassWord
	 */
	public static String getDbPassWord() {
		
		dbPassWord = tc.getValue("db_password");
		return dbPassWord;
	}
	
	/**
	 * 数据连接地址
	 * @return db_url
	 */
	public static String getDbUrl() {
		
		dbUrl= tc.getValue("db_url");
		return dbUrl;
	}
	
	/**
	 * ssH服务器地址
	 * @return sshHost
	 */
	public static String getSshHost(){
		
		sshHost=tc.getValue("sshHost");
		return sshHost;
		
	}
	
	/**
	 * ssH用户名
	 * @return sshUserName
	 */
	public static String getSshUserName(){
		
		sshUserName=tc.getValue("sshUserName");
		return sshUserName;
	}
	
	/**
	 * ssH密码
	 * @return sshPwd
	 */
	public static String getSshPwd(){
		
		sshPwd=tc.getValue("sshPwd");
		return sshPwd;
	}
	
	/**
	 * ssH端口
	 * @return sshPort
	 */
	public static String getSshPort(){
		
		sshPort=tc.getValue("sshPort");
		return sshPort;
	}
	
	/**
	 * 数据库ip
	 * @return dbHost
	 */
	public static String getDbHost(){
		
		dbHost=tc.getValue("dbHost");
		return dbHost;
	}
}