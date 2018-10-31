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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author baihw
 */
public class ServletSupportTest{

	@Mock
	private ServletRequest request;

	// 测试集合数据
	private Map<String, String> map;

	@Before
	public void init(){

		map = new HashMap<>();
		map.put( "appId", "app-001-test" );
		map.put( "accessKey", "AKIAIOSFODNN7EXAMPLE" );
		map.put( "secretKey", "k3nL7gH3+PadhTEVn5EXAMPLE" );
		map.put( "date", new Date().toString() );
		map.put( "url", "http://www.abcd.com.cn/callback/test?a=a1&b=01b1&c=a中文b#c@1" );
		map.put( "number", "0" );

		Map<String, String[]> requestParameterMap = new HashMap<>( map.size() );
		map.forEach( ( k, v ) -> {
			requestParameterMap.put( k, new String[]{ v, v + "__" } );
		} );

		MockitoAnnotations.initMocks( this );
		when( request.getParameter( Mockito.anyString() ) ).thenAnswer( ( invocation ) -> {
			Object arg0 = invocation.getArgument( 0 );
			return map.get( arg0.toString() );
		} );
		when( request.getParameterMap() ).thenReturn( requestParameterMap );

	}

	@Test
	public void test(){
		Map<String, String> r0 = ServletSupport.servletRequestToMap( request );
		assertEquals( map, r0 );

		Map<String, String> m1 = new HashMap<>();
		m1.putAll( map );
		assertEquals( map, m1 );

		Set<String> names = new HashSet<>();
		names.add( "number" );
		names.add( "secretKey" );

		names.forEach( ( val ) -> {
			m1.remove( val );
		} );
		assertNotEquals( m1, r0 );

		Map<String, String> r1 = ServletSupport.servletRequestToMap( request, names, false );
		assertEquals( m1, r1 );

		Map<String, String> m2 = new HashMap<>();
		names.forEach( ( val ) -> {
			m2.put( val, map.get( val ) );
		} );
		assertNotEquals( map, m2 );
		assertEquals( 2, m2.size() );
		Map<String, String> r2 = ServletSupport.servletRequestToMap( request, names, true );
		assertEquals( m2, r2 );

	}

}
