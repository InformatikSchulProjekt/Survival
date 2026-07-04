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
        register(new LeatherBoots());
        register(new ApprenticeRing());
        register(new SmallHealthCharm());
        register(new LuckyCoin());
        register(new WindrunnerGloves());
        register(new EmberAmulet());
        register(new MysticHourglass());
        register(new PhoenixHeart());
        register(new StormCrown());
        register(new OceanTear());
        register(new TitanBelt());
        register(new CrownOfTheArchmage());
        register(new ImmortalSoulstone());
        register(new RabbitsFoot());
        register(new BloodChalice());
        register(new CopperCompass());
        register(new ScholarsPendant());
        register(new TomeOfPractice());
        register(new MindCrystal());
        register(new CrownOfWisdom());
        register(new PhilosophersStone());
    }

    public BaseItem getItem(String itemID) {
        return items.get(itemID);
    }

    private void register(BaseItem item) {
        items.put(item.getID(), item);
    }

    public BaseItem[] getItemsByRarity(ItemRarity rarity) {
        return items.values()
            .stream()
            .filter(item -> item.getRarity() == rarity)
            .toArray(BaseItem[]::new);
    }
}

