package com.cheese.clickEvent;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import com.cheese.AI.Scanner;
import com.cheese.board.Board;
import com.cheese.stateModel.BoardState;
import com.cheese.util.Util;



/*
 * 该类用来监听点击事件当用户点击某个点时添加一枚棋子
 */
public class GameMouseEventListener implements MouseListener{

	Board board;
	
	BoardState boardState;
	
	public GameMouseEventListener(Board b, BoardState boardState) {
		this.board = b;
		this.boardState = boardState;
	}
	
	@Override
	/*
	 * 当落子时在棋盘上添加棋子并且更新棋局状态
	 */
	public void mouseClicked(MouseEvent event) {
		// 如果是机器回合那么直接返回
		if(Util.turn==Util.machinePiece) {
			return;
		}
		double x = event.getPoint().getX();
		double y = event.getPoint().getY();
		int positionX = (int) Math.floor((x-this.board.baseX+0.5*Util.interval)/Util.interval);
		int positionY = (int) Math.floor((y-this.board.baseY+0.5*Util.interval)/Util.interval);
		//如果不是空位置就直接返回
		if(positionX<0 || positionY<0 || this.boardState.getPiece(positionX, positionY)!=Util.emptyPiece)
			return;

		// 如果是空位置就落子并且更新
		System.out.println(this.boardState.getPiece(positionX, positionY));
		if(this.boardState.getPiece(positionX, positionY) == Util.emptyPiece) {
			if(this.boardState.isNextBlack()) {
				//如果是轮到玩家下手就落黑子并且更新
				this.boardState.setPiece(positionX, positionY, Util.blackPiece);
				this.board.paintPiece(positionX, positionY, Util.blackPiece);
			} else {
				//如果是轮到计算机下手就落白子并且更新
				this.boardState.setPiece(positionX, positionY, Util.whitePiece);
				this.board.paintPiece(positionX, positionY, Util.whitePiece);
			}
		}
		//转换
		Util.turn *= -1;
		
		Scanner scan = new Scanner();
		//判断是否赢棋
		int result = scan.isWin(this.boardState);
		if(result==100) {
			// 平局
			this.board.showResult("It is a draw");
		} else if(result==Util.whitePiece) {
			this.board.showResult("哈哈，我就说你赢不了我!");
		} else if(result==Util.blackPiece) {
			this.board.showResult("你等着，我更新了AI和你一战");
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
