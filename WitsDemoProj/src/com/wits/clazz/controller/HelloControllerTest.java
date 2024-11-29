package com.wits.clazz.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;

public class HelloControllerTest {
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

	@Test
	public void dbConnectNqueryTest() {
		try {
			
			// 測試 try-with-resourcr 連線 DB & 查資料
			this.testQueryWithResource("access");
			this.testQueryWithResource("mySQL");
			
			this.testQueryAccessDB_2("access");
			this.testQueryAccessDB_2("mySQL");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @param dbKind
	 */
	private void testQueryWithResource(String dbKind) {
		// Open a connection
		try (Connection conn = DriverManager.getConnection(
				dbParams.get(dbKind + "_URL"),
				dbParams.get(dbKind + "_USER"), 
				dbParams.get(dbKind + "_PASS"));
				) {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(dbParams.get(dbKind + "_SQL"));

			ResultSetMetaData rsmd = rs.getMetaData();
			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					Object obj = rs.getObject(i);
					System.out.println(rsmd.getColumnName(i) + ":" + ObjectUtils.toString(obj, ""));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 測試資料庫取得連線 & 關閉
	 */
	private void testQueryAccessDB_2(String dbKind) {
		Connection conn = null;
		try {
			// 1.載入 JDBC 驅動程式
			Class.forName(dbParams.get(dbKind + "_DRIVER"));

			// 2.提供JDBC URL
			String dbUrl = dbParams.get(dbKind + "_URL");
			String userId = dbParams.get(dbKind + "_USER");
			String passWord = dbParams.get(dbKind + "_PASS");

			// 3.取得Connection
			conn = DriverManager.getConnection(dbUrl, userId, passWord);

			if (!conn.isClosed()) {
				System.out.println("資料庫連線成功");
			}

		} catch (ClassNotFoundException e) {
			System.out.println("找不到驅動程式類別");

		} catch (SQLException se) {
			System.out.println(se.getMessage());

		} finally {
			if (null != conn) {
				try {
					conn.close();

					System.out.println(dbKind + " 資料庫關閉成功!!");

				} catch (SQLException e) {
					System.out.println(String.format("關閉 %1$s 資料庫發生異常, e:%2$s", dbKind, e.getMessage()));
				}
			}
		}
	}

}
