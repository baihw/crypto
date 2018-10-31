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
 * 加解密相关操作门面类规范接口
 * 
 * @author baihw
 */
public interface ICryptoFacade{

	/**
	 * 创建一个摘要者构造器实例
	 * 
	 * @param <T> 操作数据类型
	 * @return 构造器实例
	 */
	<T> IDigesterBuilder<T> createDigesterBuilder();

	/**
	 * 创建一个加密者构造器实例
	 * 
	 * @param <T> 操作数据类型
	 * @return 构造器实例
	 */
	<T> IEncryptorBuilder<T> createEncryptorBuilder();

}
