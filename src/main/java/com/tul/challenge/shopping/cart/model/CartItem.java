package com.tul.challenge.shopping.cart.model;


import com.tul.challenge.product.model.Product;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Cart_Item")
@Data
//@AllArgsConstructor
public class CartItem {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Column(columnDefinition = "ENUM('PENDING', 'COMPLETED')")
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;

    public CartItem(){
        this.id = UUID.randomUUID();
    }



}
