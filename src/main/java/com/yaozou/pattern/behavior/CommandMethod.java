package com.yaozou.pattern.behavior;

/**
 * @author yaoozu
 * command pattern
 * <b>命令模式把一个请求或者操作封装到一个对象中。命令模式把发出命令的责任和执行命令的责任分割开，委派给不同的对象。命令模式允许请求的一方和发送的一方独立开来，
 * 使得请求的一方不必知道接收请求的一方的接口，更不必知道请求是怎么被接收，以及操作是否执行，何时被执行以及是怎么被执行的。系统支持命令的撤消。</b>
 * @date 2020/6/2417:57
 * @since v1.0.0
 */
public class CommandMethod {
    public static void main(String[] args) {
        // 创建接收者对象
        AudioPlayer audioPlayer = new AudioPlayer();

        // 创建命令对象 new PlayCommand(audioPlayer)

        // 创建请求者对象
        Keypad keypad = new Keypad();
        keypad.setPlayCommand(new PlayCommand(audioPlayer));
        keypad.setStopCommand(new StopCommand(audioPlayer));
        keypad.setRewindCommand(new RewindCommand(audioPlayer));

        // 执行
        keypad.play();
        keypad.rewind();
        keypad.stop();
    }
}

/** 请求者角色，由键盘类扮演 */
class Keypad {
    private Command playCommand;
    private Command rewindCommand;
    private Command stopCommand;

    public void setPlayCommand(Command playCommand) {
        this.playCommand = playCommand;
    }
    public void setRewindCommand(Command rewindCommand) {
        this.rewindCommand = rewindCommand;
    }
    public void setStopCommand(Command stopCommand) {
        this.stopCommand = stopCommand;
    }
    /**
     * 执行播放方法
     */
    public void play(){
        playCommand.execute();
    }
    /**
     * 执行倒带方法
     */
    public void rewind(){
        rewindCommand.execute();
    }
    /**
     * 执行播放方法
     */
    public void stop(){
        stopCommand.execute();
    }
}


class AudioPlayer{
    public void play(){
        System.out.println("播放。。。。");
    }
    public void stop(){
        System.out.println("停止播放。。。");
    }
    public void rewind(){
        System.out.println("倒带。。。。");
    }
}

interface Command{
    void execute();
}

class PlayCommand implements Command{
    private AudioPlayer audioPlayer;
    public PlayCommand(AudioPlayer audioPlayer){
        audioPlayer = audioPlayer;
    }
    @Override
    public void execute() {
        audioPlayer.play();
    }
}

class RewindCommand implements Command {

    private AudioPlayer myAudio;

    public RewindCommand(AudioPlayer audioPlayer){
        myAudio = audioPlayer;
    }
    @Override
    public void execute() {
        myAudio.rewind();
    }

}

class StopCommand implements Command {
    private AudioPlayer myAudio;

    public StopCommand(AudioPlayer audioPlayer){
        myAudio = audioPlayer;
    }
    @Override
    public void execute() {
        myAudio.stop();
    }

}