package com.test.SurvivorGame.item;

import com.test.SurvivorGame.item.items.*;

import java.util.HashMap;
import java.util.Map;

public final class ItemRegistry {
    private final Map<String, BaseItem> items = new HashMap<>();

    public ItemRegistry() {
        register(new ChainmailHauberk());
        register(new RogueScarf());
        register(new SeraphWings());
    }

    public BaseItem getItem(String itemID) {
        return items.get(itemID);
    }

    private void register(BaseItem item) {
        items.put(item.getID(), item);
    }
}

