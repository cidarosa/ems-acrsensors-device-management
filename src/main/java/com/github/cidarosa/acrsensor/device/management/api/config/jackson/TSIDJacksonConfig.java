package com.github.cidarosa.acrsensor.device.management.api.config.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.hypersistence.tsid.TSID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TSIDJacksonConfig {

    // Modulo do Jackson
    @Bean
    public Module tsiModule(){
        SimpleModule module = new SimpleModule();

        module.addSerializer(TSID.class, new TSIDToStringSerializer());

        return module;
    }
}
