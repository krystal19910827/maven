package csbReport.action;

import crmUtil.intf.CrmSpringAction;
import csbReport.dao.Lte4GDao;
import csbReport.thread.Lte4GScoreTread;

/**
 * Created by lenovo on 2017/8/4.
 */
public class GetUnFinishedCountAction implements CrmSpringAction {
    private String deadDt;
    private Lte4GDao lte4GDao;
    public String getDeadDt() {
        return deadDt;
    }
    public void setDeadDt(String deadDt) {
        this.deadDt = deadDt;
    }
    public Lte4GDao getLte4GDao() {
        return lte4GDao;
    }
    public void setLte4GDao(Lte4GDao lte4gDao) {
        this.lte4GDao = lte4gDao;
    }

    public void action() {
        Lte4GScoreTread lte4GScoreTread=new Lte4GScoreTread();
        lte4GScoreTread.setLte4GDao(this.lte4GDao);
        lte4GScoreTread.setDeadDt(this.deadDt);
        lte4GScoreTread.start();
    }


    public void afterDoIt() {
        // TODO Auto-generated method stub

    }

    public void beforeDoIt() {
        // TODO Auto-generated method stub

    }

}


