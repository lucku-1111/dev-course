package com.example.spring.springtheory.ch01.IoC;

class ColombiaBean implements Bean {
    @Override
    public String name() {
        return "콜롬비아 원두";
    }
}

class EthiopiaBean implements Bean {
    @Override
    public String name() {
        return "에티오피아 원두";
    }
}

interface Bean {
    String name();
}
