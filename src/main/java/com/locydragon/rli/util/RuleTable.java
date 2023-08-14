package com.locydragon.rli.util;

import java.util.ArrayList;
 
public class RuleTable {
	
	//此表可添加
	//操作符
	static public char[] op  = {
			'+',
			'-',
			'*',
			'/',
			'(',
			')',
			'#'
	};
	
	//优先关系模拟表
	//此表可添加
	private int[][] re = {
			{1,1,-1,-1,-1,1,1},
			{1,1,-1,-1,-1,1,1},
			{1,1,1,1,-1,1,1},
			{1,1,1,1,-1,1,1},
			{-1,-1,-1,-1,-1,0,2},
			{1,1,1,1,-2,1,1},
			{-1,-1,-1,-1,-1,2,0},
	};
	//申明一个运算优先规则列表
	private ArrayList<Rule> ruleList = new ArrayList<>();
	
	//获取运算优先规则列表
	public ArrayList<Rule> getRuleList() {
		return this.ruleList;
	}
	//生成运算优先关系表
	public RuleTable() {
		
		for(int i = 0; i < op.length; i++) {
			for(int j = 0; j < op.length; j++) {
				Rule rule = new Rule();
				rule.ch1 = op[i];
				rule.ch2 = op[j];
				if(re[i][j] == 1) {
					rule.equ = '>';
				}else if(re[i][j] == -1) {
					rule.equ = '<';
				}else if(re[i][j] == 0) {
					rule.equ = '=';
				}else {
					rule.equ  = '@';
				}
				
				ruleList.add(rule);
			}
		}
	}
	//今设 第一个操作符为a 第二个为 b
	//当第一个为 运算符第二个位分割符
	//则运算符小于分割符
	//如果第一个为分隔符第二个为运算符
	//则运算符大于分隔符
}
//优先规则关系表元素
class Rule {
	char ch1 = 0;
	char ch2 = 0;
	char equ = 0;
}
