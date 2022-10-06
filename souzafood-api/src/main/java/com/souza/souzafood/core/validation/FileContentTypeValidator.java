package com.souza.souzafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private List<String> allowedContentTypes;

	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.allowedContentTypes = Arrays.asList(constraintAnnotation.allowed());
//		asList() Este método atua como uma ponte entre baseado em array e baseado em coleção
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

		return multipartFile == null || 
				this.allowedContentTypes.contains(multipartFile.getContentType());

	}

}
