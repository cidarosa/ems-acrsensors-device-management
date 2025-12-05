package com.github.cidarosa.acrsensor.device.management.api.config.web;

import io.hypersistence.tsid.TSID;
import org.springframework.core.convert.converter.Converter;
// String -> origen | TSID -> destino


public class StringToTSIDWebConverter implements Converter<String, TSID> {
    @Override
    public TSID convert(String source) {
        return TSID.from(source);
    }
}
