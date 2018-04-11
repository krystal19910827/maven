package csbReport.action;

import java.util.ArrayList;
import java.util.List;

 


import crmUtil.intf.CrmSpringAction;
import csbReport.dao.CsbReportDao;
import csbReport.thread.GetSvcErrorDetailTread;

public class GetSvcErrorDetailAction implements CrmSpringAction{
 	private CsbReportDao csbReportDao;
 	private String flag;
	private String startDt;
	private String overDt;
	private String path;
	private List<String> svcCodeList;
	private List<String> dstSysCode;
	private List<GetSvcErrorDetailTread>getSvcErrorDetailTreadList;
	
	
	public List<GetSvcErrorDetailTread> getGetSvcErrorDetailTreadList() {
		return getSvcErrorDetailTreadList;
	}
	public void setGetSvcErrorDetailTreadList(
			List<GetSvcErrorDetailTread> getSvcErrorDetailTreadList) {
		this.getSvcErrorDetailTreadList = getSvcErrorDetailTreadList;
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
	public List<String> getDstSysCode() {
		return dstSysCode;
	}
	public void setDstSysCode(List<String> dstSysCode) {
		this.dstSysCode = dstSysCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public void action() {
	
		getSvcErrorDetailTreadList=new ArrayList<GetSvcErrorDetailTread>();
		for(int i=0;i<this.dstSysCode.size();i++)
		{
			GetSvcErrorDetailTread getSvcErrorDetailTread=new GetSvcErrorDetailTread();
			getSvcErrorDetailTread.setFlag(this.flag);
			getSvcErrorDetailTread.setCsbReportDao(this.csbReportDao);
			getSvcErrorDetailTread.setDstCode(dstSysCode.get(i).split("[|]")[1]);
			getSvcErrorDetailTread.setOverDt(overDt);
			getSvcErrorDetailTread.setStartDt(startDt);
			getSvcErrorDetailTread.setPath(path);
			getSvcErrorDetailTread.setProvinceName(dstSysCode.get(i).split("[|]")[0]);
			getSvcErrorDetailTread.setSvcCodeList(svcCodeList);
			getSvcErrorDetailTread.setName(dstSysCode.get(i).split("[|]")[0]);
			getSvcErrorDetailTreadList.add(getSvcErrorDetailTread);
		}
		for(int i=0;i<getSvcErrorDetailTreadList.size();i++)
		{
			getSvcErrorDetailTreadList.get(i).start();
			System.out.println(getSvcErrorDetailTreadList.get(i).getName()+" start");
		}
		boolean rep=true;
		while(rep)
		{
			try {
				for(int i=0;i<this.getSvcErrorDetailTreadList.size();i++)
				{
					if (this.getSvcErrorDetailTreadList.get(i).isAlive())
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
