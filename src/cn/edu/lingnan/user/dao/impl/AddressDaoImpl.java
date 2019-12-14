package cn.edu.lingnan.user.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.lingnan.user.bean.Address;
import cn.edu.lingnan.user.bean.User;
import cn.edu.lingnan.user.dao.AddressDao;
import cn.edu.lingnan.util.JDBC;

public class AddressDaoImpl implements AddressDao {

	/**
	 * 封装address信息
	 * @param rSet
	 * @return
	 */
	public List<Address> EncapsulationAddress(ResultSet rSet) {
		List<Address> addresses=new ArrayList<Address>();
		Address address=null;
		try {
			while(rSet.next()) {
				address=new Address();
				address.setAid(rSet.getString("aid"));
				address.setAddress(rSet.getString("address"));
				address.setCommonly(rSet.getInt("commonly"));
				User user=new User();
				user.setId(rSet.getInt("uid"));
				address.setUser(user);
				addresses.add(address);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return addresses;
	}

	/**
	 * 通过uid查询地址
	 * @param oid
	 * @return
	 */
	public List<Address> findAddressByUidDao(int uid) {
		// 创建jdbc对象
		ResultSet rSet=null;
		//创建List<Address>对象
		List<Address> addresseList=new ArrayList<Address>();
		//创建sql语句
		String sql="select * from t_address where uid=?";
		//给占位符赋值
		Object[] params= {uid};
		//执行sql语句
		rSet=JDBC.executeQuery(sql, params);
		addresseList=EncapsulationAddress(rSet);
		return addresseList;
	}

	/**
	 * 添加收货地址
	 * @param address
	 * @return
	 */
	public boolean addAddressDao(Address address) {
		// 创建sql语句
		String sql="insert into t_address values(?,?,?,?)";
		//给占位符赋值
		Object[] params= {address.getAid(),address.getAddress(),address.getCommonly(),address.getUser().getId()};
		//执行sql语句
		return JDBC.executeUpdate(sql, params);
	}

}
