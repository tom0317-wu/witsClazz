package com.wits.clazz.module;

import org.junit.Test;

public class BatchClazzTest {

	@Test
	public void testDoBatchUpdateData() {
		try {
			System.out.println("批次開始執行..!");
			new BatchClazz().doBatchUpdateData();
			System.out.println("批次執行完成..!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
