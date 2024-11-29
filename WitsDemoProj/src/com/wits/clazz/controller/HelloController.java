package com.wits.clazz.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wits.clazz.utils.DBUtils;

@Controller
public class HelloController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String helloWorld(Model model) {
		model.addAttribute("Hello", "AAA123");

		Connection con = null;
		try {
			con = DBUtils.getConnection("ACCESS");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		DBUtils.closeConnection(con);

		return "HelloWorld";
	}

	@RequestMapping(value = "/Hello", method = RequestMethod.GET)
	public String helloWits(Model model) {
		model.addAttribute("Hello", "WistronITS");

		return "HelloWits";
	}
	
	@RequestMapping(value = "/Java1Rpt", method = RequestMethod.GET)
	public String java1Rpt(Model model) {
		//model.addAttribute("jiraNo", "LIPGMASGMT-10192");

		return "Java1Rpt";
	}
	
	@RequestMapping(value = "/Java1RptRst", method = RequestMethod.POST)
	public String java1RptRst(String jiraNo, String empId, Model model) {
		model.addAttribute("empId", empId);
		model.addAttribute("jiraNo", jiraNo);
		
		try {
			// 取得 access 的連線
			Connection con = DBUtils.getConnection("access");
			
			// 檢核參數
			if (StringUtils.isBlank(empId)) {
				model.addAttribute("errMsg", "員工 ID為必填條件..!");
				throw new Exception("error");
			}
			
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("SELECT r.* ");
			sqlSb.append("from java1_rpt r ");
			sqlSb.append("inner join java1_emp_info e ");
			sqlSb.append("on e.emp_name = r.pg_name ");
			sqlSb.append("where emp_id = '");
			sqlSb.append(empId);
			sqlSb.append("' ");
			
			// sqlInjection ' or '1'='1
			if (StringUtils.isNotBlank(jiraNo)) {
				sqlSb.append(" and jira_no = '");
				sqlSb.append(jiraNo);
				sqlSb.append("' ");
			}
			
			List<Map<String, String>> rtnList = DBUtils.queryDataByStatement(sqlSb.toString(), con);
			
			for (Map<String, String> maps : rtnList) {
				System.out.println(maps.get("JIRA_NO"));
				System.out.println(maps.get("PROG_ID"));
				System.out.println(maps.get("PROG_NAME"));
				System.out.println(maps.get("PG_NAME"));
			}
			
			model.addAttribute("rptData", rtnList);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "Java1Rpt";
	}

}
