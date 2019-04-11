package com.marcllort.a21points.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que contiene todos los datos referentes a las preferencias
 */

public class Preferences{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("weeklyGoal")
    @Expose
    private Integer weeklyGoal;
    @SerializedName("weightUnits")
    @Expose
    private String weightUnits;

    /**
     * Constructor de la clase preferencias
     * @param weeklyGoal el objetivo de puntos semanales
     * @param weightUnits las unidades en las que se encuentra el peso
     */
    public Preferences(Integer weeklyGoal, String weightUnits) {
        this.weeklyGoal = weeklyGoal;
        this.weightUnits = weightUnits;
    }

    /**
     * Constructor de la clase preferencias
     */
    public Preferences() {

    }

    /**
     * Getter del identificador numerico de las preferencias
     * @return identificador
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter del identificador numerico de las preferencias
     * @param id identificador
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter del usuario al cual pertenecen las preferencias
     * @return el usuario
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter del usuario al cual pertenecen las preferencias
     * @param user el usuario
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter del valor numerico de los objetivos semanales del usuario (los puntos)
     * @return el numero de puntos que tiene el usuario como objetivo cada semana
     */
    public Integer getWeeklyGoal() {
        return weeklyGoal;
    }

    /**
     * Setter del valor numerico de los objetivos semanales del usuario (los puntos)
     * @param weeklyGoal el valor numerico de puntos que tiene el usuario como objetivo cada semana
     */
    public void setWeeklyGoal(Integer weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
    }

    /**
     * Setter de las unidades de peso en las que el usuario quiere representar su peso
     * @return la unidad de medida del peso
     */
    public String getWeightUnits() {
        return weightUnits;
    }

    /**
     * Setter de las unidades de peso en las que el usuario quiere representar su peso
     * @param weightUnits la unidad de medida del peso
     */
    public void setWeightUnits(String weightUnits) {
        this.weightUnits = weightUnits;
    }
}
