package ru.saoworld.sm.swortmasters.dao.impl.location;

import android.content.Entity;

import ru.saoworld.sm.swortmasters.dao.DAOController;


public interface ILocationController extends DAOController<Entity,Integer> {
    String saveNewLocation();

        }