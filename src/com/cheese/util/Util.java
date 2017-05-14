package com.cheese.util;

/*
 * Some common constant
 */
public class Util {
	public static final int row = 12;//默认12行
	
	public static final int column = 12;//默认12列
	
	public static final int interval = 40;//该变量表示棋盘中各条直线之间的间隔
	
	public static final String gameName = "五子棋";
	
	public static final int blackPiece = 1;//1代表黑色棋子
	
	public static final int whitePiece = -1;//-1代表白色棋子
	
	public static final int outPiece = -20000;
	
	public static final int emptyPiece = 0;//0表示该位置是空的
	
	public static final int baseX = 60;
	
	public static final int baseY = 60;
	
	public static final int liveFive = 1000000;//活5情况的评分
	
	public static final int liveFour = 100000;//活4情况的评分
	
	public static final int semiFour = 10000;//死4情况的评分
	
	public static final int liveThree = 1000;
	
	public static final int semiThree = 100;
	
	public static final int liveTwo = 10;
	
	public static int machinePiece=Util.whitePiece;//默认计算机执白
	
	public static int win=0; 
	
	public static int turn=blackPiece;//默认下一步由黑棋开始（即玩家先下）

	public static int MaximumDepth = 2;
}
