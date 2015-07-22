package com.robinzhou.designpattern.iterator;

import java.util.Iterator;

/*
 * Java 自带interface Iterable
 */
public interface Menu {
	
	public Iterator<MenuItem> createIterator();

}
