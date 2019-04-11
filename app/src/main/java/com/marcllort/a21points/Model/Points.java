
package com.marcllort.a21points.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que contiene todos los datos referentes a los puntos
 */
public class Points {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("exercise")
    @Expose
    private Integer exercise;
    @SerializedName("meals")
    @Expose
    private Integer meals;
    @SerializedName("alcohol")
    @Expose
    private Integer alcohol;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("week")
    @Expose
    private String week;
    @SerializedName("points")
    @Expose
    private Integer points;


    /**
     * Constructor de la clase Points
     * @param date fecha
     * @param exercise puntos por ejercicio
     * @param meals puntos por comidas
     * @param alcohol puntos por alcohol
     * @param notes String que contiene notas
     */
    public Points(String date, Integer exercise, Integer meals, Integer alcohol, String notes) {
        this.date = date;
        this.exercise = exercise;
        this.meals = meals;
        this.alcohol = alcohol;
        this.notes = notes;
    }

    /**
     * Getter del id de los puntos
     * @return identificador de los puntos
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter del identificador de los puntos
     * @param id identificador
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter de la fecha de los puntos
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter de la fecha de los puntos
     * @param date fecha referente
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter de los puntos referentes al ejercicio
     * @return numero de puntos referentes al ejercicio
     */
    public Integer getExercise() {
        return exercise;
    }

    /**
     * Setter de los puntos referentes al ejercicio
     * @param exercise numero de puntos referentes al ejercicio
     */
    public void setExercise(Integer exercise) {
        this.exercise = exercise;
    }

    /**
     * Getter de los puntos referentes a las comidas
     * @return numero de puntos refe
     */
    public Integer getMeals() {
        return meals;
    }

    /**
     * Setter de los puntos referentes a las comidas
     * @param meals valor numerico de los puntos de las comidas
     */
    public void setMeals(Integer meals) {
        this.meals = meals;
    }

    /**
     * Getter de los puntos referentes al alcohol consumido
     * @return valor de los puntos referentes al alcohol consumido
     */
    public Integer getAlcohol() {
        return alcohol;
    }

    /**
     * Setter de los puntos referentes al alcohol consumido
     * @param alcohol valor a poner en los puntos del alcohol
     */
    public void setAlcohol(Integer alcohol) {
        this.alcohol = alcohol;
    }

    /**
     * Getter de las notas del usuario referentes a los puntos
     * @return objeto con notas
     */
    public Object getNotes() {
        return notes;
    }

    /**
     * Setter de las notas del usuario referentes a los puntos
     * @param notes objeto con notas
     */
    public void setNotes(Object notes) {
        this.notes = notes;
    }

    /**
     * Getter del usuario de los puntos
     * @return el usuario
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter del usuario de los puntos
     * @param user el usuario
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter de la semana de los puntos
     * @return string que contiene la semana
     */
    public String getWeek() {
        return week;
    }

    /**
     * Setter de la semana de los puntos
     * @param week String de la semana
     */
    public void setWeek(String week) {
        this.week = week;
    }

    /**
     * Getter de los puntos totales de la semana
     * @return numero de puntos totales de la semana
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Setter de los puntos de esa semana
     * @param points numero de puntos
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * metodo que pone en formato JSON los datos referentes a los puntos
     * @return
     */
    @Override
    public String toString() {
        return "Points{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", exercise=" + exercise +
                ", meals=" + meals +
                ", alcohol=" + alcohol +
                ", notes=" + notes +
                ", user=" + user +
                '}';
    }
}
