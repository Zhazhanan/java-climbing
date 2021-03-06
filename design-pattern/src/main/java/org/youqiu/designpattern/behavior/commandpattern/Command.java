package org.youqiu.designpattern.behavior.commandpattern;

/**
 * 命令模式将“请求”封装成对象，以便使用不同的请求、队列或者日志来参数化其他对象。命令模式也支持可撤销的操作
 */
/*命令接口*/
public interface Command {
    //    执行
    public void execute();

    //    撤销
    public void undo();
}
