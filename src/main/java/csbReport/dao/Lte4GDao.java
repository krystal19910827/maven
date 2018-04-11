package csbReport.dao;

import crmUtil.tools.files.WriteTxt;
import crmUtil.tools.staticVar.UtilStatic;
import kira.db.tool.intf.Crm4gDbLinks;
import kira.db.tool.intf.CrmCsbDbLinks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Lte4GDao {

	private Crm4gDbLinks crm4GDbLinks;
	private CrmCsbDbLinks crmCsbDbLinks;

	public CrmCsbDbLinks getCrmCsbDbLinks() {
		return crmCsbDbLinks;
	}

	public void setCrmCsbDbLinks(CrmCsbDbLinks crmCsbDbLinks) {
		this.crmCsbDbLinks = crmCsbDbLinks;
	}

	public Crm4gDbLinks getCrm4GDbLinks() {
		return crm4GDbLinks;
	}
	public void setCrm4GDbLinks(Crm4gDbLinks crm4gDbLinks) {
		crm4GDbLinks = crm4gDbLinks;
	}

	public void getOrder(String startDt, String overDt, String dbNbr,String deadLine,String path) {
		List<String> list = new ArrayList<String>();
		int db_nbr=Integer.parseInt(dbNbr,10)-1;
		UtilStatic UtilStatic =new UtilStatic();
        String lan_id=UtilStatic.CRM_CODE.get(db_nbr).commonRegion.substring(0,3).concat("%");
		String sql = "SELECT DISTINCT CO.CUST_ORDER_ID,OI.ACCESS_NBR,CO.STAFF_ID,CO.LAN_ID,TO_CHAR(CO.ACCEPT_TIME,'YYYY-MM-DD HH24:MI:SS'),CO.STATUS_CD,CO.CHANNEL_ID FROM CRM_"
				+ dbNbr
				+ ".ORDER_ITEM OI,\n"
				+ "CRM_"
				+ dbNbr
				+ ".CUSTOMER_ORDER CO WHERE CO.CUST_ORDER_ID=OI.CUST_ORDER_ID\n"
				+ "AND CO.ACCEPT_TIME>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND CO.ACCEPT_TIME<=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+" AND CO.ACCEPT_TIME<=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')"
				+ "AND CO.STATUS_CD!='100004'\n"
				+ "AND CO.STATUS_CD!='100001'\n"
				+ "AND CO.LAN_ID like (?)\n"
				+ "AND CO.STATUS_CD!='301200'\n" + "AND CO.STATUS_CD!='100002'";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;
		System.out.println("dbNbr=" + dbNbr);
		try {

			if (dbNbr.equals("1") || dbNbr.equals("2")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm0102GdJsDbConnection();
			} else if (dbNbr.equals("4") || dbNbr.equals("3")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm0304ScZjDbConnection();
			} else if (dbNbr.equals("5") || dbNbr.equals("6")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm0506AhSxDbConnection();
			} else if (dbNbr.equals("7") || dbNbr.equals("8")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm0708FjShDbConnection();
			} else if (dbNbr.equals("9")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm09HbDbConnection();
			} else if (dbNbr.equals("10")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm10NmDbConnection();
			} else if (dbNbr.equals("11") || dbNbr.equals("12")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1112HnLnDbConnection();
			} else if (dbNbr.equals("13") || dbNbr.equals("14")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1314HbGzDbConnection();
			} else if (dbNbr.equals("15") || dbNbr.equals("16")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1516SdCqDbConnection();
			} else if (dbNbr.equals("17") || dbNbr.equals("18")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1718XjBjDbConnection();
			} else if (dbNbr.equals("19") || dbNbr.equals("20")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1920TjJxDbConnection();
			} else if (dbNbr.equals("21") || dbNbr.equals("22")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm2122QhGsDbConnection();
			} else if (dbNbr.equals("24") || dbNbr.equals("23")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm2324JlHnDbConnection();
			} else if (dbNbr.equals("25") || dbNbr.equals("26")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm2526HlYnDbConnection();
			} else if (dbNbr.equals("27") || dbNbr.equals("28")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm2728GxSxDbConnection();
			} else if (dbNbr.equals("29") || dbNbr.equals("30")
					|| dbNbr.equals("31") || dbNbr.equals("32")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm293031HnNxXzDbConnection();
			}
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, startDt);
			pstmtX.setString(2, overDt);
			pstmtX.setString(3, deadLine);
			pstmtX.setString(4, lan_id);
			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5) + "|" + rs.getString(6)+"|"+ rs.getString(7));

			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			int db_nbr1=Integer.parseInt(dbNbr,10)-1;
			String provincename=UtilStatic.CRM_CODE.get(db_nbr1).provinceName.substring(0,2);
			String time =deadLine.substring(0,7).replace("-","");
			WriteTxt wt=new crmUtil.tools.files.WriteTxt();
			System.out.println(path+"/"+dbNbr+"_orderList");
			wt.writeTxt(path+"/"+provincename.concat(time).concat("超30天未报竣")+"_orderList", list, true);

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}
	public List<String> getCount (int db_nbr,String deadDt) {
		List<String> list = new ArrayList<String>();
		String dbNbr=String.valueOf(db_nbr);
		UtilStatic UtilStatic =new UtilStatic();
		String lan_id=UtilStatic.CRM_CODE.get(db_nbr-1).commonRegion.substring(0,3).concat("%");
		String sql ="select count(DISTINCT CO.CUST_ORDER_ID)"
				+"FROM CRM_"
		        +dbNbr
				+".ORDER_ITEM OI, CRM_"
				+dbNbr
				+".CUSTOMER_ORDER CO WHERE CO.CUST_ORDER_ID=OI.CUST_ORDER_ID "
		        +"AND CO.ACCEPT_TIME>=TO_DATE('2014-07-22 00:00:00','YYYY-MM-DD HH24:MI:SS')\n"
				+"AND CO.ACCEPT_TIME<=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
		        +"AND CO.ACCEPT_TIME<=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
		        +"AND CO.STATUS_CD!='100004' AND CO.STATUS_CD!='301200' AND CO.STATUS_CD!='100002' AND CO.STATUS_CD!='100001'"
				+"and co.lan_id like (?)";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;
		try {

			if (dbNbr.equals("1") || dbNbr.equals("2")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm0102GdJsDbConnection();
			} else if (dbNbr.equals("4") || dbNbr.equals("3")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm0304ScZjDbConnection();
			} else if (dbNbr.equals("5") || dbNbr.equals("6")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm0506AhSxDbConnection();
			} else if (dbNbr.equals("7") || dbNbr.equals("8")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm0708FjShDbConnection();
			} else if (dbNbr.equals("9")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm09HbDbConnection();
			} else if (dbNbr.equals("10")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm10NmDbConnection();
			} else if (dbNbr.equals("11") || dbNbr.equals("12")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1112HnLnDbConnection();
			} else if (dbNbr.equals("13") || dbNbr.equals("14")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1314HbGzDbConnection();
			} else if (dbNbr.equals("15") || dbNbr.equals("16")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1516SdCqDbConnection();
			} else if (dbNbr.equals("17") || dbNbr.equals("18")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1718XjBjDbConnection();
			} else if (dbNbr.equals("19") || dbNbr.equals("20")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm1920TjJxDbConnection();
			} else if (dbNbr.equals("21") || dbNbr.equals("22")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm2122QhGsDbConnection();
			} else if (dbNbr.equals("24") || dbNbr.equals("23")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm2324JlHnDbConnection();
			} else if (dbNbr.equals("25") || dbNbr.equals("26")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm2526HlYnDbConnection();
			} else if (dbNbr.equals("27") || dbNbr.equals("28")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm2728GxSxDbConnection();
			} else if (dbNbr.equals("29") || dbNbr.equals("30")
					|| dbNbr.equals("31") || dbNbr.equals("32")) {
				jdbc_Connect_Oracle = crm4GDbLinks.getCrm293031HnNxXzDbConnection();
			}
		  	pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, deadDt);
			pstmtX.setString(2, deadDt);
			pstmtX.setString(3, lan_id);
			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(dbNbr+"[|]" +rs.getString(1));}
				pstmtX.close();
				jdbc_Connect_Oracle.close();
			return list;
		} catch (SQLException e) {
				e.printStackTrace();
			return list;
			}
	    }

	public void updateUnFinish(String dbNbr,String startDt)
	{
		String sql = "UPDATE TIME_RULUE_HOUR HR SET HR.IF_FINISH='Y' WHERE HR.STARTDT=? AND HR.IF_FINISH='N' AND TRIM(HR.DB_NBR)=TRIM(?)";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;
		System.out.println("dbNbr=" + dbNbr);
		try {

			jdbc_Connect_Oracle = this.crmCsbDbLinks
					.getCrmAllCsbLogDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, startDt);
			pstmtX.setString(2, dbNbr);

		   pstmtX.executeUpdate();
			 
			pstmtX.close();
			jdbc_Connect_Oracle.close();
		 

		} catch (SQLException e) {

			e.printStackTrace();
 
		}

		
		
	}
	
	
	public List<String> getUnFinished(String dbNbr) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT  DISTINCT HR.STARTDT ,HR.OVERDT,TRIM(DB_NBR),HR.IF_FINISH FROM TIME_RULUE_HOUR HR WHERE HR.IF_FINISH='N' AND TRIM(HR.DB_NBR)=TRIM(?) AND ROWNUM<1000000";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;
		System.out.println("dbNbr=" + dbNbr);
		try {

			jdbc_Connect_Oracle = this.crmCsbDbLinks
					.getCrmAllCsbLogDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, dbNbr);

			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4));

			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return list;

		}

	}

}
