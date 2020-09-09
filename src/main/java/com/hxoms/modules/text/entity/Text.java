package com.hxoms.modules.text.entity;

import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.file.entity.OmsFile;
import springfox.documentation.service.ApiListing;

public class Text {


	public static void main(String[] args) {
		String  a = "你好20208月";
		String e = a.substring(a.indexOf("[") + 1, a.indexOf("]"));
		System.out.println(e);
	}
}
