package com.teammetallurgy.atum.world.biome;

import com.teammetallurgy.atum.init.AtumEntities;
import com.teammetallurgy.atum.world.gen.AtumSurfaceBuilders;
import net.minecraft.entity.EntityClassification;

public class DeadOasisBiome extends AtumBiome {

    public DeadOasisBiome() {
        super(new Builder("dead_oasis", 0).setHeightVariation(0.0F).setBiomeBlocks(AtumSurfaceBuilders.GRAVEL_CRACKED));
        this.deadwoodRarity = 0.0D;
        //this.decorator.grassPerChunk = 2;
        this.addDefaultSpawns();
    }

    @Override
    protected void addDefaultSpawns() {
        super.addDefaultSpawns();
        addSpawn(AtumEntities.CAMEL, 6, 2, 6, EntityClassification.CREATURE);
    }

    /*@Override
    public void decorate(@Nonnull World world, @Nonnull Random random, @Nonnull BlockPos pos) {
        int x = random.nextInt(4) + 4;
        int z = random.nextInt(4) + 4;

        int i1 = random.nextInt(16) + 8;
        int j1 = random.nextInt(256);
        int k1 = random.nextInt(16) + 8;
        (new WorldGenLakes(Blocks.AIR)).generate(world, random, pos.add(i1, j1, k1));

        if (random.nextFloat() <= 0.70F) {
            new WorldGenPalm(true, 5, AtumBlocks.DEADWOOD_LOG.getDefaultState().with(BlockDeadwood.HAS_SCARAB, true), PalmLeavesBlock.getLeave(BlockAtumPlank.WoodType.DEADWOOD).getDefaultState().with(PalmLeavesBlock.CHECK_DECAY, false), false).generate(world, random, world.getHeight(pos.add(x, 0, z)));
        }

        super.decorate(world, random, pos);
    }
    */

    @Override
    public int getFoliageColor() {
        return 10189386;
    }

    @Override
    public int getGrassColor(double x, double z) {
        return 10189386;
    }
}