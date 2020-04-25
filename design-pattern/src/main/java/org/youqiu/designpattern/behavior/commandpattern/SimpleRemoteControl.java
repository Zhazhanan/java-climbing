package org.youqiu.designpattern.behavior.commandpattern;

/*遥控器*/
public class SimpleRemoteControl {
    //    有一个插槽持有命令
    Command slot;

    public SimpleRemoteControl() {
    }

    //    设置插槽控制的命令，如果想改变遥控器的按钮行为，可以多次调用
    public void setCommand(Command command) {
        this.slot = command;
    }

    public void buttonWasPressed() {
        slot.execute();
    }
}
