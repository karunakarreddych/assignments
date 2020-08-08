package com.chase.chaseelasticsearch.enums;

import java.util.stream.Stream;

public enum IndexTypes {

	employees,
	teams,
	messages;
	
	
	public static String[] getValues(){
		return Stream.of(IndexTypes.values()).map(IndexTypes::name).toArray(String[]::new);
	}
}
