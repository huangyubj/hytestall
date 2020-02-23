package com.hy.productor.service;

import com.hy.busi.entity.UserEntity;
import com.hy.busi.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    public UserServiceImpl() throws RemoteException {
        super();
    }

    public UserEntity getDetail(String id) throws RemoteException{
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setAddress("北京海淀");
        user.setBalance(121212111l);
        user.setName(String.format("我是%s员工", id));
        return user;
    }
}
