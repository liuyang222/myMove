package myMove;

import java.util.ArrayList;
import java.util.List;

import moveInfo.MoveInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 
 * @author 人淡如菊
 * QQ:476273837
 * time:2017/09/27
 *
 */

public class myMoveLink implements PageProcessor {
	
	public final String filmUrl_base="http://www\\.dytt8\\.net/html/gndy/dyzz/list_23_\\w+\\.html";
	private int pageXY;//界面上的"页"字所在的位置
	private int first=0;//标志位,代表总页数只获取一次
	private int total;//总页数
	private String moveName;//电影名称
	private String moveLink;//电影下载链接
	private String moveScore;//电影评分
	private int tenXY;//评分的位置索引（IMDB评分 7.0/10 from 11,392 users）
	private Long Score;//截取评分的第一位数字进行判断,如果>8就存放数据库
	MoveSave moveSave=new MoveSave();
	private Site site = Site
	            .me()
	            .setDomain("www.dytt8.net")
	            .setSleepTime(3000)
	            .setUserAgent(
	                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	public void process(Page page) {
		// TODO Auto-generated method stub
		MoveInfo moveInfo=new MoveInfo();
		//先动态获取该年度的电影一共有多少页，截取共-页之间的数据
		if(first==0){
			//获取页字在该界面的索引位置
			pageXY=page.getHtml().xpath("//*[@id='header']/div/div[3]/div[3]/div[2]/div[2]/div[2]/div").toString().indexOf("页");
			//截取共和页之间的数据，共是固定位置索引位置是18
			total=Integer.parseInt(page.getHtml().xpath("//*[@id='header']/div/div[3]/div[3]/div[2]/div[2]/div[2]/div").toString().substring(18, pageXY));
			first=1;//获取总页数后，标志位修改为1
		}
		
		List<String> filmUrlList=new ArrayList<String>();
		//添加所有的列表页到要爬取的队列中
		for(int i=1;i<=total;i++){
			
			filmUrlList.add("http://www.dytt8.net/html/gndy/dyzz/list_23_"+i+".html");
			page.addTargetRequests(filmUrlList);
		}
		//用正则判断是否是列表页
		if(page.getUrl().regex(filmUrl_base).match()){
			
			//获取所有详情页的链接添加到目标URL
			List<String> urls=page.getHtml().xpath("//*[@id='header']/div/div[3]/div[3]/div[2]/div[2]/div[2]/ul").links().all();
			
			for(String url:urls){
				
				System.out.println("详情页的链接地址"+url);
				page.addTargetRequest(url); 
				
				
			}
		}else{
										
			//获取包含电影名称的文本			
			moveName=page.getHtml().xpath("//*[@id='header']/div/div[3]/div[3]/div[2]/div[2]/div[1]/h1/font").toString();
			//从moveName中获取《xxxxxx》这部分的电影名称
			int startXY=moveName.indexOf("《");
			int endXy=moveName.indexOf("》");			
			moveName=moveName.substring(startXY, endXy+1);
			System.out.println("截取后的电影名称"+moveName);
			
			//获取包含电影下载地址的文本   
			moveLink=page.getHtml().xpath("//*[@id='Zoom']/span/*/tbody/tr/td/a/text()").toString();
			System.out.println("截取后的电影连接是"+moveLink);
			
			/*
			*含有评分的字段文本有两种格式
			*IMDB评分 7.8/10  2,861 votes
			*IMDb评分  5.4/10 from 1,491 users
			*
			*/
			String moveScoreText=page.getHtml().xpath("//*[@id='Zoom']/span").toString();
			//IMDb位置索引
			int IMDbXY=moveScoreText.indexOf("IMDb");
			int IMDBXY=moveScoreText.indexOf("IMDB");
			//豆瓣评分的位置索引
			int douban=moveScoreText.indexOf("豆瓣评分");
			//如果文本中不含有这两种评分，则显示该电影无评分
			if(IMDbXY==-1 && IMDBXY==-1 && douban==-1){
				
				System.out.println("该电影无评分");
				
			}else{
				//获取该文本的位置,以便于获取评分
				tenXY=moveScoreText.indexOf("/10 from");
				//如果查找到该text,就获取评分
				if(tenXY!=-1){
					
					moveScore=moveScoreText.substring(tenXY-3, tenXY).trim();
					System.out.println("评分文本"+moveScore);
				}else{
					//否则查找另一种text,获取评分
					tenXY=moveScoreText.indexOf("/10 ");
					moveScore=moveScoreText.substring(tenXY-3, tenXY).trim();
					System.out.println("评分文本"+moveScore);
				}
				//转换评分为Long类型
				Score=Long.parseLong(moveScore.substring(0, 1));
				
			}
			
			//如果评分大于8.0,就存放到数据库
			if(Score>=8){
				
				moveInfo.setMoveName(moveName);
				moveInfo.setMoveLink(moveLink);
				moveInfo.setMoveScore(moveScore);
				//把过滤后的信息存放数据库
				MoveSave.moveSave(moveInfo);
			}
			
		}
		
	}

	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	
	public static void main(String[] args){
		
		Spider.create(new myMoveLink()).addUrl("http://www.dytt8.net/html/gndy/dyzz/list_23_1.html")
		.thread(10)//启用10个线程
		.run();
	}

}
