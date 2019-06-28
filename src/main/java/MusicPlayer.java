
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;


public class MusicPlayer extends JFrame {

    private JButton openButton = new JButton("Open");
    private JButton playButton = new JButton("Play");
    private JButton pauseButton = new JButton("Pause");
    private JButton stopButton = new JButton("Stop");
    private JButton rewindBackButton = new JButton(" <<< ");
    private JButton rewindForwardButton = new JButton(" >>> ");

    static BasicPlayer player = new BasicPlayer();

    public MusicPlayer() {
        super("Music Player");
        this.setBounds(100, 100, 250, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 2, 2));

        playButton.addActionListener(new PlayButton());
        container.add(playButton);

        pauseButton.addActionListener(new PauseButton());
        container.add(pauseButton);

        stopButton.addActionListener(new StopButton());
        container.add(stopButton);

    }


   public class PlayButton implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {
                player.open(new URL("file:\\C://Users\\Мирослав Олегович\\Downloads\\Nightwish\\nightwish-elan.mp3"));
                player.play();
                System.out.println("Play");
            } catch (BasicPlayerException | MalformedURLException e1) {
                e1.printStackTrace();
            }
        }
    }
    public class PauseButton implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {
                player.pause();
                System.out.println("Pause");
            } catch (BasicPlayerException e1) {
                e1.printStackTrace();
            }
        }
    }

    public class StopButton implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                player.stop();
                System.out.println("Stop");
            } catch (BasicPlayerException e1) {
                e1.printStackTrace();
            }
        }
    }







}




