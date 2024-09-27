package br.com.renan.desafioandroid.core;

class NoConnectivityException : Throwable() {
    override val message: String
        get() = "ERRO DE CONECTIVIDADE"
}
