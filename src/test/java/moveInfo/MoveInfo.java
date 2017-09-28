package moveInfo;
/**
 * 
 * @author 人淡如菊
 * time:2017/09/27
 *
 */
public class MoveInfo {
	
	@Override
	public String toString() {
		return "MoveInfo [moveName=" + moveName + ", moveLink=" + moveLink
				+ ", moveScore=" + moveScore + "]";
	}
	private String moveName;//电影名称
	private String moveLink;//电影链接
	private String moveScore;//电影评分
	
	public String getMoveName() {
		return moveName;
	}
	public void setMoveName(String moveName) {
		this.moveName = moveName;
	}
	public String getMoveLink() {
		return moveLink;
	}
	public void setMoveLink(String moveLink) {
		this.moveLink = moveLink;
	}
	public String getMoveScore() {
		return moveScore;
	}
	public void setMoveScore(String moveScore) {
		this.moveScore = moveScore;
	}
	
	
}
