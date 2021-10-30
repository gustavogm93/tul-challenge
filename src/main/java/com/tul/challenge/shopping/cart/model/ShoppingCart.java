package com.tul.challenge.shopping.cart.model;


import com.tul.challenge.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Shopping_Cart")
@Data
//@AllArgsConstructor
public class ShoppingCart {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    //private Product products;

    //private ShoppingCartEnum state;

    public ShoppingCart(){
        this.id = UUID.randomUUID();
    }



}
