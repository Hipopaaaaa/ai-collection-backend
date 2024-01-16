package com.ohj.aicollectionbackend.utils;

import com.ohj.aicollectionbackend.spark.utils.SparkAuthUtils;
import org.junit.jupiter.api.Test;

/**
 * @author Hipopaaaaa
 * @create 2023/12/22 11:35
 */

public class SparkAuthUtilsTest {


    @Test
    void test(){
        System.out.println(SparkAuthUtils.genAuthUrl("6920d5f9133ad544afb4427ddb0f237b", "NjE5N2UwYjM1MmY2OTlkZTM2M2QzYzIz", "localhost", "123"));
    }
}
