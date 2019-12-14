package cn.edu.lingnan.user.service;

import java.util.List;

import cn.edu.lingnan.user.bean.Address;
import cn.edu.lingnan.user.dao.AddressDao;
import cn.edu.lingnan.user.dao.impl.AddressDaoImpl;

public interface AddressService {
	//声明AddressDao对象
	AddressDao addressDao=new AddressDaoImpl();
	/**
	 * 查询用户收货地址
	 * @param uid
	 * @return
	 */
	List<Address> findAddressService(int uid);
	/**
	 * 添加收货地址
	 * @param address
	 * @return
	 */
	boolean addAddressService(Address address);
	
}
