package com.accp;  

import java.awt.Color;  
import java.awt.Graphics;  

/** 
 * 棋盘 
 * @author 骆昊 
 * 
 */  
public class Board {  
    private int[][] board = new int[15][15];    // 用二维数组代�?15*15的棋�?  
      
    /** 
     * 绘制棋盘 
     * @param g 画笔 
     */  
    public void draw(Graphics g) {  
        for(int i = 0; i < board.length; i++) {  
            for(int j = 0; j < board[i].length; j++) {  
                if(board[i][j] != 0) {  // 不是空格  
                    if(board[i][j] == 1) {  // 黑子  
                        g.setColor(Color.BLACK);  
                    }  
                    else if(board[i][j] == 2) {  // 白子  
                        g.setColor(Color.WHITE);  
                    }else if(board[i][j] == 3) {  // 新黑�?  
                        g.setColor(Color.red);  
                        board[i][j] = 1;
                    }else if(board[i][j] == 4) {  // 新白�?  
                        g.setColor(Color.red);  
                        board[i][j] = 2;
                    } 
                    g.fillOval(35 * (j + 1), 35 * (i + 1), 30, 30);  
                    if(board[i][j] == 2) {  // 给白子加上边�?  
                        g.setColor(Color.BLACK);  
                        g.drawOval(35 * (j + 1), 35 * (i + 1), 30, 30);  
                    }  
                }  
            }  
        }  
    }  

    /** 
     * 向棋盘中放置棋子 
     * @param row 放置棋子的行 
     * @param col 放置棋子的列 
     * @param isBlack 黑棋还是白棋 
     * @return 返回true落子成功, 返回false落子失败(已经有棋�?) 
     */  
    public boolean addPiece(int row, int col, boolean isBlack,boolean isOther) {  
        if(board[row][col] == 0) {  // 没有棋子的地方才能落子  
        	if(isOther){
        		board[row][col] = isBlack?3:4;
        	}else{
        		board[row][col] = isBlack?1:2;
        	}
            //:isBlack? 1 : 2;   // 1代表黑子2代表白子  
            return true;  
        }  
        return false;  
    } 
      
    /** 
     * 判断胜负 
     * @param row 落子的行 
     * @param col 落子的列 
     * @param isBlack 是黑子还是白�? 
     * @return 获胜返回true否则返回false 
     */  
    public boolean isWin(int row, int col, boolean isBlack) {  
        return checkH(row, col, isBlack) ||   
                checkV(row, col, isBlack) ||   
                checkX1(row, col, isBlack) ||   
                checkX2(row, col, isBlack);  
    }  

    // 判断从右上到左下的斜线上是否连成5颗棋�?  
    private boolean checkX2(int row, int col, boolean isBlack) {  
        int counter = 1;  
        int currentRow = row;  
        int currentCol = col;  
        int v = isBlack? 1 : 2;  
        while(currentRow > 0 && currentCol < 14 &&   
                board[--currentRow][++currentCol] == v) {  
            counter++;  
        }  
        currentRow = row;  
        currentCol = col;  
        while(currentRow < 14 && currentCol > 0 &&   
                board[++currentRow][--currentCol] == v) {  
            counter++;  
        }  
        return counter >= 5;  
    }  

    // 判断从左上到右下的斜线上是否连成5颗棋�?  
    private boolean checkX1(int row, int col, boolean isBlack) {  
        int counter = 1;  
        int currentRow = row;  
        int currentCol = col;  
        int v = isBlack? 1 : 2;  
        while(currentRow > 0 && currentCol > 0 &&   
                board[--currentRow][--currentCol] == v) {  
            counter++;  
        }  
        currentRow = row;  
        currentCol = col;  
        while(currentCol < 14 && currentRow < 14 &&   
                board[++currentRow][++currentCol] == v) {  
            counter++;  
        }  
        return counter >= 5;  
    }  

    // 判断竖着方向上是否连�?5颗棋�?  
    private boolean checkV(int row, int col, boolean isBlack) {  
        int counter = 1;  
        int currentRow = row;  
        int v = isBlack? 1 : 2;  
        while(currentRow > 0 && board[--currentRow][col] == v) {  
            counter++;  
        }  
        currentRow = row;  
        while(currentRow < 14 && board[++currentRow][col] == v) {  
            counter++;  
        }  
        return counter >= 5;  
    }  

    // 判断横着方向上是否连�?5颗棋�?  
    private boolean checkH(int row, int col, boolean isBlack) {  
        int counter = 1;  
        int currentCol = col;  
        int v = isBlack? 1 : 2;  
        while(currentCol > 0 && board[row][--currentCol] == v) {  
            counter++;  
        }  
        currentCol = col;  
        while(currentCol < 14 && board[row][++currentCol] == v) {  
            counter++;  
        }  
        return counter >= 5;  
    }  
}  