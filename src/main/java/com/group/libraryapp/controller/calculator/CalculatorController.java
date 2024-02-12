package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calulator.request.CalculatorMultiplyRequest;
import com.group.libraryapp.dto.calulator.request.ColculatorAddRequest;
import org.springframework.web.bind.annotation.*;

@RestController //API의 진입지점
public class CalculatorController {

    @GetMapping("/add") // get /add API 지정
    public int addTwoNumber(ColculatorAddRequest request){ // @RequestParam 주어진 쿼리를 함수 파라미터에 입력
        return request.getNumber1() + request.getNumber2();
    }

    @PostMapping("/multiply") // POST /multiply
    public int multiplyTowNumbers(@RequestBody CalculatorMultiplyRequest request){
        return request.getNumber1() * request.getNumber2();
    }
}
