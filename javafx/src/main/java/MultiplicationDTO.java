import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MultiplicationDTO {

    @Positive
    private BigDecimal number1;

    @Positive
    private BigDecimal number2;

    private BigDecimal result;

    public MultiplicationDTO(@Positive BigDecimal number1, @Positive BigDecimal number2, BigDecimal result) {
        this.number1 = number1;
        this.number2 = number2;
        this.result = result;
    }


    @Override
    public String toString() {
        return "MultiplicationDTO{" +
                "number1=" + number1 +
                ", number2=" + number2 +
                ", result=" + result +
                '}';
    }
}
