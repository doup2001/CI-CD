package com.eedo.gdg.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "members")
    private Member member;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 비즈니스 로직 추가
    public void createOrder(Member member,Delivery delivery) {
        // member 연관성 부여
        this.member = member;
        this.member.setOrders(this);

        // delivery 연관성 부여
        this.delivery = delivery;
        this.delivery.setOrder(this);
    }

    public void complete() {
        this.status = OrderStatus.COMPLETE;
        this.delivery.complete();
    }

    public void cancel() {
        this.status = OrderStatus.CANCEL;
        this.delivery.cancel();
    }

    public void update(Delivery new_delivery) {
        this.delivery = new_delivery;
        this.delivery.update(new_delivery.getAddress());

    }

}
