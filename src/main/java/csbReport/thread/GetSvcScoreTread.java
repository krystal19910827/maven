package csbReport.thread;

import java.util.ArrayList;
import java.util.List;
import crmUtil.tools.xls.CreateXls;
import csbReport.dao.CsbReportDao;
 public class GetSvcScoreTread extends Thread{
	private String flag;
	private String svcCode;
	private String startDt;
	private String overDt;
	private String systemName;
	private String path;
 
	public CsbReportDao csbReportDao;
	
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getSvcCode() {
		return svcCode;
	}
	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
 
	public CsbReportDao getCsbReportDao() {
		return csbReportDao;
	}
	public void setCsbReportDao(CsbReportDao csbReportDao) {
		this.csbReportDao = csbReportDao;
	}
	
	public void run()
	{
		String []titles={"CK_RULE","SVC_NAME","SVC_CODE","DST_CODE","PROVINCE_NAME","OK_REC_CNT","ALL_REC_CNT"};
		 List<String> list=new ArrayList<String>();
		 crmUtil.tools.xls.CreateXls cx=new CreateXls();
		 System.out.println(this.systemName);
		if(this.systemName.equals("4G"))
		{
			list=this.csbReportDao.getCsbRec4G(startDt, overDt, systemName, svcCode);
			cx.createAllNetXls(path+this.svcCode+"_"+this.systemName+"_"+this.flag, list, titles, "sheet1");
		}
		else if(this.systemName.equals("3G"))
		{
			list=this.csbReportDao.getCsbRec3G(startDt, overDt, systemName, svcCode);
			cx.createAllNetXls(path+this.svcCode+"_"+this.systemName+"_"+this.flag, list, titles, "sheet1");		}
		
		 
	}
	
	
}
