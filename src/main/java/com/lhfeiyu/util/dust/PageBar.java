package com.lhfeiyu.util.dust;

import org.springframework.ui.ModelMap;

/**
 * 页面分页栏类
 */
public class PageBar {

	public static ModelMap getPageBarNum(ModelMap modelMap,Integer page){
		int pageNum1 = 1;
		int pageNum2 = 2;
		int pageNum3 = 3;
		int pageNum4 = 4;
		int pageNum5 = 5;
		if(page > 5){
			int pagePos = page%5;
			if(pagePos == 0){
				pageNum1 = page - 4;
				pageNum2 = page - 3;
				pageNum3 = page - 2;
				pageNum4 = page - 1;
				pageNum5 = page - 0;
			}else{
				pageNum1 = page - pagePos + 1;
				pageNum2 = page - pagePos + 2;
				pageNum3 = page - pagePos + 3;
				pageNum4 = page - pagePos + 4;
				pageNum5 = page - pagePos + 5;
			}
		}
		int pageNum_pre = page-1;
		int pageNum_next = page+1;
		if(pageNum_pre<=0)pageNum_pre = 1;
		modelMap.put("pageNum1", pageNum1);
		modelMap.put("pageNum2", pageNum2);
		modelMap.put("pageNum3", pageNum3);
		modelMap.put("pageNum4", pageNum4);
		modelMap.put("pageNum5", pageNum5);
		modelMap.put("pageNum_pre", pageNum_pre);
		modelMap.put("pageNum_next", pageNum_next);
		return modelMap;
	}
	
	public static ModelMap getPageBarNumTen(ModelMap modelMap,Integer page){
		int pageNum1 = 1;
		int pageNum2 = 2;
		int pageNum3 = 3;
		int pageNum4 = 4;
		int pageNum5 = 5;
		int pageNum6 = 6;
		int pageNum7 = 7;
		int pageNum8 = 8;
		int pageNum9 = 9;
		int pageNum10 = 10;
		if(page > 10){
			int pagePos = page%5;
			if(pagePos == 0){
				pageNum1 = page - 9;
				pageNum2 = page - 8;
				pageNum3 = page - 7;
				pageNum4 = page - 6;
				pageNum5 = page - 5;
				pageNum6 = page - 4;
				pageNum7 = page - 3;
				pageNum8 = page - 2;
				pageNum9 = page - 1;
				pageNum10 = page - 0;
			}else{
				pageNum1 = page - pagePos + 1;
				pageNum2 = page - pagePos + 2;
				pageNum3 = page - pagePos + 3;
				pageNum4 = page - pagePos + 4;
				pageNum5 = page - pagePos + 5;
				pageNum6 = page - pagePos + 6;
				pageNum7 = page - pagePos + 7;
				pageNum8 = page - pagePos + 8;
				pageNum9 = page - pagePos + 9;
				pageNum10 = page - pagePos + 10;
			}
		}
		int pageNum_pre = page-1;
		int pageNum_next = page+1;
		if(pageNum_pre<=0)pageNum_pre = 1;
		modelMap.put("pageNum1", pageNum1);
		modelMap.put("pageNum2", pageNum2);
		modelMap.put("pageNum3", pageNum3);
		modelMap.put("pageNum4", pageNum4);
		modelMap.put("pageNum5", pageNum5);
		modelMap.put("pageNum6", pageNum6);
		modelMap.put("pageNum7", pageNum7);
		modelMap.put("pageNum8", pageNum8);
		modelMap.put("pageNum9", pageNum9);
		modelMap.put("pageNum10", pageNum10);
		modelMap.put("pageNum_pre", pageNum_pre);
		modelMap.put("pageNum_next", pageNum_next);
		return modelMap;
	}

}
