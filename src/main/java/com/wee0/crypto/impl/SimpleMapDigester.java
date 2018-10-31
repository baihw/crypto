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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.wee0.crypto.IDigester;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 一个简单的键值对集合摘要实现者
 * 
 * @author baihw
 */
@Resource
@ToString
@RequiredArgsConstructor
public class SimpleMapDigester extends AbstractMapDigester implements IDigester<Map<String, String>>{

//	// 使用的字符集编码
//	@Getter
//	private final String ENCODING;

	// 使用的签名数据字段名
	@Getter
	private final String KEY_SIGN;

	public SimpleMapDigester(){
//		this.ENCODING = "UTF-8";
		this.KEY_SIGN = "sign";
	}

	/* (non-Javadoc)
	 * @see com.wee0.crypto.impl.AbstractMapDigester#dataFilter(java.util.Map)
	 */
	@Override
	protected Map<String, String> dataFilter( Map<String, String> data ){
		Map<String, String> result = new HashMap<>();
		if( null == data || data.isEmpty() )
			return result;
		data.forEach( ( key, val ) -> {
			// 过滤值为空的数据
			if( null == val || 0 == ( val = val.trim() ).length() )
				return;
			// 过滤签名数据
			if( KEY_SIGN.equalsIgnoreCase( key ) )
				return;
			result.put( key, val );
		} );
		return result;
	}

//	/* (non-Javadoc)
//	 * @see com.wee0.crypto.impl.AbstractMapDigester#dataEncode(java.util.Map)
//	 */
//	@Override
//	protected Map<String, String> dataEncode( Map<String, String> data ){
//		Map<String, String> result = new HashMap<>();
//		if( null == data || data.isEmpty() )
//			return result;
//		data.forEach( ( key, val ) -> {
//			try{
//				val = URLEncoder.encode( val, ENCODING );
//			}catch( UnsupportedEncodingException e ){
//				throw new RuntimeException( e );
//			}
//			result.put( key, val );
//		} );
//		return result;
//	}

//	/* (non-Javadoc)
//	 * @see com.wee0.crypto.impl.AbstractMapDigester#dataSort(java.util.Map)
//	 */
//	@Override
//	protected Map<String, String> dataSort( Map<String, String> data ){
//		if( null == data || data.isEmpty() )
//			return new TreeMap<>();
//
//		Comparator<String> comparator = new Comparator<String>(){
//			@Override
//			public int compare( String o1, String o2 ){
//				return o2.compareTo( o1 );
//			}
//		};
//		TreeMap<String, String> result = new TreeMap<>( comparator );
//		result.putAll( data );
//		return result;
//	}

	/* (non-Javadoc)
	 * @see com.wee0.crypto.impl.AbstractMapDigester#dataToString(java.util.Map)
	 */
	@Override
	protected String dataToString( Map<String, String> data ){
		if( null == data || data.isEmpty() )
			return "";
		StringBuilder sb = new StringBuilder();
		data.forEach( ( key, val ) -> {
			sb.append( key ).append( "=" ).append( val ).append( "&" );
		} );
		if( '&' == sb.charAt( sb.length() - 1 ) )
			sb.deleteCharAt( sb.length() - 1 );
		return sb.toString();
	}

}
