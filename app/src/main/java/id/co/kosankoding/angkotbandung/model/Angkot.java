package id.co.kosankoding.angkotbandung.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Angkot implements Serializable {

    @SerializedName("id")
    private String idAngkot;
    @SerializedName("kode")
    private String kodeAngkot;
    @SerializedName("trayek")
    private String trayek;
    @SerializedName("jarak")
    private String jarak;
    @SerializedName("jumlah")
    private String jumlahArmada;
    @SerializedName("gambar")
    private String gambar;

    public Angkot(String idAngkot, String kodeAngkot, String trayek, String jarak, String jumlahArmada, String gambar) {
        this.idAngkot = idAngkot;
        this.kodeAngkot = kodeAngkot;
        this.trayek = trayek;
        this.jarak = jarak;
        this.jumlahArmada = jumlahArmada;
        this.gambar = gambar;
    }

    public String getIdAngkot() {
        return idAngkot;
    }

    public String getKodeAngkot() {
        return kodeAngkot;
    }

    public String getTrayek() {
        return trayek;
    }

    public String getJarak() {
        return jarak;
    }

    public String getJumlahArmada() {
        return jumlahArmada;
    }

    public String getGambar() {
        return gambar;
    }
}
