package com.teammetallurgy.atum.world.biome;

import com.teammetallurgy.atum.init.AtumEntities;
import net.minecraft.entity.EntityClassification;

public class SandHillsBiome extends AtumBiome {

    public SandHillsBiome() {
        super(new Builder("sand_hills", 10).setBaseHeight(0.3F).setHeightVariation(0.3F));
        this.addDefaultSpawns();
        this.deadwoodRarity = 0.08D;
    }

    @Override
    protected void addDefaultSpawns() {
        super.addDefaultSpawns();
        addSpawn(AtumEntities.DESERT_WOLF, 5, 2, 4, EntityClassification.CREATURE);
    }
}