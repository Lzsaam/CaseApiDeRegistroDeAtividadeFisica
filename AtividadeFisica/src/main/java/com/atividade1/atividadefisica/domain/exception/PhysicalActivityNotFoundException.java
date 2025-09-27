package com.atividade1.atividadefisica.domain.exception;

public class PhysicalActivityNotFoundException extends RuntimeException {

    public PhysicalActivityNotFoundException(Long id) {
        super("Physical activity with id \"%s\" not found.".formatted(id));
    }
}
