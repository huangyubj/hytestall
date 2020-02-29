package com.hy.action.command;

import com.hy.action.command.command.DiscountCommand;
import com.hy.action.command.command.HotCommand;
import com.hy.action.command.command.NewerCommand;

/**
 * 命令模式：将不符合抽象编程的调用，改造成符合的抽象编程
 * 将一个请求封装成一个对象，从而使您可以用不同的请求对客户进行参数化
 * 首页榜单
 */
public class CommandClient {

    public static void main(String[] args) {
        //三个命令，代表三个请求
        Command command1,command2,command3;
        command1 = new HotCommand();
        command2 = new NewerCommand();
        command3 = new DiscountCommand();

        ListView listView;
        listView = new ListView();
        listView.setCommand(command2);

        listView.getList();



    }
}
