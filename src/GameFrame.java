import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame {
    GameSet g;
    JPanel p;
    JButton b1;
    JButton b2;
    Image img;
    Graphics graph;
    public GameFrame() {
        g = new GameSet(15, 30);
        /* ---------- */
        setComponents();
        this.setVisible(true);
        /* ---------- */
        img = createImage(p.getWidth(), p.getHeight());
        graph = img.getGraphics();
        /* ---------- */

        java.util.Timer time = new Timer();
        TimerTask task = new TimerTask(){

            public void run(){
                if (g.isLife()) {
                    g.generation();
                    play();
                }
            }
        };
        time.scheduleAtFixedRate(task, 0, 500);
        play();
    }

    private void play(){
        graph.setColor(p.getBackground());
        graph.fillRect(0, 0, p.getWidth(), p.getHeight());

        for (int i = 0; i < g.getN_grid(); i++){
            for (int j = 0; j < g.getM_grid(); j++){
                if(g.getGrid()[i][j] == '*'){
                    graph.setColor(Color.red);
                    int x = j * p.getWidth()/g.getM_grid();
                    int y = i * p.getHeight()/g.getN_grid();
                    graph.fillRect(x, y, p.getWidth()/g.getM_grid(), p.getHeight()/g.getN_grid());
                }
            }
        }
        graph.setColor(Color.BLACK );
        for (int i = 1; i < g.getN_grid(); i++){
            int y = i * p.getHeight()/g.getN_grid();
            graph.drawLine(0 , y , p.getWidth(), y);
        }
        for (int j = 1; j < g.getM_grid(); j++){
            int x = j * p.getWidth()/g.getM_grid();
            graph.drawLine(x , 0 , x, p.getHeight());
        }

        p.getGraphics().drawImage(img, 0, 0, p);
    }

    private void setComponents() {
        p = new JPanel();
        p.setBackground(new Color(102, 102, 102));
        p.addMouseListener(new mouseAdapt(g));
        p.addComponentListener(new compAdapt());

        this.setTitle("Welcome to Game Of Life");
        this.setSize(1000, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GroupLayout pLayout = new GroupLayout(p);
        p.setLayout(pLayout);
        pLayout.setHorizontalGroup(
                pLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        pLayout.setVerticalGroup(
                pLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 265, Short.MAX_VALUE)
        );

        b1 = new JButton();
        b1.setText("Play");
        b1.addActionListener((ActionListener) this::b1Event);

        b2 = new JButton();
        b2.setText("Reset");
        b2.addActionListener(this::b2Event);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(p, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(b1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 385, Short.MAX_VALUE)
                                                .addComponent(b2)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(p, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(b1)
                                        .addComponent(b2))
                                .addContainerGap())
        );

        pack();
    }

    private void b1Event(ActionEvent evt) {
        g.setLife(!g.isLife());
        if (g.isLife()) b1.setText("Pause");
        else b1.setText("Play");
        play();
    }

    private void b2Event(ActionEvent e) {
        g.reset();
        play();
    }

    class mouseAdapt extends java.awt.event.MouseAdapter{

        GameSet g;

        public mouseAdapt(GameSet g){
            this.g = g;
        }
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            MouseClicked(evt);
        }

        private void MouseClicked(MouseEvent evt) {
            int j = g.getM_grid() * evt.getX() / p.getWidth();
            int i = g.getN_grid() * evt.getY() / p.getHeight();
            char c = g.getGrid()[i][j] == '*' ? '.' : '*';
            g.setGrid(i, j, c);
            play();
        }
    }

    class compAdapt extends java.awt.event.ComponentAdapter{


        public void componentResized(ComponentEvent evt) {
            compResized();
        }

        private void compResized() {
            img = createImage(p.getWidth(), p.getHeight());
            graph = img.getGraphics();
            play();
        }
    }
}



