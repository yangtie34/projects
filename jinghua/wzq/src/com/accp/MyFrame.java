package com.accp;  

import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  

import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JOptionPane;  
import javax.swing.JPanel;  

@SuppressWarnings("serial")  
public class MyFrame extends JFrame implements ActionListener {  
    private boolean isBlack = true; // 是否轮到黑棋  
    private Board b = new Board();  // 棋盘对象  
    private boolean isWin = false;  // 是否获得胜利  
    private boolean isStart = false;    // 是否联机成功�?始游�?  
    private boolean isYourTurn = false; // 是否轮到自己走子  

    private DataInputStream din = null;  
    private DataOutputStream dout = null;  

    private class MouseHandler extends MouseAdapter {  

        @Override  
        public void mousePressed(MouseEvent e) {  
            if (isStart && isYourTurn && !isWin) {  // 游戏已经�?始并且轮到自己且未分出胜负则可落�?  
                int x = e.getX();  
                int y = e.getY();  
                if (x >= 50 && x <= 540 && y >= 0 && y <= 540) {    // 棋盘范围以内  
                    // 通过鼠标坐标计算出点击棋盘的行和�?  
                    int row = Math.round((y - 50) / 35f);  
                    int col = Math.round((x - 50) / 35f);  
                    if (b.addPiece(row, col, isBlack,false)) {    // 落子成功  
                        repaint();  
                        try {   // 向对方棋盘发送刚才落子的位置(行�?�列)以及黑子还是白子  
                            dout.writeInt(row);  
                            dout.writeInt(col);  
                            dout.writeBoolean(isBlack);  
                        } catch (IOException ex) {  
                            ex.printStackTrace();  
                        }  
                        isWin = b.isWin(row, col, isBlack); // 判断是否获胜  
                        if (!isWin) {  
                            isYourTurn = !isYourTurn;   // 尚未分出胜负则交换走棋方  
                        } else {  
                            JOptionPane.showMessageDialog(null, isBlack ? "黑棋�?"  
                                    : "白棋�?");  
                            isWin = true;  
                            repaint();  
                        }  
                    }  
                }  
            }  
        }  
    }  

    private JButton createGameButton, joinGameButton;  
    private JPanel boardPanel;  // 绘制棋盘的面�?  

    public MyFrame() {  
        this.setTitle("五子�?");  
        this.setSize(700, 600);  
        this.setResizable(false);  
        this.setLocationRelativeTo(null);  
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);  
          
        boardPanel = new MyPanel(b);  
        boardPanel.setBounds(0, 0, 600, 600);  
        this.add(boardPanel);  

        boardPanel.addMouseListener(new MouseHandler());    // 向面板中添加鼠标监听�?  

        this.setLayout(null);  
        createGameButton = new JButton("创建游戏");  
        createGameButton.setBounds(580, 100, 100, 35);  
        createGameButton.addActionListener(this);  
        joinGameButton = new JButton("加入游戏");  
        joinGameButton.setBounds(580, 160, 100, 35);  
        joinGameButton.addActionListener(this);  
        this.add(createGameButton);  
        this.add(joinGameButton);  

        // 创建�?个线程监听对方走棋的消息  
        new Thread(new Runnable() {  

            public void run() {  
                while (true) {  // 循环监听对方发�?�的走棋消息  
                    while (isStart && !isYourTurn) {  
                        try {  
                            // 读取对方落子的行列以及是黑子还是白子  
                            int row = din.readInt();  
                            int col = din.readInt();  
                            boolean isBlack = din.readBoolean();  
                            b.addPiece(row, col, isBlack,true);  // 向棋盘添加对方走的棋�?  
                            repaint();  
                            if(b.isWin(row, col, isBlack)) {  
                                JOptionPane.showMessageDialog(null, isBlack ? "黑棋�?"  
                                        : "白棋�?");  
                                isWin = true;  
                                repaint();  
                            }  
                            isYourTurn = !isYourTurn;   // 收到对方棋子后交换走棋方  
                        } catch (IOException ex) {  
                            ex.printStackTrace();  
                        }  
                    }  
                }  
            }  
        }).start();  
    }  

    public static void main(String[] args) {  
        new MyFrame().setVisible(true);  
    }  

    @Override  
    public void actionPerformed(ActionEvent e) {  
        String command = e.getActionCommand();  
        if (command.equals("创建游戏")) {  

            // 创建�?个线程启动服务器套接�?  
            new Thread(new Runnable() {  

                @Override  
                public void run() {  
                    try {  
                        ServerSocket server = new ServerSocket(8080);  
                        Socket client = server.accept();  
                        din = new DataInputStream(client.getInputStream());  
                        dout = new DataOutputStream(client.getOutputStream());  
                        isStart = true;  
                        isBlack = true;  
                        isYourTurn = true;  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }).start();  

            joinGameButton.setEnabled(false);  
        } else if (command.equals("加入游戏")) {  
            String ip = JOptionPane.showInputDialog(this, "请输入游戏主机IP地址或机器名: ");  
            if(ip != null && !ip.equals("")) {  
                try {  
                    Socket client = new Socket(ip, 8080);  
                    din = new DataInputStream(client.getInputStream());  
                    dout = new DataOutputStream(client.getOutputStream());  
                    isStart = true;  
                    isYourTurn = false;  
                    isBlack = false;  
                } catch (Exception ex) {  
                    ex.printStackTrace();  
                }  
                repaint();  
                createGameButton.setEnabled(false);  
            }  
        }  
    }  

}  