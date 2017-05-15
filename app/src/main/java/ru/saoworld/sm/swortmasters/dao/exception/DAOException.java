package ru.saoworld.sm.swortmasters.dao.exception;

/**
 * Created by Саша on 11.04.2017.
 */

public class DAOException extends Exception {
    public DAOException(Exception e){
        super(e);
    }
}
