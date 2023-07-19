package br.com.quaz.store.services.category;


import br.com.quaz.store.controllers.request.CategoryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

class CategoryRequestTest {
    private Validator validator;

    @BeforeEach
    void setValidator() {
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    void mustValidateCategoryRequest() {
        final CategoryRequest categoryRequest = CategoryRequest.builder().build();
        this.validator.validate(categoryRequest);
        System.out.println("validate");
    }
}
