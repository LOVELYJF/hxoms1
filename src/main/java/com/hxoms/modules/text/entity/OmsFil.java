package com.hxoms.modules.text.entity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OmsFil {

	public static void main(String[] args) {
		String fileName = "hhhhh.txt";
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
		System.out.println(ext);
	}
}
