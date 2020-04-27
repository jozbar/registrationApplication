package com.baraniok.appregister.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.LowercaseCharacterRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;


public class PasswordConstrainValidator implements ConstraintValidator<ValidPassword, String> {
	
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
				new LengthRule(8, 40),
				new UppercaseCharacterRule(1),
				new LowercaseCharacterRule(1),
				new DigitCharacterRule(1),
				new WhitespaceRule()));
		
		RuleResult ruleResult = passwordValidator.validate(new PasswordData(password));
		
		if(ruleResult.isValid()) {
			return true;
		}
		
		List<String> errorMessages = passwordValidator.getMessages(ruleResult);
		context.disableDefaultConstraintViolation();
		errorMessages.forEach(s -> context.buildConstraintViolationWithTemplate(s).addConstraintViolation());
//		context.buildConstraintViolationWithTemplate(Joiner.on(",").join(errorMessages)).addConstraintViolation();
		return false;
	}
}
