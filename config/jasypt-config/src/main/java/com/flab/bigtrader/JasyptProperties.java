package com.flab.bigtrader;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Validated
@ConstructorBinding
@RequiredArgsConstructor
@Profile(value = {"dev", "prod"})
@ConfigurationProperties(prefix = "jasypt.encryptor")
public class JasyptProperties {

	@NotBlank
	private final String password;
}
