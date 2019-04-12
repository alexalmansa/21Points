package com.marcllort.a21points.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;

import static com.marcllort.a21points.BloodActivity.toZoneDateTime;

/**
 * Clase que contiene los datos referentes al peso
 */
public class Weight {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("weight")
    @Expose
    private Integer weight;

    public Weight() {
    }

    public Weight(Integer weight) {
        this.timestamp = toZoneDateTime(ZonedDateTime.now());
        this.weight = weight;
    }

    /**
     * Getter del identificador del peso con el que se esta tratando
     * @return identificador númerico del peso
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter del identidicador del peso
     * @param id identificador numerico de aquel peso
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter de la fecha del peso
     * @return String que contiene el Timestamp de el peso
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Setter de la fecha del peso
     * @param timestamp String que contiene la fecha del peso
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter del usuario referente a aquel peso
     * @return Usuario al que le corresponde aquel peso
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter del usuario al que pertenece el peso
     * @param user el usuario
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter del valor numerico
     * @return valor del peso
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Setter del peso
     * @param weight valor del peso
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}