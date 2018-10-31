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

package com.wee0.crypto;

/**
 * 摘要者规范接口
 * 
 * @param <T> 加解密数据类型
 * @author xiaohu
 */
public interface IDigester<T>{

	/**
	 * 对指定数据计算摘要
	 * 
	 * @param obj 要计算摘要的数据
	 * @return 摘要结果
	 */
	String digest( final T obj );

	/**
	 * 检查原始数据与指定摘要结果是否匹配
	 * 
	 * @param obj 原始数据
	 * @param expected 期望匹配的摘要结果
	 * @return 是否匹配
	 */
	boolean matches( final T obj, final String expected );

	/**
	 * 对指定数据计算摘要
	 * 
	 * @param obj 要计算摘要的数据
	 * @param key 计算摘要时使用的秘钥
	 * @return 摘要结果
	 */
	String digestWithKey( final T obj, final String key );

	/**
	 * 检查原始数据与指定摘要结果是否匹配
	 * 
	 * @param obj 原始数据
	 * @param expected 期望匹配的摘要结果
	 * @param key 计算摘要时使用的秘钥
	 * @return 是否匹配
	 */
	boolean matchesWithKey( final T obj, final String expected, final String key );

}
