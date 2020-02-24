package com.hy.structure.adapter;

import com.hy.entity.bag.AppleBag;
import com.hy.entity.bag.OrangeBag;
import com.hy.entity.frute.Orange;

/**
 * 适配器模式(adapter)
 * 将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
 * 适配和桥接都是把对象传入组合起来使用，桥接的目标是分离，适配的目标是合并
 */
public class AdapterClient {
	public static void main(String[] args){
		Orange orange = new Orange(100, "hy");
		OrangeBag bag = getBag2();
		orange.pack(bag);
	}

	/**
	 * 取桔子包装
	 * @return
     */
//	private static OrangeBag getBag(){
//		OrangeBag bag = new OrangeBag();
//		return bag;
//	}

	private static OrangeBag getBag2(){
		//准备用途苹果盒代替
		AppleBag appleBag = new AppleBag();

		//把苹果盒适配成桔子包装盒
		OrangeBag orangeBag = new OrangeBagAdapter(appleBag);

		return orangeBag;
	}
}
