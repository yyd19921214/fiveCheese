package com.cheese.stateModel;

import com.cheese.util.Util;


/*
 * 该类用来表示棋盘的状态包括已有的棋子的情况（位置、颜色），空位置的情况以及下一步是玩家还是计算机落子
 * Represent the state of the board, including where those pieces are and 
 * who is the next player
 */
public class BoardState {
	// indicate next player
	// black为真表示下一步是玩家落子
	private boolean black;
	
	// store the pieces on board
	private int data[][];
	
	//初始化游戏状态
	public BoardState() {
		data = new int[Util.column][Util.row];
		// initialize the data: set the board full with empty pieces
		// set black next
		for(int i=0;i<Util.column;i++) {
			for(int j=0;j<Util.row;j++) {
				setPiece(i,j,Util.emptyPiece);
			}
		}
		//游戏一开始由黑色选手先下即玩家先
		black = true;
	}
	
	// set one piece on the board according to the coordinate
	// 落子并且转换下一步的控制权
	public void setPiece(int x, int y, int piece) {
		data[x][y] = piece;
		alternate();
	}
	
	public int getPiece(int x, int y) {
		if(x<0||x>=Util.row||y<0||y>=Util.column)
			return Util.outPiece;
		return data[x][y];
	}
	
	// switch to next player
	private void alternate() {
		black = !black;
	}
	
	public boolean isNextBlack() {
		return black; 
	}
	
	//重写clone函数，用以复制一份现在的状态，便于在递归调用时传递
	public BoardState clone() {
		BoardState newBS = new BoardState();
		for(int i=0;i<Util.column;i++) {
			for(int j=0;j<Util.row;j++) {
				newBS.data[i][j] = this.data[i][j];
			}
		}
		return newBS;
	}
}
