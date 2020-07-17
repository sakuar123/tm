package com.sakura.tm.common.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FourDto<L, M, R,T> {
    private L one;
    private M two;
    private R three;
    private T four;
}