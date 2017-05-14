package com.cheese.board;

import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.cheese.clickEvent.GameMouseEventListener;
import com.cheese.stateModel.BoardState;
import com.cheese.util.Util;



/*
 * 用来绘制窗口和棋盘
 */
public class Board extends JFrame {

	//代表四个方位的坐标用来帮助绘制棋盘
	private int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

	private BoardState boardState;
	
	
	public Board(BoardState boardState) {
		this.boardState = boardState;
	}
	
	//baseX baseY代表棋盘的初始坐标 注意需要和窗口边界分开一定的间隔
	public final int baseX = (int)(Util.interval * 1.5);
	public final int baseY = (int)(Util.interval * 1.5);
	public void showUI() {
		//这里的size表示窗口的大小
		this.setSize(Util.interval * (Util.row+3), Util.interval * (Util.column+3));
		this.setTitle(Util.gameName);
		java.awt.FlowLayout fl = new java.awt.FlowLayout();
		this.setLayout(fl);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.getContentPane().setBackground(Color.gray);
		//添加一个事件触发器
		addMouseListener(new GameMouseEventListener(this, boardState));
		try {
			Thread.sleep(1000);
		} catch (Exception ef) {
			ef.printStackTrace();
		}
		Graphics g = this.getGraphics();
		//调用doPaint方法进行绘制
		doPaint(g);
	}
	
	/*
	 * 本函数绘制棋盘和上面的子
	 */
	private void doPaint(Graphics g) {
		// 棋盘的初始坐标
		x1 = baseX;
		y1 = baseY;
		//绘制横向线段
		x2 = x1 + Util.interval * (Util.column - 1);
		for (int i = 0; i < Util.row; i++) {
			g.drawLine(x1, y1, x2, y1);
			y1 += Util.interval;
		}
		x1 = baseX;
		y1 = baseY;
		//绘制纵向线段
		y2 = x1 + Util.interval * (Util.row - 1);
		for (int j = 0; j < Util.column; j++) {
			g.drawLine(x1, y1, x1, y2);
			x1 += Util.interval;
		}
		// 放置棋盘上各个棋子
		for(int i=0;i<Util.row;i++) {
			for(int j=0;j<Util.column;j++) {
				paintPiece(i,j,this.boardState.getPiece(i, j));
			}
		}
	}
	
	// 画一颗棋子
	public void paintPiece(int row, int column, int piece) {
		//若是空子则直接返回
		if(piece == Util.emptyPiece)
			return;
		URL url;
		//若是黑子则将url设置为黑色棋子的图片
		if(piece == Util.blackPiece) {
			url=this.getClass().getResource("/AI/game/resource/B.png");
		} else {
			//若是白子则将url设置为白色棋子的图片
			url=this.getClass().getResource("/AI/game/resource/W.png");
		}
		ImageIcon pieceImage=new ImageIcon(url);
		this.getGraphics().drawImage(pieceImage.getImage(),(int) (baseX+(row-0.5)*Util.interval)
				,(int)(baseY+(column-0.5)*Util.interval),40,40,this);
	}

	//用来显示棋局的结果
	public void showResult(String result) {
		if(JOptionPane.showConfirmDialog(null, result)==JOptionPane.OK_OPTION) {
			System.exit(0);
		} else {
			
		}
	}

	
	public void paint(Graphics g) {
		super.paint(g);
		doPaint(g);
	}
}
