package com.om.flagexplorer;

import org.springframework.boot.SpringApplication;

public class TestFlagExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.from(FlagExplorerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
