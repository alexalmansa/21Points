
package com.marcllort.a21points.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que contiene todos los datos referentes a un usuario
 */
public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("activated")
    @Expose
    private Boolean activated;
    @SerializedName("langKey")
    @Expose
    private String langKey;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("resetDate")
    @Expose
    private Object resetDate;

    /**
     * Getter del id del usuario
     * @return identificador numerico correspondiente del usuario
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter del id del usuario
     * @param id recibe el identificador numérico del usuario
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter del nombre del username del usuario
     * @return String que contiene el login del usuario
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter del login del usuario
     * @param login String que contiene el login del usuario
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Getter del nombre del usuario
     * @return String que contiene el nombre del usuario
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter del nombre del usuario
     * @param firstName String que contiene el nombre del usuario
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter del apellido del usuario
     * @return Retorna un String que contiene el apellido del usuario
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter del apellido del usuario
     * @param lastName recibe un String que contiene el apellido del usuario
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter del email con el que esta registrado el usuario
     * @return devuelve String que contiene el email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter del correo electronico del usuario.
     * @param email recibe un String que contiene el correo electronico del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter para saber si el usuario esta activado
     * @return booleano indicando si esta activado el usuario o no
     */
    public Boolean getActivated() {
        return activated;
    }

    /**
     * Setter para indicar si el usuario esta activado o no
     * @param activated el estado del usuario
     */
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getResetDate() {
        return resetDate;
    }

    public void setResetDate(Object resetDate) {
        this.resetDate = resetDate;
    }

    /**
     * Metodo que pone en formato JSON los datos del usuario
     * @return string
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
