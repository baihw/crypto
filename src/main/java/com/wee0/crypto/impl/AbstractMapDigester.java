/**
 * Copyright (c) 2016-2022, wee0.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package com.wee0.crypto.impl;

import java.util.Map;
import java.util.TreeMap;

import com.wee0.crypto.IDigester;

/**
 * 一个抽象的键值对集合摘要实现者基类
 * 
 * @author baihw
 */
public abstract class AbstractMapDigester implements IDigester<Map<String, String>>{

	// 字符串摘要实现者实例
	private IDigester<String> stringDigester = new SimpleStringDigester();

	/* (non-Javadoc)
	 * @see com.wee0.crypto.IDigester#digest(java.lang.Object)
	 */
	@Override
	public String digest( Map<String, String> obj ){
		String objString = mapToString( obj );
		return stringDigester.digest( objString );
	}

	/* (non-Javadoc)
	 * @see com.wee0.crypto.IDigester#matches(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean matches( Map<String, String> obj, String expected ){
		String objString = mapToString( obj );
		return stringDigester.matches( objString, expected );
	}

	/* (non-Javadoc)
	 * @see com.wee0.crypto.IDigester#digestWithKey(java.lang.Object, java.lang.String)
	 */
	@Override
	public String digestWithKey( Map<String, String> obj, String key ){
		String objString = mapToString( obj );
		return stringDigester.digestWithKey( objString, key );
	}

	/* (non-Javadoc)
	 * @see com.wee0.crypto.IDigester#matchesWithKey(java.lang.Object, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean matchesWithKey( Map<String, String> obj, String expected, String key ){
		String objString = mapToString( obj );
		return stringDigester.matchesWithKey( objString, expected, key );
	}

	/**
	 * 将键值数据转换为字符串表示形式的处理逻辑。
	 * 
	 * @param data 键值数据
	 * @return 字符串
	 */
	private final String mapToString( Map<String, String> data ){
		// 数据过滤
		data = dataFilter( data );
		// 数据编码
		data = dataEncode( data );
		// 数据排序
		data = dataSort( data );
		// 转字符串
		return dataToString( data );
	}

	/**
	 * 对数据进行过滤
	 * 
	 * @param data 原始数据
	 * @return 过滤后的数据
	 */
	protected abstract Map<String, String> dataFilter( Map<String, String> data );

	/**
	 * 对数据进行编码
	 * 
	 * @param data 原始数据
	 * @return 编码后的数据
	 */
	protected Map<String, String> dataEncode( Map<String, String> data ){
		// 默认不进行编码
		return data;
	}

	/**
	 * 对数据进行排序
	 * 
	 * @param data 原始数据
	 * @return 排序后的数据
	 */
	protected Map<String, String> dataSort( Map<String, String> data ){
		if( null == data || data.isEmpty() )
			return new TreeMap<>();
		// 使用TreeMap默认的字典排序升序排列数据
		TreeMap<String, String> result = new TreeMap<>();
		result.putAll( data );
		return result;
	}

	/**
	 * 将数据转换为字符串文本表示形式
	 * 
	 * @param data 原始数据
	 * @return 字符串文本
	 */
	protected abstract String dataToString( Map<String, String> data );

}
