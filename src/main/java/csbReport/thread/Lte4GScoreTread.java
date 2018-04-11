package csbReport.thread;

import csbReport.dao.Lte4GDao;

import java.util.ArrayList;
import java.util.List;

public class Lte4GScoreTread extends Thread {
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
        lte4GDao = lte4gDao;
    }

    public void run() {
        List<String> getList = new ArrayList<String>();
        for (int i = 1; i < 32; i++) {
            List<String> tempList = this.lte4GDao.getCount(i, this.deadDt);
            System.out.println("this.dbNbr=" + i);
            System.out.println("\n Count=" + tempList.get(1));
        }
    }
}

