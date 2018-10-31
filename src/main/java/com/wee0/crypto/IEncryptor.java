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
 * 加密者规范接口
 * 
 * @param <T> 加解密数据类型
 * @author baihw
 */
public interface IEncryptor<T>{

	/**
	 * 加密指定数据
	 * 
	 * @param val 要加密的数据
	 * @return 加密结果
	 */
	public T encrypt( T val );

	/**
	 * 解密经过加密的数据
	 * 
	 * @param encryptedVal 待解密的数据
	 * @return 解密数据
	 */
	public T decrypt( T encryptedVal );

}
