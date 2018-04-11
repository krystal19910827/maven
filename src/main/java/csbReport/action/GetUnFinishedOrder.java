package csbReport.action;
import crmUtil.intf.CrmSpringAction;
import csbReport.thread.Lte4GThread;

import java.util.List;

public class GetUnFinishedOrder implements CrmSpringAction {
	 private List<Lte4GThread>getOrderList;

	public List<Lte4GThread> getGetOrderList() {
		return getOrderList;
	}

	public void setGetOrderList(List<Lte4GThread> getOrderList) {
		this.getOrderList = getOrderList;
	}

	@Override
	public void action() {
		for(int i=0;i<this.getOrderList.size();i++)
		{
			this.getOrderList.get(i).start();
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
