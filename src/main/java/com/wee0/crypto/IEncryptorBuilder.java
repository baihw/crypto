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
 * 加密者实例构建器规范接口
 * 
 * @param <T> 操作对象数据类型
 * @author baihw
 */
public interface IEncryptorBuilder<T>{

	/**
	 * 设置秘钥
	 * 
	 * @param key 秘钥
	 * @return 当前构建器对象
	 */
	IEncryptorBuilder<T> setKey( String key );

	/**
	 * 构建实例
	 * 
	 * @return 实例对象
	 */
	IEncryptor<T> build();

}
