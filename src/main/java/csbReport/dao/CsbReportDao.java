package csbReport.dao;

import kira.db.tool.intf.Crm4gDbLinks;
import kira.db.tool.intf.CrmCsbDbLinks;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CsbReportDao {
	private Crm4gDbLinks crm4GDbLinks;
	private CrmCsbDbLinks crmCsbDbLinks;

	public CrmCsbDbLinks getCrmCsbDbLinks() {
		return crmCsbDbLinks;
	}

	public void setCrmCsbDbLinks(CrmCsbDbLinks crmCsbDbLinks) {
		this.crmCsbDbLinks = crmCsbDbLinks;
	}

	public Crm4gDbLinks getCrm4gDbLinks() {
		return crm4GDbLinks;
	}

	public void setCrm4gDbLinks(Crm4gDbLinks crm4gDbLinks) {
		crm4GDbLinks = crm4gDbLinks;
	}

	public List<String> getCsbRec3G(String startDt, String overDt,
			String sysName, String svcCode) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT CSL.CK_RULE,(SELECT G.NAME FROM XIEBC.CONTRACT_3G G WHERE G.CODE=CSL.SVC_CODE) SVC_NAME,CSL.SVC_CODE,CSL.DST_CODE,(SELECT TRIM(C4.NAME) FROM XIEBC.COMPONENT_3G C4 WHERE\n"
				+ "C4.COMPONENT_ID=CSL.DST_CODE)PROVINCE_NAME,SUM(CSL.ALL_REC_CNT) OK_REC_CNT,SUM(CSL.OK_REC_CNT) ALL_REC_CNT FROM XIEBC.CSB_REC CSL WHERE CSL.SCORE_DT>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND CSL.SCORE_DT<TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND CSL.SYS_NAME=?\n"
				+ "AND CSL.SVC_CODE=?\n"
				+ "AND CSL.DST_CODE IN (SELECT G3.COMPONENT_ID FROM XIEBC.COMPONENT_3G G3 WHERE G3.NAME LIKE '%CRM系统%')\n"
				+ "AND CSL.DST_CODE IS NOT NULL\n"
				+ "GROUP BY CSL.DST_CODE,CSL.SVC_CODE,CSL.CK_RULE\n"
				+ "ORDER BY CSL.DST_CODE";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = this.crmCsbDbLinks
					.getCrmAllCsbLogDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, startDt);
			pstmtX.setString(2, overDt);
			pstmtX.setString(3, sysName);
			pstmtX.setString(4, svcCode);
			 

			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5) + "|" + rs.getString(6) + "|"
						+ rs.getString(7));
			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return list;

		}

	}

	public List<String> getCsbRec4G(String startDt, String overDt,
			String sysName, String svcCode) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT CSL.CK_RULE,(SELECT G.NAME FROM XIEBC.CONTRACT_4G G WHERE G.CODE=CSL.SVC_CODE) SVC_NAME,CSL.SVC_CODE,CSL.DST_CODE,(SELECT TRIM(C4.NAME) FROM XIEBC.COMPONENT_4G C4 WHERE\n"
				+ "C4.CODE=CSL.DST_CODE)PROVINCE_NAME ,SUM(CSL.ALL_REC_CNT) OK_REC_CNT,SUM(CSL.OK_REC_CNT) ALL_REC_CNT FROM XIEBC.CSB_REC CSL WHERE CSL.SCORE_DT>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND CSL.SCORE_DT<TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND CSL.SVC_CODE=?\n"
				+ "AND CSL.SYS_NAME=?\n"
				+ "AND CSL.DST_CODE IN (SELECT G4.COMPONENT_ID FROM XIEBC.COMPONENT_4G G4 WHERE G4.NAME LIKE '%CRM系统%')AND CSL.DST_CODE IS NOT NULL\n"
				+ "GROUP BY CSL.DST_CODE,CSL.SVC_CODE,CSL.CK_RULE\n"
				+ "ORDER BY CSL.DST_CODE";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = this.crmCsbDbLinks
					.getCrmAllCsbLogDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, startDt);
			pstmtX.setString(2, overDt);
			pstmtX.setString(3, svcCode);
			pstmtX.setString(4, sysName);

			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5) + "|" + rs.getString(6) + "|"
						+ rs.getString(7));
			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return list;

		}
	}

	public List<String> getErrorDetailForCsb(String systemName, String svcCode,
			String dstCode, String startDt, String overDt) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT /*+INDEX(SED IDX_SVC_ERROR_DETAIL_CREATE_DT)*/  SED.SRC_TRAN_ID,SED.SRC_SYS_CODE,SED.DST_SYS_CODE,SED.CSBPERIODFWD,SED.PERIOD,SED.CSBPERIODREQ,TO_CHAR(SED.CREATE_TIME,'YYYY-MM-DD HH24:MI:SS'),\n"
				+ "SED.CSB_NAME,SED.SVC_CODE\n"
				+ " FROM XIEBC.SVC_ERROR_DETAIL SED WHERE\n"
				+ " SED.SVC_CODE=? \n"
				+ "AND SED.CREATE_TIME>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND SED.CREATE_TIME<=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND SED.CSB_NAME=?\n"
				+ "AND SED.DST_SYS_CODE=? ";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = this.crmCsbDbLinks.getCrmAllCsbLogDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, svcCode);
			pstmtX.setString(2, startDt);
			pstmtX.setString(3, overDt);
			pstmtX.setString(4, systemName);
			pstmtX.setString(5, dstCode);

			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5) + "|" + rs.getString(6) + "|"
						+ rs.getString(7) + "|" + rs.getString(8) + "|"
						+ rs.getString(9));
			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return list;

		}

	}

	public List<String> getArchiveTime(String start, String over) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT TO_CHAR(TABLE_2.ACCEPT_TIME,'YYYY-MM-DD'),\n"
				+ "COUNT(*) ,SUM(TABLE_2.AP),ROUND(SUM(TABLE_2.AP)/COUNT(*),4)*100 FROM (\n"
				+ "SELECT TABLE_1.*,CASE WHEN \n"
				+ "(TABLE_1.ARCHIVE_DATE-TABLE_1.DISPATCH_DT)*24*3600>300 THEN 0\n"
				+ "ELSE\n"
				+ "  1\n"
				+ "END  AP\n"
				+ " FROM\n"
				+ "(\n"
				+ "SELECT JK4.DB_NBR,NVL(JK4.ARCHIVE_DATE,SYSDATE)ARCHIVE_DATE ,JK4.DISPATCH_DT,JK4.ACCEPT_TIME\n"
				+ "FROM JK_TABLE_EASY_4G JK4 WHERE JK4.ACCEPT_TIME>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND JK4.ACCEPT_TIME<TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND JK4.DISPATCH_DT IS NOT NULL\n"
				+ "AND JK4.INNER_CUST_ORDER_TYPE_CD<>'4') TABLE_1 ) TABLE_2\n"
				+ "GROUP BY TO_CHAR(TABLE_2.ACCEPT_TIME,'YYYY-MM-DD')";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = crm4GDbLinks.getReadDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, start);
			pstmtX.setString(2, over);

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

	public List<String> getArchiveProvinceOnlyGD(String start, String over) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT TABLE_2.DB_NBR,\n"
				+ "(SELECT DN.PROVINCE_NAME FROM DB_NBR DN WHERE DN.DB_NBR=TABLE_2.DB_NBR) PROVINCE_NAME\n"
				+ ",COUNT(*) ,SUM(TABLE_2.AP),ROUND(SUM(TABLE_2.AP)/COUNT(*),4)*100 FROM (\n"
				+ "SELECT TABLE_1.*,CASE WHEN \n"
				+ "(TABLE_1.ARCHIVE_DATE-TABLE_1.DISPATCH_DT)*24*3600>300 THEN 0\n"
				+ "ELSE\n"
				+ "  1\n"
				+ "END  AP\n"
				+ " FROM\n"
				+ "(\n"
				+ "SELECT JK4.DB_NBR,NVL(JK4.ARCHIVE_DATE,SYSDATE)ARCHIVE_DATE ,JK4.DISPATCH_DT,JK4.ACCEPT_TIME\n"
				+ "FROM JK_TABLE_EASY_4G JK4 WHERE JK4.ACCEPT_TIME>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND JK4.ACCEPT_TIME<TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND JK4.STAFF_ID='2' AND JK4.DB_NBR='1' AND JK4.DISPATCH_DT IS NOT NULL\n"
				+ "AND JK4.INNER_CUST_ORDER_TYPE_CD<>'4') TABLE_1 ) TABLE_2\n"
				+ "GROUP BY TABLE_2.DB_NBR";

		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = crm4GDbLinks.getReadDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, start);
			pstmtX.setString(2, over);

			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(2) + "|" + rs.getString(3) + "|"
						+ rs.getString(4) + "|" + rs.getString(5));
			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return list;

		}

	}

	public List<String> getCSBGETDETAIL(String dstSysCode, String startDt,
			String overDt, String csbCode, String sysName) {

		List<String> list = new ArrayList<String>();
		String sql = "";
		sql = "SELECT SED.SRC_TRAN_ID,SED.SRC_SYS_CODE,SED.DST_SYS_CODE,SED.CSBPERIODFWD,SED.PERIOD,SED.CSBPERIODREQ,"
				+ "TO_CHAR(SED.CREATE_TIME,'YYYY-MM-DD HH24:MI:SS'),SED.RESPONSE_CODE\n"
				+ " FROM xiebc.svc_error_detail sed where SED.CREATE_TIME>TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND SED.CREATE_TIME<TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND SED.SVC_CODE=?\n"
				+ "AND SED.CSB_NAME=?\n"
				+ "AND SED.DST_SYS_CODE=?";

		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;
		try {
			jdbc_Connect_Oracle = crmCsbDbLinks.getCrmAllCsbLogDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(5, dstSysCode);
			pstmtX.setString(4, sysName);
			pstmtX.setString(3, csbCode);
			pstmtX.setString(1, startDt);
			pstmtX.setString(2, overDt);

			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				// SVC.SRC_TRAN_ID,SVC.SRC_SYS_CODE,SVC.DST_SYS_CODE,SVC.CSBPERIODFWD,SVC.PERIOD,SVC.CSBPERIODREQ,SVC.CREATE_TIME,SVC.RESPONSE_CODE
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5) + "|" + rs.getString(6) + "|"
						+ rs.getString(7) + "|" + rs.getString(8));
			}
			System.out.println("dstSysCode=" + dstSysCode + " csbCode="
					+ csbCode + " startDt=" + startDt + " overDt=" + overDt);
			pstmtX.close();
			jdbc_Connect_Oracle.close();
		} catch (java.lang.OutOfMemoryError e) {
			try {
				jdbc_Connect_Oracle.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		catch (SQLException e) {
			try {
				jdbc_Connect_Oracle.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
		return list;
	}

	public List<String> getEssWayErrorArchiveInThreeHours(String dbNbr) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT  /*+parallel(4)*/   JKKH.CUST_ORDER_ID  ,JKKH.ACCESS_NBR,JKKH.CHANNEL_ID,TO_CHAR(JKKH.ACCEPT_TIME,'YYYY-MM-DD HH24:MI:SS'),\n"
				+ "                   TO_CHAR(JKKH.DISPATCH_DT,'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(JKKH.ARCHIVE_DATE,'YYYY-MM-DD HH24:MI:SS')  FROM TEMP_JKKH\n"
				+ "                   JKKH  WHERE JKKH .CHANNEL_ID   IN (\n"
				+ "                   select ch.channel_id from  crm_lte.channel ch\n"
				+ "                                 where ch.common_region_id='8100000'\n"
				+ "                                  and channel_type_cd like '12%'\n"
				+ "                          and channel_name not like '%测试%'\n"
				+ "                         and status_cd='1000'\n"
				+ "                                 and  ch.channel_id <100100000)\n"
				+ "                   AND (JKKH.ARCHIVE_DATE-JKKH.DISPATCH_DT)*3600*24<3600*3\n"
				+ "                   AND JKKH.CUST_ORDER_ID IN (SELECT ERR.CUST_ORDER_ID FROM XIEBC.ERROR_INTF_CO_ARCHIVE_MSG_HIS ERR WHERE ERR.CUST_ORDER_ID=JKKH.CUST_ORDER_ID AND ERR.MSG_CONTENT NOT LIKE '%300000%')\n"
				+ "                and JKKH.ACCESS_NBR NOT LIKE '149%' AND JKKH.INNER_CUST_ORDER_TYPE_CD<>'4'\n"
				+ "                AND JKKH.DB_NBR=?";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = crm4GDbLinks.getReadDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, dbNbr);
			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5) + "|" + rs.getString(6));
			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return list;

		}
	}

	public List<String> getRealWayErrorArchiveInThreeDays(String dbNbr) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT  /*+parallel(4)*/   JKKH.CUST_ORDER_ID  ,JKKH.ACCESS_NBR,JKKH.CHANNEL_ID,TO_CHAR(JKKH.ACCEPT_TIME,'YYYY-MM-DD HH24:MI:SS'),\n"
				+ "                        TO_CHAR(JKKH.DISPATCH_DT,'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(JKKH.ARCHIVE_DATE,'YYYY-MM-DD HH24:MI:SS')  FROM TEMP_JKKH\n"
				+ "                        JKKH  WHERE JKKH .CHANNEL_ID NOT IN (\n"
				+ "                        select ch.channel_id from  crm_lte.channel ch\n"
				+ "                                      where ch.common_region_id='8100000'\n"
				+ "                                       and channel_type_cd like '12%'\n"
				+ "                               and channel_name not like '%测试%'\n"
				+ "                              and status_cd='1000'\n"
				+ "                                      and  ch.channel_id <100100000)\n"
				+ "                        AND (JKKH.ARCHIVE_DATE-JKKH.DISPATCH_DT)*3600*24<86400*3\n"
				+ "                        AND JKKH.CUST_ORDER_ID IN (SELECT ERR.CUST_ORDER_ID FROM XIEBC.ERROR_INTF_CO_ARCHIVE_MSG_HIS ERR WHERE ERR.CUST_ORDER_ID=JKKH.CUST_ORDER_ID AND ERR.MSG_CONTENT NOT LIKE '%300000%')\n"
				+ "                     and JKKH.ACCESS_NBR NOT LIKE '149%' AND JKKH.INNER_CUST_ORDER_TYPE_CD<>'4'\n"
				+ "                     AND JKKH.DB_NBR=?";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = crm4GDbLinks.getReadDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, dbNbr);
			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5) + "|" + rs.getString(6));
			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return list;

		}

	}

	public List<String> getRealWayUnArchiveInThreeDays(String dbNbr) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT  /*+parallel(4)*/  JKKH.Cust_Order_Id,to_char(jkkh.accept_time,'yyyy-mm-dd hh24:mi:ss') accept_time,\n"
				+ "to_char(jkkh.dispatch_dt,'yyyy-mm-dd hh24:mi:ss') dispatch_dt,to_char(jkkh.archive_date,'yyyy-mm-dd hh24:mi:ss') archive_date,\n"
				+ "jkkh.staff_id,jkkh.channel_id FROM TEMP_JKKH\n"
				+ "JKKH  WHERE JKKH .CHANNEL_ID NOT IN (\n"
				+ "select ch.channel_id from  crm_lte.channel ch\n"
				+ "                   where ch.common_region_id='8100000'\n"
				+ "                   and channel_type_cd like '12%'\n"
				+ "                   and channel_name not like '%测试%'\n"
				+ "                  and status_cd='1000'\n"
				+ "                   and  ch.channel_id <100100000)\n"
				+ "AND (JKKH.ARCHIVE_DATE-JKKH.DISPATCH_DT)*3600*24>=86400*3\n"
				+ "AND JKKH.INNER_CUST_ORDER_TYPE_CD<>4\n"
				+ "AND JKKH.ACCESS_NBR NOT LIKE '149%'\n" + "AND JKKH.DB_NBR=?";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = crm4GDbLinks.getReadDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, dbNbr);
			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5) + "|" + rs.getString(6));
			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return list;

		}
	}

	public List<String> getEssWayUnArchiveInThreeHours(String dbNbr) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT  /*+parallel(4)*/  JKKH.Cust_Order_Id,to_char(jkkh.accept_time,'yyyy-mm-dd hh24:mi:ss') accept_time,\n"
				+ "to_char(jkkh.dispatch_dt,'yyyy-mm-dd hh24:mi:ss') dispatch_dt,to_char(jkkh.archive_date,'yyyy-mm-dd hh24:mi:ss') archive_date,\n"
				+ "jkkh.staff_id,jkkh.channel_id FROM TEMP_JKKH\n"
				+ "JKKH  WHERE JKKH .CHANNEL_ID  IN (\n"
				+ "select ch.channel_id from  crm_lte.channel ch\n"
				+ "                   where ch.common_region_id='8100000'\n"
				+ "                   and channel_type_cd like '12%'\n"
				+ "                   and channel_name not like '%测试%'\n"
				+ "                  and status_cd='1000'\n"
				+ "                   and  ch.channel_id <100100000)\n"
				+ "AND (JKKH.ARCHIVE_DATE-JKKH.DISPATCH_DT)*3600*24>=3600*3\n"
				+ "AND JKKH.INNER_CUST_ORDER_TYPE_CD<>4\n"
				+ "AND JKKH.ACCESS_NBR NOT LIKE '149%'\n" + "AND JKKH.DB_NBR=?";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = crm4GDbLinks.getReadDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, dbNbr);
			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + "|" + rs.getString(2) + "|"
						+ rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5) + "|" + rs.getString(6));
			}
			pstmtX.close();
			jdbc_Connect_Oracle.close();
			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return list;

		}
	}

	public List<String> getArchiveProvince(String start, String over) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT TABLE_2.DB_NBR,\n"
				+ "(SELECT DN.PROVINCE_NAME FROM DB_NBR DN WHERE DN.DB_NBR=TABLE_2.DB_NBR) PROVINCE_NAME\n"
				+ ",COUNT(*) ,SUM(TABLE_2.AP),ROUND(SUM(TABLE_2.AP)/COUNT(*),4)*100 FROM (\n"
				+ "SELECT TABLE_1.*,CASE WHEN \n"
				+ "(TABLE_1.ARCHIVE_DATE-TABLE_1.DISPATCH_DT)*24*3600>300 THEN 0\n"
				+ "ELSE\n"
				+ "  1\n"
				+ "END  AP\n"
				+ " FROM\n"
				+ "(\n"
				+ "SELECT JK4.DB_NBR,NVL(JK4.ARCHIVE_DATE,SYSDATE)ARCHIVE_DATE ,JK4.DISPATCH_DT,JK4.ACCEPT_TIME\n"
				+ "FROM JK_TABLE_EASY_4G JK4 WHERE JK4.ACCEPT_TIME>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND JK4.ACCEPT_TIME<TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')\n"
				+ "AND JK4.DISPATCH_DT IS NOT NULL\n"
				+ "AND JK4.INNER_CUST_ORDER_TYPE_CD<>'4') TABLE_1 ) TABLE_2\n"
				+ "GROUP BY TABLE_2.DB_NBR";
		Connection jdbc_Connect_Oracle = null;
		PreparedStatement pstmtX = null;

		try {

			jdbc_Connect_Oracle = crm4GDbLinks.getReadDbConnection();
			pstmtX = jdbc_Connect_Oracle.prepareStatement(sql);
			pstmtX.setString(1, start);
			pstmtX.setString(2, over);

			ResultSet rs = pstmtX.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(2) + "|" + rs.getString(3) + "|"
						+ rs.getString(4) + "|" + rs.getString(5));
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
