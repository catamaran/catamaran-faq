package org.catamarancode.faq;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class IdTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(Base64.encodeBase64(Long.toBinaryString(System.currentTimeMillis() >> 4).getBytes()));
		System.out.println(Base64.encodeBase64(Long.toBinaryString(System.currentTimeMillis()).getBytes()));
		try {
			System.out.println(new String(Base64.encodeBase64(Long.toBinaryString(System.currentTimeMillis() >> 4).getBytes()), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(System.currentTimeMillis() >> 4);
		System.out.println(System.currentTimeMillis());

	}

}
