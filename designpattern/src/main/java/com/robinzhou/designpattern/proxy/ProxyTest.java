package com.robinzhou.designpattern.proxy;

import java.lang.reflect.Proxy;

public class ProxyTest {

	public static void main(String[] args) {
		PersonBean robin = new PersonBeanImpl();
		robin.setGender("male");
		robin.setInterests("PC");
		robin.setName("robinzhou");
		robin.setHotOrNotRating(10);
		
		PersonBean ownerProxy = getOwnerProxy(robin);
		ownerProxy.setName("zhou");
		ownerProxy.setHotOrNotRating(9);

	}

	static PersonBean getOwnerProxy(PersonBean person) {
		return (PersonBean) Proxy.newProxyInstance(person.getClass()
				.getClassLoader(), person.getClass().getInterfaces(),
				new OwnerInvocationHandler(person));
	}

	static PersonBean getNonOwnerProxy(PersonBean person) {
		return (PersonBean) Proxy.newProxyInstance(person.getClass()
				.getClassLoader(), person.getClass().getInterfaces(),
				new NonOwnerInvocationHandle(person));
	}

}
