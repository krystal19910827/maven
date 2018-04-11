package csbReport.thread;

import crmUtil.tools.staticVar.UtilStatic;
import csbReport.dao.Lte4GDao;

import java.util.List;

public class Lte4GThread extends Thread{
	private String deadDt;
	private String dbNbr;
	private Lte4GDao lte4GDao;
	private String path;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDeadDt() {
		return deadDt;
	}
	public void setDeadDt(String deadDt) {
		this.deadDt = deadDt;
	}
	public String getDbNbr() {
		return dbNbr;
	}
	public void setDbNbr(String dbNbr) {
		this.dbNbr = dbNbr;
	}
	public Lte4GDao getLte4GDao() {
		return lte4GDao;
	}
	public void setLte4GDao(Lte4GDao lte4gDao) {
		lte4GDao = lte4gDao;
	}
	public void run()
	{
		UtilStatic UtilStatic =new UtilStatic();
		int db_nbr1=Integer.parseInt(this.dbNbr,10)-1;
		String provincename=UtilStatic.CRM_CODE.get(db_nbr1).provinceName.substring(0,2);
		String time =this.deadDt.substring(0,7).replace("-","");
		List<String>list=this.lte4GDao.getUnFinished(dbNbr);
			System.out.println("this.dbNbr="+this.dbNbr+" list.size() "+list.size());
		for(int i=0;i<list.size();i++)
			{
				String startDt=list.get(i).split("[|]")[0];
				String overDt=list.get(i).split("[|]")[1];
				this.lte4GDao.getOrder(startDt, overDt, this.dbNbr,this.deadDt,this.path);
				System.out.println("this.dbNbr="+this.dbNbr+"overDt="+overDt+" startDt="+startDt +" tempList..size()=");
				//WriteTxt wt=new crmUtil.tools.files.WriteTxt();
				//System.out.println(this.path+"/"+this.dbNbr+"_orderList");
 				this.lte4GDao.updateUnFinish(dbNbr, startDt);
			}
	}
	
	
	
	
	
	
	
	
	
}
