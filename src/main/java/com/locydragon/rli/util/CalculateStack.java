package com.locydragon.rli.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;
 
 
public class CalculateStack {
	static private CalculateStack cl = new CalculateStack();
	//申明一个存放数字元素的栈
	private Stack<String> num = new Stack<>();
	//声明一个存放操作符的栈
	private Stack<Character> opStack = new Stack<>();
	//申明一个优先权规则关系表
	private RuleTable ruleTable = new RuleTable();
	
	//运算简单表达式 
	//测试通过
	//可添加运算规则
	private double getCalculateNumber(double num1,Character op,double num2) {
		
		double res = 0;
		switch(op.charValue()) {
		
			case '+':{
				res = num1 + num2;
			}break;
			
			case '-':{	
				res = num1 - num2;
			}break;
			case '*':{
				
				res = num1 * num2;
			}break;
			case '/':{
				
				res = num1 / num2;
			}break;
 
		}
		return res;
	}
	
	//得到指定的俩个操作符的优先关系
	/**
	 * @return 当返回@时为无效字符输入
	 * */
	private char serchOp(char ch, char top) {
		
		//System.out.println("ch:"+ch+"   top:"+top);
		for(Rule rule:this.ruleTable.getRuleList()) {
			if(top == rule.ch1&&rule.ch2 == ch) {
				//System.out.println("从表中找到：关系为top" + rule.equ +"ch");
				return rule.equ;
			}
		}
		return '@';
	}

	public String Calculate(String strSource,int Accuracy) {
		boolean ret = true;
		
		Object[] ch1 = this.strToOpStr(strSource);
		//遍历表达式数组，逐个读入
		for(int i = 0; i < ch1.length; i++) {
			//判断是否是操作符
			if(ch1[i].toString().isEmpty())
				break;
		if(!this.isOperator(ch1[i].toString())) {
			//不是则如数字字符栈
			this.num.push(ch1[i].toString());
			
		}else {
				//System.out.println(ch);
				//从优先关系表中查找当前字符和操作符栈栈顶元素的运算优先关系
				switch(this.serchOp(ch1[i].toString().charAt(0),this.opStack.lastElement())){
					//如果ch优先顺序大于top 压入操作符栈
					case '<':{
						
						this.opStack.push(new Character(ch1[i].toString().charAt(0)));
						//System.out.println(ch1[i].charAt(0)+"压入操作符");
					}break;
					//如果ch优顺序小于top 取出 俩个操作数以及弹出操作符进行运算
					case '>': {
						//弹出俩个数字元素
						double num2 = Double.valueOf(this.num.pop());
		
						double num1 = Double.valueOf(this.num.pop());
						
						char op = this.opStack.pop();
						//System.out.println(ch1[i].charAt(0)+"弹出操作符");
						this.num.push(new Double(this.getCalculateNumber(num1, op, num2)).toString());
						//但前元素不进行操作，返回重新进行比较
						//栈顶元素运算，退栈
						i--;
					}break;
					case '@': {
						System.out.println("不合法的表达式");
						ret = false;
					}break;
					
					case '=': {
						//去括号，#号
						this.opStack.pop();
						//System.out.println(ch1[i].charAt(0)+"弹出操作符");
					}break;
				}
			}
		}
		//格式化结果
		NumberFormat nb = NumberFormat.getNumberInstance();
		nb.setMinimumFractionDigits(Accuracy);
		double res = Double.valueOf(this.num.pop());
		
		return ret?nb.format(res):null;
	}
	
	/**
	 * 此函数用于在表达式进入运算之前检查表达式的合法性
 * */
 private boolean CheckeStr(String strSource) {
		
		char[] chSource = strSource.toCharArray();
		//先检查括号的数量
		for(int i = 1; i < chSource.length; i++) {
			if((chSource[i-1] == '('||chSource[i-1] == ')')&&!this.isOperater(chSource[i])) {
				return false;
			}else if((chSource[i] == '('||chSource[i] == ')')&&!this.isOperater(chSource[i-1])) {
				return false;
			}
		}
		return true;
	}
	
	//初始化俩个栈
	public CalculateStack() {
		//操作数栈初始化栈顶为0
		this.num.push(new Integer(0).toString());
		//操作符栈初始化为#(结尾符)
		this.opStack.push(new Character('#'));
	}
	
	//判断字串是否为操作符
	private boolean isOperator(String str) {
		
		if(str.toCharArray().length == 1) {
				for(char ch:RuleTable.op) {
					if(ch == str.toCharArray()[0])
						return true;
				}
		}else if(str.isEmpty()){
				return false;
		}else {
			return false;
		}
		return false;
	}
	
//此方法实现把操作符和操作数解析为字符串数组
 private Object[] strToOpStr(String strSource) {
		int num = 0;
		boolean isOp = true; 
		String[] retStr = new String[100]; 
		
		char[] ch = strSource.toCharArray();
		for(char ch1:ch) {
			//起始为数字
			if(this.isOperater(ch1)&&num == 0&&!isOp) {
				num++;
				retStr[num++] = new String(new Character(ch1).toString());
				isOp = true;
			}else if(this.isOperater(ch1)&&num == 0&&isOp) {
				//以操作符起始
				retStr[num++] = new String(new Character(ch1).toString());
				isOp = true;
			}else if(this.isOperater(ch1)&&num != 0){
				//不是起始位置并且是操作符
				if(!isOp)//如果前一个不是操作符 增加num
					num++;
				retStr[num++] = new String(new Character(ch1).toString());
				isOp = true;
			}else if(!this.isOperater(ch1)&&num != 0){
				//当前数字并且不是起始位置
				if(retStr[num] == null)
					retStr[num] = new String(new Character(ch1).toString());
				else {
					retStr[num] += ch1;
				}
				
				isOp = false;
			}else if(!this.isOperater(ch1)&&num == 0) {
				
				if(isOp) {
					//System.out.println("opchar:" + ch1+"op:"+isOp+"num:"+num);
					retStr[num] = new String(new Character(ch1).toString());
					//System.out.println("opchar:" + ch1+"op:"+isOp+"num:"+num+"rerStr:"+retStr[num]);
				}else {
					retStr[num] += ch1;
					//System.out.println("opchar:" + ch1+"op:"+isOp+"num:"+0+"rerStr:"+retStr[0]);
				}
				isOp = false;
			}	
		}
		ArrayList<String> list = new ArrayList<>();
		for(int i = 0; i < retStr.length; i++) {
			if(retStr[i] != null) {
				list.add(retStr[i]);
			}
		}
		list.add(list.size(),"#");
		return list.toArray();
	}
	//判断一个字符是否为操作符
	private boolean isOperater(char ch) {
		
		for(char ch1:RuleTable.op) {
			if(ch == ch1)
				return true;
		}
		return false;
	}

	static public CalculateStack getCalculateInstance() {
		return  cl;
	}
}
