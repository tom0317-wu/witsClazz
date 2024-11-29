package com.wits.clazz.module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.wits.clazz.utils.DBUtils;

public class BatchClazz {
	
	/**
	 * 連線 DB 初始化資訊
	 */
	Map<String, String> dbParams = new HashMap<String, String>() {
		{
			put("access_DRIVER","net.ucanaccess.jdbc.UcanaccessDriver");
			put("access_URL", "jdbc:ucanaccess://C:/Programs/Java1_Report.accdb;showSchema=true");
			put("access_USER", "");
			put("access_PASS", "");
			put("access_SQL",
					"SELECT jira_no, prog_id, prog_name, pg_name from java1_rpt where jira_no = 'LIPGMASGMT-10192'");
			
			put("mySQL_DRIVER","com.mysql.cj.jdbc.Driver");
			put("mySQL_URL", "jdbc:mysql://localhost:3306/world");
			put("mySQL_USER", "root");
			put("mySQL_PASS", "11111111");
			put("mySQL_SQL", "select * from city where countryCode = 'AFG'");
		}
	};

	/**
	 * 批次更新 Table 資料
	 * 測試更新 MySQL.city 的資料
	 * 把 countryCode 字首為 A 的 population 都 + 20
	 * @throws Exception
	 */
	public void doBatchUpdateData() throws Exception{
		
		DBUtils dbUtils = new DBUtils();
		
		Connection con = null;
		try {
			con = dbUtils.getConnectionDirect("mySQL", dbParams);
			
		} catch (Exception e) {
			System.out.println("取得資料庫連線發生異常.., e:" + e.getMessage());
			throw new Exception("取得資料庫連線發生異常.., e:" + e.getMessage(), e);
		}
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append("select countrycode ");
			sb.append("from city ");
			sb.append("where substr(countrycode,1,1) = 'A' ");
			sb.append("group by countrycode");
			
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(sb.toString());
			
			sb.setLength(0);
			sb.append("update city ");
			sb.append("set population = population - 20 ");
			sb.append("where countrycode = ?");
			
			PreparedStatement pstmt = con.prepareStatement(sb.toString());
			
			// 設定 autoCommit 為 false
			con.setAutoCommit(false);
			while (result.next()) {
				pstmt.setString(1, result.getString(1));
				pstmt.addBatch();
			}
			
			int[] executeResult = pstmt.executeBatch();
			
			for (int i : executeResult) {
				System.out.println("eachResult:" + i);
			}
			
			con.commit();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			con.rollback();
			throw e;
		} finally {
			if (null != con) {
				con.close();
			}
		}
	}
}
