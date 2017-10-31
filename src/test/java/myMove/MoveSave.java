package myMove;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import moveInfo.MoveInfo;
import moveUtil.DBUtils;

public class MoveSave {
	
	static DBUtils dbUtils=new DBUtils();
	StringBuffer sql = new StringBuffer();
	private int status;
	public int moveSave(MoveInfo moveInfo){
		
		String sql ="insert into myMove(moveName, moveLink, moveScore) values(?,?,?)";
		List<String> sqlValues=new ArrayList<>();
		sqlValues.add(moveInfo.getMoveName());
		sqlValues.add(moveInfo.getMoveLink());
		sqlValues.add(moveInfo.getMoveScore());
		int result = dbUtils.executeUpdate(sql.toString(), sqlValues);
		return result;
	}
	
	public int sqlDelete(){
		
		String sqlDelete="TRUNCATE TABLE myMove";
		
		try {
			
			status=dbUtils.delete(sqlDelete);
			System.out.println("删除是否成功:"+ status);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
}
