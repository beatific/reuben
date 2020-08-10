package com.skcc.reuben;

import com.skcc.reuben.bean.Action;
import com.skcc.reuben.bean.Reuben;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Reuben(name="test")
@Slf4j
public class ReubenTest {

	@Getter
	private String test;

	@Action
	public void reubenTest(String test) {
		
		this.test = test;
	}
}
