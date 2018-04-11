package csbReport.action;
 
import crmUtil.intf.CrmSpringAction;
import csbReport.dao.CsbReportDao;
import csbReport.thread.GetSvcScoreTread;

import java.util.ArrayList;
import java.util.List;

public class GetSvcScoreAction implements CrmSpringAction{
 	private CsbReportDao csbReportDao;
 	private String flag;
	private String startDt;
	private String overDt;
	private String path;
	private List<String> svcCodeList;
	private List<GetSvcScoreTread>getSvcScoreTreadList;
	
	
	 
 
	public List<GetSvcScoreTread> getGetSvcScoreTreadList() {
		return getSvcScoreTreadList;
	}
	public void setGetSvcScoreTreadList(List<GetSvcScoreTread> getSvcScoreTreadList) {
		this.getSvcScoreTreadList = getSvcScoreTreadList;
	}
	public CsbReportDao getCsbReportDao() {
		return csbReportDao;
	}
	public void setCsbReportDao(CsbReportDao csbReportDao) {
		this.csbReportDao = csbReportDao;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getOverDt() {
		return overDt;
	}
	public void setOverDt(String overDt) {
		this.overDt = overDt;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<String> getSvcCodeList() {
		return svcCodeList;
	}
	public void setSvcCodeList(List<String> svcCodeList) {
		this.svcCodeList = svcCodeList;
	}
 
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public void action() {
	
		this.getSvcScoreTreadList  =new ArrayList<GetSvcScoreTread>();
		for(int i=0;i<this.svcCodeList.size();i++)
		{
			GetSvcScoreTread getSvcScoreTread=new GetSvcScoreTread();
			getSvcScoreTread.setFlag(this.flag);
			getSvcScoreTread.setCsbReportDao(this.csbReportDao);
			getSvcScoreTread.setSvcCode(svcCodeList.get(i).split("[|]")[0]);
			getSvcScoreTread.setOverDt(overDt);
			getSvcScoreTread.setStartDt(startDt);
			getSvcScoreTread.setPath(path);
			getSvcScoreTread.setSystemName(svcCodeList.get(i).split("[|]")[1]); 
 			getSvcScoreTread.setName(svcCodeList.get(i).split("[|]")[0]);
 			getSvcScoreTreadList.add(getSvcScoreTread);
		}
		for(int i=0;i<getSvcScoreTreadList.size();i++)
		{
			getSvcScoreTreadList.get(i).start();
			System.out.println(getSvcScoreTreadList.get(i).getName()+" start");
		}
		boolean rep=true;
		while(rep)
		{
			try {
				for(int i=0;i<this.getSvcScoreTreadList.size();i++)
				{
					if (this.getSvcScoreTreadList.get(i).isAlive())
					{
						rep=true;
						break;
					}
					else
					{
						rep=false;
					}
				
				}
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	@Override
	public void afterDoIt() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void beforeDoIt() {
		// TODO Auto-generated method stub
		
	}
}
