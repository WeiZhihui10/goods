package cn.edu.lingnan.user.service.impl;

import java.util.List;

import cn.edu.lingnan.user.bean.Address;
import cn.edu.lingnan.user.dao.AddressDao;
import cn.edu.lingnan.user.dao.impl.AddressDaoImpl;
import cn.edu.lingnan.user.service.AddressService;

public class AddressServiceImpl implements AddressService {
	//声明AddressDao对象
	AddressDao addressDao=new AddressDaoImpl();
	
	public List<Address> findAddressService(int uid) {
		//返回结果
		return addressDao.findAddressByUidDao(uid);
	}

	public boolean addAddressService(Address address) {
		// 返回结果
		return addressDao.addAddressDao(address);
	}

}
