package Player;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MusicPlayerController extends JFrame implements ActionListener {
    private JPanel panel1;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton previousTrackButton;
    private JButton nextTrackButton;
    private JButton fastForwardButton;
    private JButton openFileButton;
    private JTextField textFieldPath;
    private JButton muteButton;
    private JSlider sliderValue;
    private JButton fastRewindButton;
    private JTextField textFieldSong;
    private JButton repeatButton;
    private JProgressBar progressSong;
    private File songFile;
    private static BasicPlayer player = new BasicPlayer();
    private SourceDataLine m_line;
    private FloatControl m_gainControl;

    private MusicPlayerController(){

        textFieldSong.setEditable(false);
        textFieldSong.setText("Waiting for track...");
        playButton.addActionListener(this);
        stopButton.addActionListener(this);
        pauseButton.addActionListener(this);
        openFileButton.addActionListener(this);
//        volumeDownButton.addActionListener(this);
//        volumeUpButton.addActionListener(this);
//        volumeFullButton.addActionListener(this);
        muteButton.addActionListener(this);
        repeatButton.addActionListener(this);
    }

    public static void main(String[] args) {

        JFrame playerFrame = new JFrame( "Player" );
        playerFrame.setContentPane(new MusicPlayerController().panel1);
        playerFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        playerFrame.pack();
        playerFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            start();
        } else if (e.getSource() == pauseButton) {
            pause();
        } else if (e.getSource() == openFileButton) {
            open();
//        } else if (e.getSource() == sliderValue) {
//            volumeDownBtn();
//        } else if (e.getSource() == sliderValue) {
//            volumeUpBtn();
//        } else if (e.getSource() == volumeFullButton) {
//            volumeFullBtn();
        }else if (e.getSource() == muteButton) {
            MuteBtn();
        } else {
            stop();
        }
    }

    //Button implementation

    private void open() {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose song to load...");
        chooser.showOpenDialog(null);
        songFile = chooser.getSelectedFile();
        textFieldPath.setText(songFile.getAbsolutePath());
        textFieldSong.setText(songFile.getName());
        System.out.println("File " + songFile.getName() + " Selected.");
        try {
            player.open(songFile);
            player.play();
            System.out.println("Play " + songFile.getName());
        } catch (BasicPlayerException e1) {
            e1.printStackTrace();
        }
    }

    private void repeat () {

    }

    private void start() {
        try {
            player.resume();
            player.play();
            System.out.println("Play " + songFile.getName());
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "No file Selected", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pause() {
        try {
            player.pause();
            System.out.println("Pause " + songFile.getName());
        } catch (BasicPlayerException e1) {
            e1.printStackTrace();
        }
    }

    private void stop() {
        try {
            player.stop();
            System.out.println("Stop " + songFile.getName());
        } catch (BasicPlayerException e1) {
            e1.printStackTrace();
        }
    }

    private void volumeDownControl(Double valueToPlusMinus) {

        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        for (Mixer.Info mixersInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixersInfo);
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                Line line = null;
                boolean opened = true;

                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    if (!opened) {
                        line.open();
                    }

                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    float currentVolume = volControl.getValue();
                    Double volumeToCut = valueToPlusMinus;
                    float checgedCalc = (float) ((float) currentVolume - (double) volumeToCut);
                    volControl.setValue(checgedCalc);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } finally {
                    if (line != null && !opened) {
                        line.close();
                    }
                }
            }
        }
    }

    private void volumeDownBtn() {
        volumeDownControl(0.1);
    }

    private void volumeUpControl(Double valueToPlusMinus) {

        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        for (Mixer.Info mixersInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixersInfo);
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                Line line = null;
                boolean opened = true;

                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    if (!opened) {
                        line.open();
                    }

                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    float currentVolume = volControl.getValue();
                    Double volumeToCut = valueToPlusMinus;
                    float checgedCalc = (float) ((float) currentVolume + (double) volumeToCut);
                    volControl.setValue(checgedCalc);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } finally {
                    if (line != null && !opened) {
                        line.close();
                    }
                }
            }
        }
    }

    private void volumeUpBtn() {
        volumeUpControl(0.1);
    }

    private void volumeControl(Double valueToPlusMinus) {

        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        for (Mixer.Info mixersInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixersInfo);
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                Line line = null;
                boolean opened = true;

                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    if (!opened) {
                        line.open();
                    }

                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    float currentVolume = volControl.getValue();
                    Double volumeToCut = valueToPlusMinus;
                    float checgedCalc = (float) ((double) volumeToCut);
                    volControl.setValue(checgedCalc);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } finally {
                    if (line != null && !opened) {
                        line.close();
                    }
                }
            }
        }
    }

    private void volumeFullBtn() {
        volumeControl(1.0);
    }

    private void MuteBtn() {
        volumeControl(0.0);
    }

//    private void repeat (){
//        if (repeat == false){
//            repeat = true;
//            repeat();
//        }
//    }


}
