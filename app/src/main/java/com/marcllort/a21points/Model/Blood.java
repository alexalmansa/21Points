package com.marcllort.a21points.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que contiene todos los datos referentes a la sangre
 */
public class Blood {

    @SerializedName("diastolic")
    @Expose
    private Integer diastolic;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("systolic")
    @Expose
    private Integer systolic;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * Getter de la presión diastolica de la sangre
     * @return integer que indica la presión diastolica
     */
    public Integer getBlood() {
        return diastolic;
    }

    /**
     * Getter de la presión diastolica de la sangre
     * @param diastolic integer que contiene la información
     */
    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    /**
     * Getter del identificador de la tensión arterial
     * @return integer indicando el identificador
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter del identificador númerico de la presión
     * @param id identificador numérico de aquella presión
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter de la presión arterial sistólica de la sangre
     * @return integer con la presion
     */
    public Integer getSystolic() {
        return systolic;
    }

    /**
     * Setter de la presión arterial sistólica de la sangre
     * @param systolic valor de la presión sistólica
     */
    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    /**
     * Getter de la fecha de cuando se tomo la presión el usuario
     * @return fecha
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Setter de la fecha de cuando se tomo la presión
     * @param timestamp string que contiene la fecha
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter del user al cual pertenece la presión
     * @return El usuario
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter del user al cual pertenece aquella presión sanguinea
     * @param user usuario
     */
    public void setUser(User user) {
        this.user = user;
    }

}