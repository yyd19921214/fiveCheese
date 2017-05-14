package com.cheese.AI;

import com.cheese.stateModel.BoardState;
import com.cheese.util.Util;

/*
 * 用来扫描整个棋盘给每个可能的位置打分
 */
public class Scanner {
	private BoardState boardState;
	
	public Scanner() {
		this.evalution = 0;
	}
	
	public void setBoardState(BoardState boardState) {
		this.boardState = boardState;
		this.evalution = 0;
	}
	
	
	int evalution;
	
	public int getEvaluation() {
		this.doScan();
		return evalution;
	}
	
	public void doScan() {
		// scan the row

		int live5Black=0;//黑子活5得情况
		int live5White=0;//白子活5得情况
		int live4Black=0;
		int live4White=0;
		int semi4Black=0;
		int semi4White=0;
		int live3Black=0;
		int live3White=0;
		int semi3Black=0;
		int semi3White=0;
		int live2Black=0;
		int live2White=0;
		//扫描每个位置
		for(int j=0;j<Util.row;j++) {
			for(int i=0;i<Util.column;i++) {
				// 获得活5的黑棋子的数目
				if(this.isLiveFive(i, j, Util.blackPiece)) {
//					System.out.println("black win 5");
					live5Black++;
				}
				if(this.isLiveFive(i, j, Util.whitePiece)) {
//					System.out.println("white win 5");
					live5White++;
				}
				// live four
				if(this.isLiveFour(i, j, Util.blackPiece)) {
//					System.out.println("black live 4");
					live4Black++;
				}
				if(this.isLiveFour(i, j, Util.whitePiece)) {
//					System.out.println("white live 4");
					live4White++;
				}
				// semi live four
				if(this.isSemiLiveFour(i, j, Util.blackPiece)) {
//					System.out.println("black semi 4");
					semi4Black++;
				}
				if(this.isSemiLiveFour(i, j, Util.whitePiece)) {
//					System.out.println("white semi 4");
					semi4White++;
				}
				
				// live three
				if(this.isLiveThree(i, j, Util.blackPiece)) {
//					System.out.println("black live three");
					live3Black++;
				}
				if(this.isLiveThree(i, j, Util.whitePiece)) {
//					System.out.println("white live three");
					live3White++;
				}
				
				//semi three
				if(this.isSemiThree(i,j,Util.blackPiece)) {
//					System.out.println("black semi three");
					semi3Black++;
				}
				if(this.isSemiThree(i,j,Util.whitePiece)) {
//					System.out.println("white semi three");
					semi3White++;
				}
				
				// live two
				if(this.isLiveTwo(i,j,Util.blackPiece)) {
//					System.out.println("black live two");
					live2Black++;
				}
				if(this.isLiveTwo(i, j, Util.whitePiece)) {
//					System.out.println("white live two");
					live2White++;
				}
			}
		}
		//评分函数是针对白子（也就是计算机方）来写的，该值越大说明对计算机越有利，值越小说明越不利
		this.evalution += (live5Black-live5White)*Util.liveFive;
		this.evalution += (live4Black-live4White)*Util.liveFour;
		this.evalution += (semi4Black-semi4White)*Util.semiFour;
		this.evalution += (live3Black-live3White)*Util.liveThree;
		this.evalution += (semi3Black-semi3White)*Util.semiThree;
		this.evalution += (live2Black-live2White)*Util.liveTwo;
		//将估计值取负数从而得出对计算机来说的估值Util.machinePiece=Util.whitePiece=-1
		this.evalution = Util.machinePiece * Util.blackPiece * this.evalution;
	}
	
	public int isWin(BoardState boardState) {
		this.boardState = boardState;
		for(int j=0;j<Util.row;j++) {
			for(int i=0;i<Util.column;i++) {
				// 判断是否赢棋
				if(this.isLiveFive(i, j, Util.blackPiece)) {
					return Util.blackPiece;//返回1代表黑色棋子赢了比赛
				}
				if(this.isLiveFive(i, j, Util.whitePiece)) {
					return Util.whitePiece;//返回-1代表白色棋子赢了比赛
				}
			}
		}
		
		boolean draw = true;
		for(int j=0;j<Util.row;j++) {
			for(int i=0;i<Util.column;i++) {
				//只有在每个位置都下满的情况下才会出现平局
				if(boardState.getPiece(i, j)==Util.emptyPiece) {
					draw = false;
				}
			}
		}
		if(draw) {
			return 100;//平局返回100
		} else {
			return 0;
		}
	}
	
	
	// determine if semi three
	private boolean isSemiThree(int x, int y, int piece) {
		// scan the column, up to down
		if(boardState.getPiece(x, y-1)!=piece && boardState.getPiece(x, y-1)!=Util.emptyPiece) {
			if((boardState.getPiece(x, y)+boardState.getPiece(x, y+1)+boardState.getPiece(x, y+2)+
					boardState.getPiece(x, y+3)+boardState.getPiece(x, y+4)==3*piece) && 
				(boardState.getPiece(x, y)*boardState.getPiece(x, y+1)*boardState.getPiece(x, y+2)*
					boardState.getPiece(x, y+3)*boardState.getPiece(x, y+4)==0)) {
				if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x, y+5)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		// scan the column, down to up
		if(boardState.getPiece(x, y+1)!=piece && boardState.getPiece(x, y+1)!=Util.emptyPiece) {
			if((boardState.getPiece(x, y)+boardState.getPiece(x, y-1)+boardState.getPiece(x, y-2)+
					boardState.getPiece(x, y-3)+boardState.getPiece(x, y-4)==3*piece) && 
				(boardState.getPiece(x, y)*boardState.getPiece(x, y-1)*boardState.getPiece(x, y-2)*
					boardState.getPiece(x, y-3)*boardState.getPiece(x, y-4)==0)) {
				if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x, y-5)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		
		// scan the row, left to right
		if(boardState.getPiece(x-1, y)!=piece && boardState.getPiece(x-1, y)!=Util.emptyPiece) {
			if((boardState.getPiece(x, y)+boardState.getPiece(x+1, y)+boardState.getPiece(x+2, y)+
					boardState.getPiece(x+3, y)+boardState.getPiece(x+4, y)==3*piece) && 
				(boardState.getPiece(x, y)*boardState.getPiece(x+1, y)*boardState.getPiece(x+2, y)*
					boardState.getPiece(x+3, y)*boardState.getPiece(x+4, y)==0)) {
				if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x+5, y)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		

		// scan the row, right to left
		if(boardState.getPiece(x+1, y)!=piece && boardState.getPiece(x+1, y)!=Util.emptyPiece) {
			if((boardState.getPiece(x, y)+boardState.getPiece(x-1, y)+boardState.getPiece(x-2, y)+
					boardState.getPiece(x-3, y)+boardState.getPiece(x-4, y)==3*piece) && 
				(boardState.getPiece(x, y)*boardState.getPiece(x-1, y)*boardState.getPiece(x-2, y)*
					boardState.getPiece(x-3, y)*boardState.getPiece(x-4, y)==0)) {
				if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x-5, y)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		// scan the diagnose, right up to left down
		if(boardState.getPiece(x+1, y-1)!=piece && boardState.getPiece(x+1, y-1)!=Util.emptyPiece) {
			if((boardState.getPiece(x, y)+boardState.getPiece(x-1, y+1)+boardState.getPiece(x-2, y+2)+
					boardState.getPiece(x-3, y+3)+boardState.getPiece(x-4, y+4)==3*piece) && 
				(boardState.getPiece(x, y)*boardState.getPiece(x-1, y+1)*boardState.getPiece(x-2, y+2)*
					boardState.getPiece(x-3, y+3)*boardState.getPiece(x-4, y+4)==0)) {
				if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x-5, y+5)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		
		// scan the diagnose, left down to right up
		if(boardState.getPiece(x-1, y+1)!=piece && boardState.getPiece(x-1, y+1)!=Util.emptyPiece) {
			if((boardState.getPiece(x, y)+boardState.getPiece(x+1, y-1)+boardState.getPiece(x+2, y-2)+
					boardState.getPiece(x+3, y-3)+boardState.getPiece(x+4, y-4)==3*piece) && 
				(boardState.getPiece(x, y)*boardState.getPiece(x+1, y-1)*boardState.getPiece(x+2, y-2)*
					boardState.getPiece(x+3, y-3)*boardState.getPiece(x+4, y-4)==0)) {
				if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x+5, y-5)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		// scan the diagnose, left up to right down 
		if(boardState.getPiece(x-1, y-1)!=piece && boardState.getPiece(x-1, y-1)!=Util.emptyPiece) {
			if((boardState.getPiece(x, y)+boardState.getPiece(x+1, y+1)+boardState.getPiece(x+2, y+2)+
					boardState.getPiece(x+3, y+3)+boardState.getPiece(x+4, y+4)==3*piece) && 
				(boardState.getPiece(x, y)*boardState.getPiece(x+1, y+1)*boardState.getPiece(x+2, y+2)*
					boardState.getPiece(x+3, y+3)*boardState.getPiece(x+4, y+4)==0)) {
				if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x+5, y+5)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		
		// scan the diagnose, right down to left up
		if(boardState.getPiece(x+1, y+1)!=piece && boardState.getPiece(x+1, y+1)!=Util.emptyPiece) {
			if((boardState.getPiece(x, y)+boardState.getPiece(x-1, y-1)+boardState.getPiece(x-2, y-2)+
					boardState.getPiece(x-3, y-3)+boardState.getPiece(x-4, y-4)==3*piece) && 
				(boardState.getPiece(x, y)*boardState.getPiece(x-1, y-1)*boardState.getPiece(x-2, y-2)*
					boardState.getPiece(x-3, y-3)*boardState.getPiece(x-4, y-4)==0)) {
				if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x-5, y-5)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	
	// determine if live two
	private boolean isLiveTwo(int x,int y, int piece) {
		// scan the column
		if(boardState.getPiece(x, y)==piece && boardState.getPiece(x, y+1)==piece 
				&& boardState.getPiece(x, y+2)==Util.emptyPiece && boardState.getPiece(x, y-1)==Util.emptyPiece) {
			if((boardState.getPiece(x, y+3)==Util.emptyPiece) || (boardState.getPiece(x, y-2)==Util.emptyPiece)) {
				return true;
			}
		}
		// scan the row
		if(boardState.getPiece(x, y)==piece && boardState.getPiece(x+1, y)==piece
				&& boardState.getPiece(x+2, y)==Util.emptyPiece && boardState.getPiece(x-1, y)==Util.emptyPiece) {
			if((boardState.getPiece(x+3, y)==Util.emptyPiece) || (boardState.getPiece(x-2, y)==Util.emptyPiece)) {
				return true;
			}
		}
		// scan the diagnose, right up to left down
		if(boardState.getPiece(x, y)==piece && boardState.getPiece(x-1, y+1)==piece 
				&& boardState.getPiece(x-2, y+2)==Util.emptyPiece && boardState.getPiece(x+1, y-1)==Util.emptyPiece) {
			if((boardState.getPiece(x-3, y+3)==Util.emptyPiece) ||(boardState.getPiece(x+2, y-2)==Util.emptyPiece)) {
				return true;
			}
		}
		// scan the diagnose, left up to right down
		if(boardState.getPiece(x, y)==piece && boardState.getPiece(x+1, y+1)==piece
				&& boardState.getPiece(x+2,y+2)==Util.emptyPiece && boardState.getPiece(x-1, y-1)==Util.emptyPiece) {
			if((boardState.getPiece(x+3, y+3)==Util.emptyPiece) || (boardState.getPiece(x-2, y-2)==Util.emptyPiece)) {
				return true;
			}
		}
		return false;
	}
	
	
	// determine if live three
	private boolean isLiveThree(int x, int y, int piece) {
		// scan the column
		if(boardState.getPiece(x, y) + boardState.getPiece(x, y+1) + boardState.getPiece(x, y+2) + 
				boardState.getPiece(x, y+3)== piece * 3) {
			if(boardState.getPiece(x, y+3)==piece && boardState.getPiece(x, y)==piece) {
				if(boardState.getPiece(x, y-1)==Util.emptyPiece && boardState.getPiece(x, y+4)==Util.emptyPiece) {
					return true;
				}
			} else if(boardState.getPiece(x, y+3)==Util.emptyPiece) {
				if(boardState.getPiece(x, y-1)==Util.emptyPiece && 
						(boardState.getPiece(x, y-2)==Util.emptyPiece || boardState.getPiece(x, y+4)==Util.emptyPiece))	{
					return true;
				}
			}
		}
		
		// scan the row
		if(boardState.getPiece(x, y) + boardState.getPiece(x+1, y) + boardState.getPiece(x+2, y) + 
				boardState.getPiece(x+3, y)==piece*3) {
			if(boardState.getPiece(x+3, y)==piece && boardState.getPiece(x, y)==piece) {
				if(boardState.getPiece(x-1, y)==Util.emptyPiece && boardState.getPiece(x+4, y)==Util.emptyPiece) {
					return true;
				}
			} else if(boardState.getPiece(x+3, y)==Util.emptyPiece) {
				if(boardState.getPiece(x-1, y)==Util.emptyPiece &&
						(boardState.getPiece(x-2, y)==Util.emptyPiece || boardState.getPiece(x+4, y)==Util.emptyPiece)) {
					return true;
				}
			}
		}
		// scan the diagnose, right up to left down
		if(boardState.getPiece(x, y)+boardState.getPiece(x-1, y+1)+boardState.getPiece(x-2, y+2)+
				boardState.getPiece(x-3, y+3)==piece*3) {
			if(boardState.getPiece(x-3, y+3)==piece && boardState.getPiece(x, y)==piece) {
				if(boardState.getPiece(x+1, y-1)==Util.emptyPiece && boardState.getPiece(x-4, y+4)==Util.emptyPiece) {
					return true;
				}
			} else if(boardState.getPiece(x-3, y+3)==Util.emptyPiece) {
				if((boardState.getPiece(x+1, y-1)==Util.emptyPiece) && 
						(boardState.getPiece(x+2, y-2)==Util.emptyPiece || boardState.getPiece(x-4, y+4)==Util.emptyPiece)) {
					return true;
				}
			}
		}
		// scan the diagnose, left up to right down
		if(boardState.getPiece(x, y)+boardState.getPiece(x+1, y+1)+boardState.getPiece(x+2, y+2)+
				boardState.getPiece(x+3, y+3)==piece*3) {
			if(boardState.getPiece(x+3,y+3)==piece && boardState.getPiece(x, y)==piece) {
				if(boardState.getPiece(x-1, y-1)==Util.emptyPiece && boardState.getPiece(x+4, y+4)==Util.emptyPiece) {
					return true;
				}
			} else if(boardState.getPiece(x+3, y+3)==Util.emptyPiece) {
				if((boardState.getPiece(x-1, y-1)==Util.emptyPiece) &&
						(boardState.getPiece(x-2, y-2)==Util.emptyPiece || boardState.getPiece(x+4, y+4)==Util.emptyPiece)) {
					return true;
				}
			}
			
		}
		return false;
		
	}
	
	
	// 检测是否是死4的情形(注意此时要把活4的情况排除出去,此时x，y位置应该是空的)
	private boolean isSemiLiveFour(int x,int y, int piece) {
		// 扫描竖向的列
		if((boardState.getPiece(x, y-1)!=piece && boardState.getPiece(x, y-1)!=Util.emptyPiece) ||
				(boardState.getPiece(x, y+5)!=piece && boardState.getPiece(x, y+5)!=Util.emptyPiece)){
			if(boardState.getPiece(x, y) + boardState.getPiece(x, y+1) + boardState.getPiece(x, y+2) + boardState.getPiece(x, y+3)
					+ boardState.getPiece(x, y+4) == piece * 4) {
//				把活4的情况排除出去
				if(boardState.getPiece(x, y)==Util.emptyPiece &&  boardState.getPiece(x, y+5)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		// 扫描横向的情况
		if((boardState.getPiece(x-1,y)!=piece && boardState.getPiece(x-1, y)!=Util.emptyPiece) ||
				(boardState.getPiece(x+5, y)!=piece && boardState.getPiece(x+5, y)!=Util.emptyPiece)) {
			if(boardState.getPiece(x, y) + boardState.getPiece(x+1, y) + boardState.getPiece(x+2, y) + boardState.getPiece(x+3, y)
					+ boardState.getPiece(x+4, y) == piece * 4) {
				if(boardState.getPiece(x, y)==Util.emptyPiece &&  boardState.getPiece(x+5, y)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		// scan the diagnose, right up to left down
		if((boardState.getPiece(x+1, y-1)!=piece && boardState.getPiece(x+1, y-1)!=Util.emptyPiece) ||
				(boardState.getPiece(x+1, y-1)!=piece && boardState.getPiece(x+1, y-1)!=Util.emptyPiece)) {
			if(boardState.getPiece(x, y) + boardState.getPiece(x-1, y+1) + boardState.getPiece(x-2, y+2) + boardState.getPiece(x-3, y+3)
					+ boardState.getPiece(x-4, y+4) == piece * 4) {
				if(boardState.getPiece(x, y)==Util.emptyPiece &&  boardState.getPiece(x-5, y+5)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		// scan the diagnose, left up to right down
		if((boardState.getPiece(x-1, y-1)!=piece && boardState.getPiece(x-1, y-1)!=Util.emptyPiece) ||
				(boardState.getPiece(x-1, y-1)!=piece && boardState.getPiece(x-1, y-1)!=Util.emptyPiece)) {
			if(boardState.getPiece(x, y) + boardState.getPiece(x+1, y+1) + boardState.getPiece(x+2, y+2) + boardState.getPiece(x+3, y+3)
					+ boardState.getPiece(x+4, y+4) == piece * 4) {
				if(boardState.getPiece(x, y)==Util.emptyPiece &&  boardState.getPiece(x+5, y+5)==Util.emptyPiece) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	// 检测是否是活4得情形(此时x，y位置应该是空的)
	private boolean isLiveFour(int x, int y, int piece) {
		// 检测竖向的列
		if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x, y+1)==piece && 
				boardState.getPiece(x, y+2)==piece && boardState.getPiece(x, y+3)==piece && boardState.getPiece(x, y+4)==piece
				&& boardState.getPiece(x, y+5)==Util.emptyPiece) {
			return true;
		}
		// 检测横向的列
		if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x+1, y)==piece && 
				boardState.getPiece(x+2, y)==piece && boardState.getPiece(x+3, y)==piece && boardState.getPiece(x+4, y)==piece
				&& boardState.getPiece(x+5, y)==Util.emptyPiece) {
			return true;
		}
		// 以x,y作为起点向右上方扫描（反对角线方向）
		if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x-1, y+1)==piece &&
				boardState.getPiece(x-2, y+2)==piece && boardState.getPiece(x-3, y+3)==piece && boardState.getPiece(x-4, y+4)==piece
				&& boardState.getPiece(x-5, y+5)==Util.emptyPiece) {
			return true;
		}
		// 以x,y作为起点向右下方扫描（正对角线方向）
		if(boardState.getPiece(x, y)==Util.emptyPiece && boardState.getPiece(x+1, y+1)==piece &&
				boardState.getPiece(x+2, y+2)==piece && boardState.getPiece(x+3, y+3)==piece && boardState.getPiece(x+4, y+4)==piece
				&& boardState.getPiece(x+5, y+5)==Util.emptyPiece) {
			return true;
		}
		return false;		
	}
	
	// 检测是否是活5得情形
	private boolean isLiveFive(int x, int y, int piece) {
		// 扫描竖向的列
		if(boardState.getPiece(x, y)==piece && boardState.getPiece(x, y+1)==piece && 
				boardState.getPiece(x, y+2)==piece && boardState.getPiece(x, y+3)==piece && boardState.getPiece(x, y+4)==piece) {
			return true;
		}
		// 扫描横向的行
		if(boardState.getPiece(x, y)==piece && boardState.getPiece(x+1, y)==piece && 
				boardState.getPiece(x+2, y)==piece && boardState.getPiece(x+3, y)==piece && boardState.getPiece(x+4, y)==piece) {
			return true;
		}
		// 以x,y作为起点向右上方扫描（反对角线方向）
		if(boardState.getPiece(x, y)==piece && boardState.getPiece(x-1, y+1)==piece &&
				boardState.getPiece(x-2, y+2)==piece && boardState.getPiece(x-3, y+3)==piece && boardState.getPiece(x-4, y+4)==piece) {
			return true;
		}
		// 以x,y作为起点向右下方扫描（正对角线方向）
		if(boardState.getPiece(x, y)==piece && boardState.getPiece(x+1, y+1)==piece &&
				boardState.getPiece(x+2, y+2)==piece && boardState.getPiece(x+3, y+3)==piece && boardState.getPiece(x+4, y+4)==piece) {
			return true;
		}
		return false;
	}
}
