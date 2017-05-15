package ru.saoworld.sm.swortmasters.dao;

import java.io.IOException;
import java.util.ArrayList;

import ru.saoworld.sm.swortmasters.dao.exception.DAOException;
import ru.saoworld.sm.swortmasters.dao.impl.IDAO;

public interface DAOController<Entity,PrimaryKey> extends IDAO {
    ArrayList<Entity> getTable(String sql) throws DAOException;
    int checkOnRightLoginData(String login, String password);
    Boolean userRegistry(String login,String password,String email) throws IOException;
    Entity getOneField(String sql) throws DAOException;
}
