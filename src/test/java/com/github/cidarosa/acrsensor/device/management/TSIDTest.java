package com.github.cidarosa.acrsensor.device.management;

import com.github.cidarosa.acrsensor.device.management.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class TSIDTest {

    @Test
    void shouldGenerateTSID() {

        // Foi setado as variáveis de ambiente na IDE, node=7, node.count=64, em edit Configurations
        TSID tsid = IdGenerator.generateTSID();

        Assertions.assertThat(tsid.getInstant())
                .isCloseTo(Instant.now(), Assertions.within(1, ChronoUnit.MINUTES));

//        System.out.println("String: " + tsid);
//        System.out.println(tsid.toLong());
//        System.out.println(tsid.getInstant());


    }

}
