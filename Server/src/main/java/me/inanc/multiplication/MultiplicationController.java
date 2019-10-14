package me.inanc.multiplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
/*
Created by İnanç Furkan Çakıl
inanc.cakil@gmail.com
https://github.com/inanc & http://inanc.me
 */

@RestController
public class MultiplicationController {


    @RequestMapping("/")
    public MultiplicationDTO getWithoutRequestParam(MultiplicationDTO multiplicationDTO) {

        BigDecimal number1 = multiplicationDTO.getNumber1();
        BigDecimal number2 = multiplicationDTO.getNumber2();
        BigDecimal result = number1.multiply(number2);
        multiplicationDTO.setResult(result);

        System.out.println(multiplicationDTO);

        return multiplicationDTO;
    }

}
