package cn.edu.lingnan.user.dao;

import java.sql.ResultSet;
import java.util.List;

import cn.edu.lingnan.user.bean.Address;

public interface AddressDao {
	/**
	 * 封装address信息
	 * @param rSet
	 * @return
	 */
	List<Address> EncapsulationAddress(ResultSet rSet);
	/**
	 * 通过uid查询地址
	 * @param oid
	 * @return
	 */
	List<Address> findAddressByUidDao(int uid);
	/**
	 * 添加收货地址
	 * @param address
	 * @return
	 */
	boolean addAddressDao(Address address);
}
