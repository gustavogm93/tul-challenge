package com.tul.challenge.config.exception;

import com.tul.challenge.config.exception.error.FormatMessage;
import com.tul.challenge.product.exceptions.InvalidProductFormatException;
import com.tul.challenge.product.exceptions.ProductNotFoundException;
import com.tul.challenge.shopping.cart.exceptions.cart.item.CartItemNotFoundException;
import com.tul.challenge.shopping.cart.exceptions.cart.item.DuplicateCartItemException;
import com.tul.challenge.shopping.cart.exceptions.cart.item.UpdateDifferentCartItemException;
import com.tul.challenge.shopping.cart.exceptions.shopping.cart.ShoppingCartEmptyException;
import com.tul.challenge.shopping.cart.exceptions.shopping.cart.ShoppingCartHasStateCompletedException;
import com.tul.challenge.shopping.cart.exceptions.shopping.cart.ShoppingCartNotFoundException;
import com.tul.challenge.shopping.cart.exceptions.shopping.cart.ShoppingCartNotHaveCartItemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionConfig {

    @ExceptionHandler({NotFoundException.class, CartItemNotFoundException.class, ProductNotFoundException.class, ShoppingCartNotFoundException.class})
        public ResponseEntity<?> notFoundException(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FormatMessage.toJson(e.getMessage()));
        }

    @ExceptionHandler({BadRequestException.class, DuplicateCartItemException.class, InvalidProductFormatException.class,
            ShoppingCartNotHaveCartItemException.class, UpdateDifferentCartItemException.class, ShoppingCartEmptyException.class,
            ShoppingCartHasStateCompletedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> badRequestException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FormatMessage.toJson(e.getMessage()));
    }


}
