package moveUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 查询结果处理接口
 * 
 * @author 人淡如菊
 * QQ:476273837  备注：18201059157
 * @version 创建时间：2016-3-31 下午07:26:41
 */

public class DBUtils {

	private static String dbType = TestProperties.getDbType();// get dbType
	private static String dbUserName = TestProperties.getDbUserName();// get dbUser
	private static String dbPassWord = TestProperties.getDbPassWord();// get dbPwd
	private static String dbUrl = TestProperties.getDbUrl();// get dbUrl
	private static Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rst = null;
	private ParameterMetaData psm;
	private int paramNum;
	private int status = 0;

	public DBUtils() {

		conn = DBUtils.getConnection();
	}

	// database connect
	public static synchronized Connection getConnection() {

		if (conn == null) {

			try {

				if (dbType.equals("mysql")) {

					Class.forName("com.mysql.jdbc.Driver");

				} else if (dbType.equals("oracle")) {

					Class.forName("oracle.jdbc.driver.OracleDriver")
							.newInstance();

				} else {

					System.out.println("类型：" + dbType);
					System.out.print("undefined db type !");
				}

				conn = DriverManager.getConnection(dbUrl, dbUserName,
						dbPassWord);// local connect
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("数据库链接成功");

		}

		return conn;
	}

	/**
	 * get ResultSet
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public ResultSet getResultSet(Connection conn, String sql) throws Exception {
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			// PreparedStatement pstmt;
			// ResultSet rset;
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// pstmt = conn.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}

		return resultSet;
	}

	/**
	 * get ColumnCount
	 * 
	 * @param resultSet
	 * @return
	 * @throws Exception
	 */
	private int getColumnCount(ResultSet resultSet) throws Exception {
		int columnCount = 0;
		try {
			// ResultSet resultSet = this.getResultSet(conn, sql);
			columnCount = resultSet.getMetaData().getColumnCount();
			if (columnCount == 0) {
				System.out.print("sql error!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return columnCount;
	}

	/**
	 * get ColumnCount
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int getColumnCount(Connection conn, String sql) throws Exception {
		int columnCount = 0;
		try {
			// ResultSet resultSet = this.getResultSet(conn, sql);
			columnCount = getResultSet(conn, sql).getMetaData()
					.getColumnCount();
			if (columnCount == 0) {
				System.out.print("sql error!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return columnCount;
	}

	/**
	 * get RowCount
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int getRowCount(Connection conn, String sql) throws Exception {
		int rowCount = 0;
		try {
			rst = getResultSet(conn, sql);
			rst.last();
			rowCount = rst.getRow();
			if (rowCount == 0) {
				System.out.print("sql query no data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return rowCount;
	}

	/**
	 * get RowCount
	 * 
	 * @param resultSet
	 * @return
	 * @throws Exception
	 */
	private int getRowCount(ResultSet rst) throws Exception {
		int rowCount = 0;
		try {
			rst.last();
			rowCount = rst.getRow();
			if (rowCount == 0) {
				System.out.print("sql query no data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return rowCount;
	}

	/**
	 * get data by row index and col index
	 * 
	 * @param conn
	 * @param sql
	 * @param row
	 * @param col
	 * @return
	 * @throws Exception
	 */
	public String getData(Connection conn, String sql, int row, int col)
			throws Exception {
		String data = null;
		int rownum = 0;
		int rowcount = 0;
		int colcount = 0;
		try {

			rst = getResultSet(conn, sql);
			colcount = getColumnCount(rst);
			rowcount = getRowCount(rst);
			rst.beforeFirst();
			if (rowcount > 0) {
				if (row <= 0 || row > rowcount) {
					System.out.print("error row index!");
				} else {
					if (col <= 0 || col > colcount) {
						System.out.print("error col index!");
					} else {
						while (rst.next()) {
							rownum++;
							if (rownum == row) {
								data = rst.getString(col);
								break;
							}
						}
					}
				}
			} else {
				System.out.print("sql query no data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}

		return data;
	}

	/**
	 * get data by row index and col index
	 * 
	 * @param conn
	 * @param sql
	 * @param row
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public String getData(Connection conn, String sql, int row, String field)
			throws Exception {
		String data = null;
		int rownum = 0;
		int rowcount = 0;
		try {
			rst = getResultSet(conn, sql);
			// colcount = getColumnCount(resultSet);
			rowcount = getRowCount(rst);
			rst.beforeFirst();
			if (rowcount > 0) {
				if (row <= 0 || row > rowcount) {
					System.out.print("error row index!");
				} else {
					while (rst.next()) {
						rownum++;
						if (rownum == row) {
							data = rst.getString(field);
							break;
						}
					}
				}
			} else {
				System.out.print("sql query no data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return data;
	}

	// update database
	public int update(String sql, Object[] params) throws SQLException {

		try {
			pst = conn.prepareStatement(sql);
			psm = pst.getParameterMetaData();// get query info
			paramNum = psm.getParameterCount();// get params count
			for (int i = 1; i <= paramNum; i++) {
				pst.setObject(i, params[i - 1]);
			}

			status = pst.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return status;
	}

	// update database
	public int updateN(String sql, Object[] params, int N) throws SQLException {

		try {
			pst = conn.prepareStatement(sql);
			psm = pst.getParameterMetaData();// get query info
			paramNum = psm.getParameterCount();// get params count
			for (int i = 1; i <= paramNum; i++) {
				pst.setObject(i, params[i - 1]);
			}

			status = pst.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {

			// con.close();
		}

		return status;
	}

	// query database
	public ResultSet query(String sql, Object[] params) throws SQLException {

		try {

			pst = conn.prepareStatement(sql);
			psm = pst.getParameterMetaData();// get param info
			paramNum = psm.getParameterCount();// get params count
			for (int i = 1; i <= paramNum; i++) {
				pst.setObject(i, params[i - 1]); // 给参数赋值（param里面的值要和sql里面的占位符一一对应）
			}
			rst = pst.executeQuery();
			return rst;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int delete(String sql, Object[] params) throws SQLException {

		try {

			pst = conn.prepareStatement(sql);
			psm = pst.getParameterMetaData(); // get param info
			paramNum = psm.getParameterCount();// get params count

			for (int i = 1; i <= paramNum; i++) {
				pst.setObject(i, params[i - 1]);
			}

			status = pst.executeUpdate();
			System.out.println("Delete resutl: " + status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public int delete(String sql) throws SQLException {

		try {

			pst = conn.prepareStatement(sql);
			status = pst.executeUpdate();
			System.out.println("Delete resutl: " + status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	/**
     * update
     * 
     * @param sql
     * @param sqlValues
     * @return result
     */
    public int executeUpdate(String sql, List<String> sqlValues) {
        int result = -1;
        try {
            pst = conn.prepareStatement(sql);
            if (sqlValues != null && sqlValues.size() > 0) {
                setSqlValues(pst, sqlValues);
            }
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * sql set value
     * 
     * @param pst
     * @param sqlValues
     */
    private void setSqlValues(PreparedStatement pst, List<String> sqlValues) {
        for (int i = 0; i < sqlValues.size(); i++) {
            try {
                pst.setObject(i + 1, sqlValues.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
	// close connect
	public void close() {

		try {
			if (conn != null) {

				DBUtils.conn.close();
			}
			if (pst != null) {

				this.pst.close();
			}
			if (rst != null) {

				this.rst.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 最优经验是按照ResultSet，Statement，Connection的顺序执行close；

}
