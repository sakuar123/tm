package com.sakura.tm.common.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripleDto<L, M, R> {

    private L left;
    private M middle;
    private R right;
}