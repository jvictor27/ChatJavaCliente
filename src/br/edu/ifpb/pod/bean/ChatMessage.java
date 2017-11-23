/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class ChatMessage implements Serializable {
    
    private String name;
    private String text;
    
    // Armazena nome do cliente que receberá mensagem reservada
    private String nameReserved;
    
    // Armazena todos os cientes online/conectados no servidor, enquanto servidor ativo
    private Set<String> setOnlines = new HashSet<String>();
    
    private Action action;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNameReserved() {
        return nameReserved;
    }

    public void setNameReserved(String nameReserved) {
        this.nameReserved = nameReserved;
    }

    public Set<String> getSetOnlines() {
        return setOnlines;
    }

    public void setSetOnlines(Set<String> setOnlines) {
        this.setOnlines = setOnlines;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
        
    // Ações a serem executados pelo cliente no chat
    public enum Action {
        CONNECT, DISCONNECT, SEND_ONE, SEND_ALL, USERS_ONLINE, COMMAND_ERO
    }
}
