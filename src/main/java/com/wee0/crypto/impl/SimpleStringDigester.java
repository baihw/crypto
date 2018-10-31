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

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;

import com.wee0.crypto.IDigester;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * 一个简单的字符串摘要实现者
 * 
 * @author baihw
 */
@Resource
@ToString
@EqualsAndHashCode
public class SimpleStringDigester implements IDigester<String>{

	@Override
	public String digest( @NonNull String obj ){
		return DigestUtils.sha1Hex( obj );
	}

	@Override
	public boolean matches( @NonNull String obj, @NonNull String expected ){
		return expected.equals( DigestUtils.sha1Hex( obj ) );
	}

	@Override
	public String digestWithKey( @NonNull String obj, @NonNull String key ){
		obj = generateWithKeyVal( obj, key );
		return DigestUtils.sha1Hex( obj );
	}

	@Override
	public boolean matchesWithKey( @NonNull String obj, @NonNull String expected, @NonNull String key ){
		obj = generateWithKeyVal( obj, key );
		return expected.equals( DigestUtils.sha1Hex( obj ) );
	}

	/**
	 * 生成一个带秘钥值的方法
	 * 
	 * @param val 原值
	 * @param key 秘钥
	 * @return 新值
	 */
	private String generateWithKeyVal( @NonNull String val, @NonNull String key ){
		SimpleStringEncryptor sse = new SimpleStringEncryptor( key );
		return sse.encrypt( val );
	}

}
