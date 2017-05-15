package ru.saoworld.sm.swortmasters.dao.impl.shop;

import java.io.IOException;

import ru.saoworld.sm.swortmasters.bin.bean.GameItem;
import ru.saoworld.sm.swortmasters.bin.subsidiary.shop.config.ShopAdapter;
import ru.saoworld.sm.swortmasters.dao.DAOController;


public interface IShopController extends DAOController<GameItem,Integer> {
    void getShopItem(ShopAdapter adp,int shopId) throws IOException;


}
