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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author baihw
 */
public class SimpleMapDigesterTest{

	// 测试实例对象
	private static SimpleMapDigester mapDigester;

	// 测试数据容器
	private static Map<String, String> map;

	@BeforeClass
	public static void init(){

		mapDigester = new SimpleMapDigester();

		LocalDateTime ldt = LocalDateTime.now();

		map = new HashMap<>();
		map.put( "appId", "app-001-test" );
		map.put( "accessKey", "AKIAIOSFODNN7EXAMPLE" );
		map.put( "secretKey", "k3nL7gH3+PadhTEVn5EXAMPLE" );
		map.put( "date", new Date().toString() );
		map.put( "date1", ldt.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm:ss.SSS" ) ) );
		map.put( "date2", ldt.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm:ss,SSS" ) ) );
		map.put( "date3", ldt.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ) ) );
		map.put( "date4", ldt.format( DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" ) ) );
		map.put( "date5", ldt.format( DateTimeFormatter.ofPattern( "EEE MMM d HH:mm:ss 'GMT' yyyy" ) ) );
		map.put( "date6", ldt.format( DateTimeFormatter.ofPattern( "EEE, MMM d HH:mm:ss 'GMT' yyyy" ) ) );
		map.put( "date7", ldt.format( DateTimeFormatter.ofPattern( "EEE, d MMM yyyy HH:mm:ss" ) ) );
		map.put( "date8", ldt.format( DateTimeFormatter.ISO_DATE_TIME ) );
		map.put( "date9", ldt.format( DateTimeFormatter.BASIC_ISO_DATE ) );
		map.put( "date10", DateTimeFormatter.ISO_INSTANT.format( Instant.now() ) );
		map.put( "url", "http://www.abcd.com.cn/callback/test?a=a1&b=01b1&c=a中文b#c@1" );
		map.put( "number", "0" );
		map.put( "number0", "00" );
		map.put( "number1", "1" );
		map.put( "number2", "2.1" );
		map.put( "number3", "3.10" );
		map.put( "number4", "04" );
		map.put( "number5", "05.10" );

	}

	@AfterClass
	public static void destroy(){
		map.clear();
		map = null;
	}

	@Test
	public void testDigest(){

		String r0 = mapDigester.digest( map );
		assertNotNull( r0 );

		String r1 = mapDigester.digest( new HashMap<>() );
		assertNotNull( r1 );

		String r2 = mapDigester.digest( null );
		assertNotNull( r2 );

	}

	@Test
	public void testMatches(){

		String r0 = mapDigester.digest( map );
		assertTrue( mapDigester.matches( map, r0 ) );
		assertFalse( mapDigester.matches( null, r0 ) );

		Map<String, String> m1 = new HashMap<>();
		String r1 = mapDigester.digest( m1 );
		assertTrue( mapDigester.matches( m1, r1 ) );
		assertTrue( mapDigester.matches( new HashMap<>(), r1 ) );

		String r2 = mapDigester.digest( null );
		assertTrue( mapDigester.matches( null, r2 ) );
		assertFalse( mapDigester.matches( map, r2 ) );

	}

	@Test
	public void testDigestWithKey(){

		String r0 = mapDigester.digestWithKey( map, "1234567abcd" );
		assertNotNull( r0 );

		String r1 = mapDigester.digestWithKey( new HashMap<>(), "abcdefg" );
		assertNotNull( r1 );

		String r2 = mapDigester.digestWithKey( null, "0123456789" );
		assertNotNull( r2 );

	}

	@Test
	public void testMatchesWithKey(){

		String k0 = "000";
		String r0 = mapDigester.digestWithKey( map, k0 );
		assertTrue( mapDigester.matchesWithKey( map, r0, k0 ) );
		assertFalse( mapDigester.matchesWithKey( null, r0, k0 ) );

		String k1 = "abcdefgh";
		Map<String, String> m1 = new HashMap<>();
		String r1 = mapDigester.digestWithKey( m1, k1 );
		assertTrue( mapDigester.matchesWithKey( m1, r1, k1 ) );
		assertTrue( mapDigester.matchesWithKey( new HashMap<>(), r1, k1 ) );

		String k2 = "abcdefghijklmn1234567Abb#@!$^";
		String r2 = mapDigester.digestWithKey( null, k2 );
		assertTrue( mapDigester.matchesWithKey( null, r2, k2 ) );
		assertFalse( mapDigester.matchesWithKey( map, r2, k2 ) );

	}

}
