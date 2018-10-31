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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.wee0.crypto.impl.SimpleStringDigester;

/**
 * 
 * @author baihw
 */
public class SimpleStringDigesterTest{

	// 期望的摘要数据长度
	public static final int EXPECT_MIN_LEN = 32;
	public static final int EXPECT_MAX_LEN = 64;

	// 测试实例对象
	private static SimpleStringDigester stringDigester;

	@BeforeClass
	public static void init(){
		stringDigester = new SimpleStringDigester();
	}

	@Test
	public void testDigest(){

		String r0 = stringDigester.digest( TestData.STR0 );
		assertNotNull( r0 );
		assertTrue( r0.length() >= EXPECT_MIN_LEN && r0.length() <= EXPECT_MAX_LEN );
		assertNotEquals( TestData.STR0, r0 );

		String r1 = stringDigester.digest( TestData.STR1 );
		assertNotNull( r1 );
		assertTrue( r1.length() >= EXPECT_MIN_LEN && r1.length() <= EXPECT_MAX_LEN );
		assertNotEquals( TestData.STR1, r1 );

		String r2 = stringDigester.digest( TestData.STR2 );
		assertNotNull( r2 );
		assertTrue( r2.length() >= EXPECT_MIN_LEN && r2.length() <= EXPECT_MAX_LEN );
		assertNotEquals( TestData.STR2, r2 );
	}

	@Test
	public void testMatches(){
		String r0 = stringDigester.digest( TestData.STR0 );
		assertTrue( stringDigester.matches( TestData.STR0, r0 ) );
		assertFalse( stringDigester.matches( TestData.STR1, r0 ) );

		String r1 = stringDigester.digest( TestData.STR1 );
		assertTrue( stringDigester.matches( TestData.STR1, r1 ) );
		assertFalse( stringDigester.matches( TestData.STR2, r1 ) );

		String r2 = stringDigester.digest( TestData.STR2 );
		assertTrue( stringDigester.matches( TestData.STR2, r2 ) );
		assertFalse( stringDigester.matches( TestData.STR1, r2 ) );
	}

	@Test
	public void testDigestWithKey(){
		String r0 = stringDigester.digestWithKey( TestData.STR0, "000" );
		assertNotNull( r0 );
		assertTrue( r0.length() >= EXPECT_MIN_LEN && r0.length() <= EXPECT_MAX_LEN );
		assertNotEquals( TestData.STR0, r0 );

		String r1 = stringDigester.digestWithKey( TestData.STR1, "abc" );
		assertNotNull( r1 );
		assertTrue( r1.length() >= EXPECT_MIN_LEN && r1.length() <= EXPECT_MAX_LEN );
		assertNotEquals( TestData.STR1, r1 );

		String r2 = stringDigester.digestWithKey( TestData.STR2, "1234567abcd" );
		assertNotNull( r2 );
		assertTrue( r2.length() >= EXPECT_MIN_LEN && r2.length() <= EXPECT_MAX_LEN );
		assertNotEquals( TestData.STR2, r2 );
	}

	@Test
	public void testMatchesWithKey(){

		String k0 = "00000000";
		String r0 = stringDigester.digestWithKey( TestData.STR0, k0 );
		assertTrue( stringDigester.matchesWithKey( TestData.STR0, r0, k0 ) );
		assertFalse( stringDigester.matchesWithKey( TestData.STR1, r0, k0 ) );

		String k1 = "abcdefgh";
		String r1 = stringDigester.digestWithKey( TestData.STR1, k1 );
		assertTrue( stringDigester.matchesWithKey( TestData.STR1, r1, k1 ) );
		assertFalse( stringDigester.matchesWithKey( TestData.STR2, r1, k1 ) );

		String k2 = "abcdefghijklmn1234567Abb#@!$^";
		String r2 = stringDigester.digestWithKey( TestData.STR2, k2 );
		assertTrue( stringDigester.matchesWithKey( TestData.STR2, r2, k2 ) );
		assertFalse( stringDigester.matchesWithKey( TestData.STR1, r2, k2 ) );
		
	}

}
