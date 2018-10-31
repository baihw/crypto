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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.wee0.crypto.IEncryptor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * 一个简单的字符串加密者实现
 * 
 * @author baihw
 */
@Resource
@ToString( exclude = "SECRETKEY" )
@EqualsAndHashCode
public class SimpleStringEncryptor implements IEncryptor<String>{

	// 默认算法，可选：AES, DES, DESede, Blowfish, RC2, RC4
	private static final String DEF_ALGORITHM = "AES";
	// 加密秘钥
	private final SecretKey SECRETKEY;

	// 使用的算法
	@Getter
	private final String ALGORITHM;

	// 用户秘钥
	@Getter
	private final String KEY;

	/**
	 * 使用指定的秘钥构造对象
	 * 
	 * @param key 秘钥
	 */
	public SimpleStringEncryptor( @NonNull String key ){

		if( key.length() < 8 ){
			key = String.format( "%8s", key );
		}
		this.KEY = key;
		this.ALGORITHM = DEF_ALGORITHM;

		try{
			KeyGenerator keyGen = KeyGenerator.getInstance( this.ALGORITHM );
			SecureRandom sr = SecureRandom.getInstance( "SHA1PRNG" );
			sr.setSeed( this.KEY.getBytes() );
			keyGen.init( 128, sr );
			this.SECRETKEY = keyGen.generateKey();
		}catch( NoSuchAlgorithmException e ){
			throw new RuntimeException( e );
		}

	}

	@Override
	public String encrypt( String val ){

		try{
			Cipher cipher = Cipher.getInstance( this.ALGORITHM );
			cipher.init( Cipher.ENCRYPT_MODE, this.SECRETKEY );
			byte[] resBytes = cipher.doFinal( val.getBytes() );
			return Hex.encodeHexString( resBytes );
		}catch( NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e ){
			throw new RuntimeException( e );
		}

	}

	@Override
	public String decrypt( String encryptedVal ){

		try{
			Cipher cipher = Cipher.getInstance( this.ALGORITHM );
			cipher.init( Cipher.DECRYPT_MODE, this.SECRETKEY );
			byte[] resBytes = cipher.doFinal( Hex.decodeHex( encryptedVal ) );
			return new String( resBytes );
		}catch( NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | DecoderException e ){
			throw new RuntimeException( e );
		}

	}

}
