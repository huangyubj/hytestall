package com.hy.rmi;

import com.hy.busi.service.UserService;
import com.hy.productor.service.UserServiceImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiDemo {

    public static void initRmiProtocol(){
        try {
            UserService userService = new UserServiceImpl();
            LocateRegistry.createRegistry(UserService.port);
            Naming.bind(UserService.RMI_URL, userService);
            System.out.println("初始化rmi绑定[userService]");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public static void consumRmiProtocal(){
        try {
            UserService userService = (UserService) Naming.lookup(UserService.RMI_URL);
            System.out.println("获取RMI服务接口完毕");
            System.out.println(userService.getDetail("1111").toString());
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
