package com.cheese.thread;

import com.cheese.board.Board;
import com.cheese.stateModel.BoardState;
/**
 * 
 * @author yangyudong
 * 这个类继承Runnable接口，用以不间断的画棋盘
 */
public class ViewThread implements Runnable {

	BoardState boardState;

	Board board;

	public ViewThread(BoardState boardState, Board cb) {
		this.boardState = boardState;
		this.board = cb;
	}

	@Override
	public void run() {
		board.showUI();
	}

}
