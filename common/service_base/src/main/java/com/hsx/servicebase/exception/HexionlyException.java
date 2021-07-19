package com.hsx.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HexionlyException extends RuntimeException {

    private Integer code;//状态码

    private String msg;//异常信息
}
