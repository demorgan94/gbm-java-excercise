package mx.dm.gbmexercisebackend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    @JsonProperty("current_balance")
    private Account currentBalance;

    @JsonProperty("business_errors")
    private List<BusinessError> businessErrors;

    public OrderResponse(List<BusinessError> businessErrors) {
        this.businessErrors = businessErrors;
    }
}
