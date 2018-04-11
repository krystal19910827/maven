package csbReport.thread;

import java.util.ArrayList;
import java.util.List;

 

import crmUtil.tools.files.WriteTxt;
import csbReport.dao.CsbReportDao;
public class GetSvcErrorDetailTread extends Thread{
	private String flag;
	private String dstCode;
	private String startDt;
	private String overDt;
	private List<String>svcCodeList;
	private String path;
	private String provinceName;
	public CsbReportDao csbReportDao;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getDstCode() {
		return dstCode;
	}
	public void setDstCode(String dstCode) {
		this.dstCode = dstCode;
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
	public List<String> getSvcCodeList() {
		return svcCodeList;
	}
	public void setSvcCodeList(List<String> svcCodeList) {
		this.svcCodeList = svcCodeList;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public CsbReportDao getCsbReportDao() {
		return csbReportDao;
	}
	public void setCsbReportDao(CsbReportDao csbReportDao) {
		this.csbReportDao = csbReportDao;
	}
	
	public void run()
	{
		String systemName="";
		String svcCode="";
		List<String>list=new ArrayList<String> ();
		for(int i=0;i<this.svcCodeList.size();i++)
		{
			systemName=this.svcCodeList.get(i).split("[|]")[1];
			svcCode=	this.svcCodeList.get(i).split("[|]")[0];
			list=this.csbReportDao.getErrorDetailForCsb(systemName,svcCode,dstCode, startDt, overDt);
			WriteTxt wt=new WriteTxt();
			wt.writeTxt(this.path+ svcCode+"_"+this.provinceName+"_"+systemName+"_"+this.flag,list,false);
			
		}
	}
	
	
}
