package myMove;

import java.util.ArrayList;
import java.util.List;

import moveInfo.MoveInfo;
import moveUtil.DBUtils;

public class MoveSave {
	
	static DBUtils dbUtils=new DBUtils();
	StringBuffer sql = new StringBuffer();
	public static int moveSave(MoveInfo moveInfo){
		
		String sql ="insert into moveLink(moveName, moveLink, moveScore) values(?,?,?)";
		List<String> sqlValues=new ArrayList<>();
		sqlValues.add(moveInfo.getMoveName());
		sqlValues.add(moveInfo.getMoveLink());
		sqlValues.add(moveInfo.getMoveScore());
		int result = dbUtils.executeUpdate(sql.toString(), sqlValues);
		return result;
	}
}
