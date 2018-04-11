package csbReport.app;


import crmUtil.intf.CrmSpringAction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.lang.String;

public class App {
	public  static void main(String args[])
 { 
		 //appCsbRecKafka.xml appCsbRecConsumer appCsbRecCount.xml appCsbRecCount3G.xml
		//ApplicationContextpub ctx=new FileSystemXmlApplicationContext(args[0]); //appCsbRecKafka3G.xml  appCsbRecConsumer3G.xml
		ApplicationContext ctx=new FileSystemXmlApplicationContext("appGetUnArchiveOrder.xml");
	// ApplicationContext ctx=new FileSystemXmlApplicationContext("appGetSvcErrorDetail.xml");

	 CrmSpringAction crmSpringAction=(CrmSpringAction) ctx.getBean("crmSpringAction");
        crmSpringAction.action();
	}
}
