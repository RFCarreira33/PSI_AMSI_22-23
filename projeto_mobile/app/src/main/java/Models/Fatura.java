package Models;

import java.util.Date;

public class Fatura {
    int id;
    String nif, morada, data;
    double valorTotal, valorIva;

    public Fatura(int id, String nif, String morada, String data, double valorTotal, double valorIva) {
        this.id = id;
        this.nif = nif;
        this.morada = morada;
        this.data = data;
        this.valorTotal = valorTotal;
        this.valorIva = valorIva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorIva() {
        return valorIva;
    }

    public void setValorIva(double valorIva) {
        this.valorIva = valorIva;
    }
}
