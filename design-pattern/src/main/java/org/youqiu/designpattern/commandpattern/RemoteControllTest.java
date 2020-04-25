package org.youqiu.designpattern.commandpattern;

/*命令模式的客户端*/
public class RemoteControllTest {
    public static void main(String[] args) {
//        调用者
        SimpleRemoteControl remote = new SimpleRemoteControl();
//        请求接收者
        Light light = new Light();
//        命令
        LightOnCommand lc = new LightOnCommand(light);
//        把命令传个调用者
        remote.setCommand(lc);
        remote.buttonWasPressed();
    }
}
