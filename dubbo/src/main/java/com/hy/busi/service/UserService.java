package com.hy.busi.service;

import com.hy.busi.entity.UserEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

    String RMI_URL = "rmi://127.0.0.1:9999/UserService";
    int port = 9999;

    //RMI方法需要跑出remoteException，否则注册会启动报错
    UserEntity getDetail(String id) throws RemoteException;
}
