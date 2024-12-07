package br.com.souza.optimistic_locking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyProductRequest {

    private Integer id;
    private Integer desiredQuantity;
}
