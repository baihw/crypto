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

package com.wee0.crypto.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletRequest;

/**
 * servlet支持对象
 * 
 * @author baihw
 */
public class ServletSupport{

	/**
	 * servletRequest转换为简单键值集合，使用指定过滤函数对参数进行过滤。
	 * 
	 * @param request servletRequest对象
	 * @param nameFilter 参数名称过滤函数，返回true表示接受参数，返回false表示不接受参数。
	 * @return 键值集合
	 */
	public static Map<String, String> servletRequestToMap( ServletRequest request, Function<String, Boolean> nameFilter ){

		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, String> result = new HashMap<>();
		if( null == parameterMap || parameterMap.isEmpty() )
			return result;

		parameterMap.forEach( ( key, vals ) -> {
			if( null == key )
				return;
			if( null == nameFilter || nameFilter.apply( key ) ){
				if( null == vals || 0 == vals.length ){
					result.put( key, "" );
					return;
				}
				String value = vals[0];
				result.put( key, null == value ? "" : value );
			}
		} );

		return result;

	}

	/**
	 * servletRequest转换为简单键值集合
	 * 
	 * @param request servletRequest对象
	 * @param names 名称集合
	 * @param isInclude true表示只接受名称集合中的名称，false表示是排除名称集合中的名称
	 * @return 键值集合
	 */
	public static Map<String, String> servletRequestToMap( ServletRequest request, Set<String> names, boolean isInclude ){

		if( null == names || names.isEmpty() )
			return servletRequestToMap( request, null );

		return servletRequestToMap( request, ( k ) -> {
			return isInclude ? names.contains( k ) : !names.contains( k );
		} );

	}

	/**
	 * servletRequest转换为简单键值集合
	 * 
	 * @param request servletRequest对象
	 * @return 键值集合
	 */
	public static Map<String, String> servletRequestToMap( ServletRequest request ){
		return servletRequestToMap( request, null );
	}

}
