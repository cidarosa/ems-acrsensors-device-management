package com.github.cidarosa.acrsensor.device.management.common;

import io.hypersistence.tsid.TSID;

import java.util.Optional;

public class IdGenerator {

    private static final TSID.Factory tsidFactory;

    static {
        //busca variável de ambiente das prorpiedades do sistema
//        System.setProperty("tsid.node", "2");
//        System.setProperty("tsid.node.count", "32"); //tamanho do cluster
        //em produção setar as variáveis de ambiente, senão pega o padrão do TSID
        Optional.ofNullable(System.getenv("tsid.node"))
                .ifPresent(tsidNode -> System.setProperty("tsid.node", tsidNode));

        Optional.ofNullable(System.getenv("tsid.node.count"))
                .ifPresent(tsidNodeCount -> System.setProperty("tsid.node.count", tsidNodeCount));


        tsidFactory = TSID.Factory.builder().build();
    }

    private IdGenerator() {
    }

    public static TSID generateTSID() {
        return tsidFactory.generate();
    }
}
