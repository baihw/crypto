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

package com.wee0.crypto.cli;

import com.wee0.crypto.impl.SimpleStringEncryptor;

/**
 * 加解密命令行操作支持
 * 
 * @author baihw
 */
public class EncryptorCli{

	/**
	 * 命令行操作入口
	 * 
	 * @param args 命令行参数
	 */
	public static void main( String[] args ){

		// 获取通过-DKey=val方式设置的秘钥。
		String key = System.getProperty( "wee0.crypto.key" );
		if( null == key || 0 == ( key = key.trim() ).length() ){
			System.err.println( "wee0.crypto.key不能为空！请通过 java -Dwee0.crypto.key=youKey 的方式设置。" );
			return;
		}

		// 当前仅支持简单的调用加密和解密操作，尚不支持指定算法实现等操作。
		// 要求至少有一个参数；
		// 当第1个参数为 decrypt 时，第2个参数为待解密数据；
		// 否则取第1个参数为待加密数据，其它参数直接忽略。
		if( null == args || 0 == args.length ){
			System.err.println( "请至少指定一个参数，当第1个参数为 decrypt 时，第2个参数为待解密数据；否则取第1个参数为待加密数据，其它参数直接忽略。" );
			return;
		}
		String arg0 = args[0];
		if( null == arg0 || 0 == ( arg0 = arg0.trim() ).length() ){
			System.err.println( "请至少指定一个参数，当第1个参数为 decrypt 时，第2个参数为待解密数据；否则取第1个参数为待加密数据，其它参数直接忽略。" );
			return;
		}
		if( "decrypt".equals( arg0 ) ){
			String arg1 = args[1];
			if( null == arg1 || 0 == ( arg1 = arg1.trim() ).length() ){
				System.err.println( "待解密数据不能为空！" );
				return;
			}
			SimpleStringEncryptor sse = new SimpleStringEncryptor( key );
			String res = sse.decrypt( arg1 );
			System.out.println( "解密结果：" + res );
		}else{
			SimpleStringEncryptor sse = new SimpleStringEncryptor( key );
			String res = sse.encrypt( arg0 );
			System.out.println( "加密结果：" + res );
		}

	}

}
