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

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.wee0.crypto.impl.SimpleStringEncryptor;

/**
 * 
 * @author baihw
 */
public class SimpleStringEncryptorTest{

	// 测试使用的默认秘钥
	private static final String DEF_KEY = "123abc,.";

	// 测试实例对象
	private static SimpleStringEncryptor stringEncryptor;

	@BeforeClass
	public static void init(){
		stringEncryptor = new SimpleStringEncryptor( DEF_KEY );
	}

	@Test
	public void testEncrypt(){

		String r0 = stringEncryptor.encrypt( TestData.STR0 );
		assertNotNull( r0 );
		String r01 = stringEncryptor.encrypt( TestData.STR0 );
		// 要求两次生成的加密信息一致
		assertEquals( r0, r01 );

		String r1 = stringEncryptor.encrypt( TestData.STR1 );
		assertNotNull( r1 );
		String r11 = stringEncryptor.encrypt( TestData.STR1 );
		assertEquals( r1, r11 );

		String r2 = stringEncryptor.encrypt( TestData.STR2 );
		assertNotNull( r2 );
		String r21 = stringEncryptor.encrypt( TestData.STR2 );
		assertEquals( r2, r21 );

	}

	@Test
	public void testDecrypt(){

		String r0 = stringEncryptor.encrypt( TestData.STR0 );
		String r1 = stringEncryptor.encrypt( TestData.STR1 );
		String r2 = stringEncryptor.encrypt( TestData.STR2 );
		assertEquals( TestData.STR0, stringEncryptor.decrypt( r0 ) );
		assertEquals( TestData.STR1, stringEncryptor.decrypt( r1 ) );
		assertEquals( TestData.STR2, stringEncryptor.decrypt( r2 ) );
		
		String r01 = stringEncryptor.encrypt( TestData.STR0 );
		assertEquals( r0, r01 );
		assertEquals( TestData.STR0, stringEncryptor.decrypt( r01 ) );
	}

}
