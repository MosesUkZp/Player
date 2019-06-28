package Player;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Player implements ActionListener {

    private File songFile;
    private static BasicPlayer player = new BasicPlayer();
    private JButton playButton = new JButton("Play");
    private JButton pauseButton = new JButton("Pause");
    private JButton stopButton = new JButton("Stop");
    private JButton openFileButton = new JButton("Open");
    private JButton volumeDownButton = new JButton("Volume Down");
    private JButton volumeUpButton = new JButton("Volume Up");
    private JButton volumeFullButton = new JButton("Volume Full");
    private JButton MuteButton = new JButton("Mute");
    //    private JButton repeatButton = new JButton("Repeat");
    private JTextField textField = new JTextField();
    private JPanel jPanel = new JPanel();
//    boolean repeat = false;

    public static void main(String[] args) {

        new Player().createGui();
    }

    //Gui implementation

    private void createGui() {

        JFrame frame = new JFrame("Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3, 5, 5));

        frame.add(openFileButton);

        frame.add(textField);
        textField.setEditable(false);
        textField.setText("Song Path");
        textField.setBounds(10, 11, 182, 20);
        textField.setColumns(10);

        frame.add(playButton);
        frame.add(pauseButton);
        frame.add(stopButton);
        frame.add(volumeDownButton);
        frame.add(volumeUpButton);
        frame.add(volumeFullButton);
        frame.add(MuteButton);
//            frame.add(repeatButton);
        frame.add(jPanel);
        jPanel.setBounds(20, 20, 280, 20);

        frame.pack();
        frame.setVisible(true);

        playButton.addActionListener(this);
        stopButton.addActionListener(this);
        pauseButton.addActionListener(this);
        openFileButton.addActionListener(this);
        volumeDownButton.addActionListener(this);
        volumeUpButton.addActionListener(this);
        volumeFullButton.addActionListener(this);
        MuteButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            start();
        } else if (e.getSource() == pauseButton) {
            pause();
        } else if (e.getSource() == openFileButton) {
            open();
        } else if (e.getSource() == volumeDownButton) {
            volumeDownBtn();
        } else if (e.getSource() == volumeUpButton) {
            volumeUpBtn();
        } else if (e.getSource() == volumeFullButton) {
            volumeFullBtn();
        } else if (e.getSource() == MuteButton) {
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
        textField.setText(songFile.getAbsolutePath());
        System.out.println("File " + songFile.getName() + " Selected.");
        try {
            player.open(songFile);
            player.play();
            System.out.println("Play " + songFile.getName());
        } catch (BasicPlayerException e1) {
            e1.printStackTrace();
        }
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
